package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Items.HealthPotion;
import com.mygdx.game.Items.Poison;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Controller extends ApplicationAdapter {

    // game entities
    ArrayList<GameEntity> gameEntities = new ArrayList<>();
    //camera
    SpriteBatch batch;
    OrthographicCamera camera;
    //InputProcessor
    InputProcessor inputProcessor = new InputProcessor();
    //heart for hp bar
    Texture healthTexture;
    //player
    Player player;
    //enemies
    Texture white_enemy_texture;
    Texture blue_enemy_texture;
    Texture pink_enemy_texture;
    //timers
    long start_time_damageSplash = System.currentTimeMillis();
    long start_time = System.currentTimeMillis();
    long start_time_itemSpawn = System.currentTimeMillis();
    long start_time_spawn = System.currentTimeMillis();
    long start_time_bullet = System.currentTimeMillis();
    long start_time_poison = System.currentTimeMillis();
    //Items
    Texture redpotion_texture;
    Texture greenpotion_texture;
    Texture yellowpotion_texture;
    //background textures
    Texture background_texture;
    //random for random generated numbers
    Random random = new Random();
    //fireball
    Texture fireball_texture;
    //counters for increased hardness
    int min_enemies = 3;
    int max_enemies = 5;
    int waveCount;
    int enemySpawnDelay = 1000;
    Texture gameOver;
    boolean playerTookDamage;

    @Override
    public void create() {
        //Camera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);

        //Input Processor
        Gdx.input.setInputProcessor(inputProcessor);

        //Spieler
        player = new Player();

        //heart Texture
        healthTexture = new Texture("heart.png");

        //background textures
        background_texture = new Texture("duuh69.png");

        //fireball
        fireball_texture = new Texture("myBall.png");

        //Items
        greenpotion_texture = new Texture("green potion.png");
        redpotion_texture = new Texture("red potion.png");
        yellowpotion_texture = new Texture("yellow potion.png");

        //enemy textures
        white_enemy_texture = new Texture("Enemy 09-1.png");
        blue_enemy_texture = new Texture("Enemy 11-1.png");
        pink_enemy_texture = new Texture("Enemy 12-1.png");
    }

    @Override
    public void render() {


        //Initialise scene
        ScreenUtils.clear(216, 158, 85, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //start of draw process
        batch.begin();

        // draw background
        batch.draw(background_texture, 0, 0);

        // draw all items
        for (GameEntity e : gameEntities) {
            if (e.getEntityType() == GameEntity.entityType.ITEM) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }
        // draw player
        if (playerTookDamage) {
            batch.setColor(Color.RED);
        }
        if (System.currentTimeMillis() - start_time_damageSplash > 2000) {
            start_time_damageSplash = System.currentTimeMillis();
            batch.setColor(Color.WHITE);
            playerTookDamage = false;
        }

        if (player.playerdirection == null) {
            player.playerdirection = Player.direction.BACK;
        }
        switch (player.playerdirection) {
            case BACK -> batch.draw(player.player_walk_front, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            case FRONT -> batch.draw(player.player_walk_back, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            case LEFT -> batch.draw(player.player_walk_left, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            case RIGHT -> batch.draw(player.player_walk_right, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        }


        // draw all enemies
        //batch.setColor(1, 1, 1, 0.9f);
        batch.setColor(Color.WHITE);
        for (GameEntity e : gameEntities) {
            if (e.getEntityType() == GameEntity.entityType.ENEMY || e.getEntityType() == GameEntity.entityType.PINKENEMY) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        // draw all bullets
        for (GameEntity e : gameEntities) {
            if (e.getEntityType() == GameEntity.entityType.BULLET) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTexture(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }


        //Draw Hp Bar
        drawHealthIcons();

        if (player.hp.getHealth() == 0) {
            batch.draw(gameOver, 0, 0);
        }

        if (inputProcessor.isPaused())
            batch.setColor(Color.GRAY);
        else
            batch.setColor(Color.WHITE);

        //end of draw process
        batch.end();

        //check if Game is paused
        if (!inputProcessor.isPaused()) {

            // spawn enemies
            spawnRandomEnemies(random.nextInt(min_enemies, max_enemies));

            //spawn Item
            spawnItems();

            //loop through gameEntities arraylist
            for (int i = 0; i < gameEntities.size(); i++) {
                GameEntity e = gameEntities.get(i);
                Rectangle r = new Rectangle(e.getx(), e.gety(), 96, 96);

                e.move(player, gameEntities, Gdx.graphics.getDeltaTime());

                //damage the player if he has contact with enemy
                if (e.getEntityType() == GameEntity.entityType.ENEMY && player.player_rectangle.overlaps(r)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        player.hp.decrease(1);
                        playerTookDamage = true;
                        if (player.killsEnemiesOnContact) {
                            gameEntities.remove(i);
                        }
                    }
                }
                if (e.getEntityType() == GameEntity.entityType.PINKENEMY && player.player_rectangle.overlaps(r)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        player.hp.decrease(1);
                        playerTookDamage = true;
                        gameEntities.remove(i);
                        System.out.println("Pinki Boy");
                    }
                }

                //execute onContact of Item when in contact & remove item from screen
                if (e.getEntityType() == GameEntity.entityType.ITEM && player.player_rectangle.overlaps(r)) {
                    e.onContact();
                    gameEntities.remove(i);
                }
            }

            //Check if poison effect is over
            if (System.currentTimeMillis() - start_time_poison > 5000) {
                player.killsEnemiesOnContact = false;
                System.out.println("poison off");
            }


            //If player dies
            if (player.hp.getHealth() == 0) {
                playerDeath();
            }
            // Wenn player ausserhalb des Screens im X bereich
            if (player.player_rectangle.x < 0) player.player_rectangle.x = 0;
            if (player.player_rectangle.x > 1280 - 96) player.player_rectangle.x = 1280 - 96;

            // Wenn player ausserhalb des Screens im Y bereich
            if (player.player_rectangle.y < 0) player.player_rectangle.y = 0;
            if (player.player_rectangle.y + 96 > 720) player.player_rectangle.y = 720 - 96;

            //Input
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.setPlayerdirection(Player.direction.RIGHT);
                player.player_rectangle.x += 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.setPlayerdirection(Player.direction.LEFT);
                player.player_rectangle.x -= 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.setPlayerdirection(Player.direction.FRONT);
                player.player_rectangle.y += 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.setPlayerdirection(Player.direction.BACK);
                player.player_rectangle.y -= 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (System.currentTimeMillis() - start_time_bullet > 700) {
                    start_time_bullet = System.currentTimeMillis();
                    switch (player.playerdirection) {
                        case BACK -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -280, fireball_texture));
                        case FRONT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, 280, fireball_texture));
                        case LEFT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -280, 0, fireball_texture));
                        case RIGHT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, 280, 0, fireball_texture));
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                playerDeath();
            }

        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        healthTexture.dispose();
    }

    /**
     * draws health bar according to players Hp
     */
    public void drawHealthIcons() {
        for (int i = 0; i < player.hp.getHealth(); i++) {
            batch.draw(healthTexture, i * 40, 720 - 40, 40, 40);
        }
    }

    /**
     * called to spawn a random amount of enemies on random positions
     *
     * @param enemy_amount amount of enemies to spawn
     */
    public void spawnRandomEnemies(int enemy_amount) {
        if (System.currentTimeMillis() - start_time_spawn > enemySpawnDelay) {
            enemySpawnDelay = 6000;
            start_time_spawn = System.currentTimeMillis();

            int enemy_type = random.nextInt(2);
            int direction = random.nextInt(4);
            int r_pink = random.nextInt(1, 101);
            Texture texture = healthTexture;

            switch (enemy_type) {
                case 0 -> {
                    texture = white_enemy_texture;
                }
                case 1 -> {
                    texture = blue_enemy_texture;
                    //check to avoid min and max value for random to be same
                    if (min_enemies + 1 < max_enemies) {
                        min_enemies++;
                    }
                    if (max_enemies < 10) {
                        max_enemies++;
                    }
                }
            }

            for (int i = 0; i < enemy_amount; i++) {

                int speed1 = 0, speed2 = 0, speed_x = 0, speed_y = 0, x = 0, y = 0;

                // get random speeds
                switch (enemy_type) {
                    case 0 -> {
                        //speed1 = 0;
                        speed2 = random.nextInt(80,200);
                    }
                    case 1 -> {
                        speed1 = random.nextInt(80,200);
                        speed2 = random.nextInt(20,100);
                    }
                }

                switch (direction) {
                    case 0 -> {
                        speed_x = speed1;
                        speed_y = -speed2;
                        x = random.nextInt(1280 - 96);
                        y = 720 - 96;
                    }
                    case 1 -> {
                        speed_x = speed1;
                        speed_y = speed2;
                        x = random.nextInt(1280 - 96);
                        //y = 0;
                    }
                    case 2 -> {
                        speed_x = speed2;
                        speed_y = speed1;
                        //x = 0;
                        y = random.nextInt(720 - 96);
                    }
                    case 3 -> {
                        speed_x = -speed2;
                        speed_y = speed1;
                        x = 1280 - 96;
                        y = random.nextInt(720 - 96);
                    }
                }

                waveCount++;
                if (r_pink <= 10 && waveCount > 7) {
                    gameEntities.add(new PinkEnemy(10, 1, 125, 125, 500, 500, pink_enemy_texture));
                } else {
                    gameEntities.add(new Enemy(gameEntities.size(), 1, speed_x, speed_y, x, y, texture));
                }

            }
        }

    }


    /**
     * called to spawn random items after 12s
     */
    public void spawnItems() {
        if (System.currentTimeMillis() - start_time_itemSpawn > 12000) {
            start_time_itemSpawn = System.currentTimeMillis();
            int r = random.nextInt(1, 101);

            if (r <= 100) {
                gameEntities.add(new Poison(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, greenpotion_texture));
                start_time_poison = System.currentTimeMillis();
            } else if (r <= 80) {
                gameEntities.add(new HealthPotion(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, redpotion_texture));
            } else if (r <= 90) {
                gameEntities.add(new HealthPotion(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, yellowpotion_texture));
            }

        }
    }

    /**
     * callled when the player dies, resets the game state
     */
    public void playerDeath() {
        player.hp.setHealth(4);
        gameEntities.clear();
        waveCount = 0;
        player.killsEnemiesOnContact = false;
        player.player_rectangle.x = 640 - 16;
        player.player_rectangle.y = 360;
        min_enemies = 3;
        max_enemies = 5;
        enemySpawnDelay = 1000;
        batch.setColor(Color.WHITE);
        player.setPlayerdirection(Player.direction.BACK);
    }
}
