package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class MyGdxGame extends ApplicationAdapter {

    Texture enemy09_texture;
    TextureRegion enemy09_front;
    Texture blue_enemy_texture;

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture healthTexture;
    long start_time_initial = System.currentTimeMillis();
    long start_time = System.currentTimeMillis();
    long start_time_2 = System.currentTimeMillis();
    long start_time_spawn = System.currentTimeMillis();
    InputProcessor inputProcessor = new InputProcessor();
    Texture redpotion_texture;
    TextureRegion redpotion;
    Rectangle redpotion_rectangle;
    Texture background_texture;
    boolean zeichneGegner;
    Player player;
    Random random = new Random();
    Bullet bullet;
    boolean fireball_draw = false;
    Texture fireball_texture;
    Rectangle fireball_rectangle;
    int c;
    boolean is_paused = false;

    // game entities or enemies ?
    ArrayList<GameEntity> gameEntities = new ArrayList<>();

    @Override
    public void create() {
        //Texturen
        healthTexture = new Texture("heart.png");
        enemy09_texture = new Texture("Enemy 09-1.png");
        redpotion_texture = new Texture("red potion.png");
        background_texture = new Texture("duuh69.png");

        fireball_texture = new Texture("myBall.png");

        //Spieler
        player = new Player();

        fireball_rectangle = new Rectangle(player.player_rectangle.x, player.player_rectangle.y, 32, 32);
        //Enemy Texture Region
        enemy09_front = new TextureRegion(enemy09_texture, 0, 0, 32, 32);

        //Red Potion Texture Region
        redpotion = new TextureRegion(redpotion_texture, 0, 0, 16, 16);


        //Kamera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);

        redpotion_rectangle = new Rectangle(100, 500, 64, 64);
        // Input Prozessor
        Gdx.input.setInputProcessor(inputProcessor);

        bullet = new Bullet();

        //draw random enemies
        spawnRandomEnemies(5);

        blue_enemy_texture = new Texture("Enemy 11-1.png");

    }

    @Override
    public void render() {

        //Initalize scene
        ScreenUtils.clear(216, 158, 85, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Start des Draw Prozess
        batch.begin();

        // draw background
        batch.draw(background_texture, 0, 0);

        // draw all items
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            if (e.getType() == GameEntity.entityType.ITEM) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        // draw player
        if (player.show_player_right)
            batch.draw(player.player_walk_right, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        else if (player.show_player_left)
            batch.draw(player.player_walk_left, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        else if (player.show_player_back)
            batch.draw(player.player_walk_back, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        else if (player.show_player_front)
            batch.draw(player.player_walk_front, player.player_rectangle.x, player.player_rectangle.y, 96, 96);

        // draw all enemies
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            if (e.getType() == GameEntity.entityType.ENEMY) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        // draw all bullets
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            if (e.getType() == GameEntity.entityType.BULLET) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        //Draw Hp Bar
        drawHealthIcons();

        //Ende Des Draw Prozess
        batch.end();

        // spawn enemies
        spawnRandomEnemies(5);

        //spawn Item
        spawnItem();

        //Movement der Gegner
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            Rectangle r = new Rectangle(e.getx(), e.gety(), 96, 96);

            e.move(player, gameEntities, Gdx.graphics.getDeltaTime());

            //damage the player if he has contact with enemy
            if (e.getType() == GameEntity.entityType.ENEMY && player.player_rectangle.overlaps(r)) {
                if (System.currentTimeMillis() - start_time > 2000 || System.currentTimeMillis() - start_time == 0) {
                    start_time = System.currentTimeMillis();
                    player.hp.decrease(1);
                }
            }

            //increase health of player if he walks into health potion
            if (e.getType() == GameEntity.entityType.ITEM && player.player_rectangle.overlaps(r)) {
                player.hp.increase(1);
                gameEntities.remove(i);
            }


        }

        //If player dies
        if (player.hp.getHealth() == 0) {
            player.hp.increase(4);
            gameEntities.clear();
        }
        // Wenn player ausserhalb des Screens im X bereich
        if (player.player_rectangle.x < 0) player.player_rectangle.x = 0;
        if (player.player_rectangle.x > 1280 - 96) player.player_rectangle.x = 1280 - 96;

        // Wenn player ausserhalb des Screens im Y bereich
        if (player.player_rectangle.y < 0) player.player_rectangle.y = 0;
        if (player.player_rectangle.y + 96 > 720) player.player_rectangle.y = 720 - 96;

        //Input
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.show_player_left = false;
            player.show_player_right = true;
            player.show_player_back = false;
            player.show_player_front = false;
            player.player_rectangle.x += 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.show_player_right = false;
            player.show_player_left = true;
            player.show_player_back = false;
            player.show_player_front = false;
            player.player_rectangle.x -= 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.show_player_right = false;
            player.show_player_left = false;
            player.show_player_front = false;
            player.show_player_back = true;
            player.player_rectangle.y += 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.show_player_right = false;
            player.show_player_left = false;
            player.show_player_back = false;
            player.show_player_front = true;
            player.player_rectangle.y -= 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
        }

        //Debug
    }

    @Override
    public void dispose() {
        batch.dispose();
        healthTexture.dispose();
    }

    public void drawHealthIcons() {
        for (int i = 0; i < player.hp.getHealth(); i++) {
            batch.draw(healthTexture, i * 40, 720 - 40, 40, 40);
        }
    }


    public void spawnRandomEnemies(int enemy_amount) {
        if (System.currentTimeMillis() - start_time_spawn > 10000) {
            start_time_spawn = System.currentTimeMillis();

            int enemy_type = random.nextInt(2);
            int direction = random.nextInt(4);
            Texture texture = healthTexture;

            switch (enemy_type){
                case 0 -> {
                    texture = enemy09_texture;
                }
                case 1 -> {
                    texture = blue_enemy_texture;
                }
            }

            for (int i = 0; i < enemy_amount; i++) {

                int speed1 = 0, speed2 = 0, speed_x = 0, speed_y = 0, x = 0, y = 0;

                // get random speeds
                switch (enemy_type) {
                    case 0 -> {
                        speed1 = 0;
                        speed2 = random.nextInt(70) + 80;
                    }
                    case 1 -> {
                        speed1 = random.nextInt(70) + 90;
                        speed2 = random.nextInt(20) + 30;
                    }
                }

                switch(direction) {
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
                        y = 0;
                    }
                    case 2 -> {
                        speed_x = speed2;
                        speed_y = speed1;
                        x = 0;
                        y = random.nextInt(720 - 96);
                    }
                    case 3 -> {
                        speed_x = -speed2;
                        speed_y = speed1;
                        x = 1280 - 96;
                        y = random.nextInt(720 - 96);
                    }
                }

                gameEntities.add(new Enemy(gameEntities.size(), 1, speed_x, speed_y, x, y, texture));

            }
        }

    }


    public void spawnItem() {
        if (random.nextInt(1000) == 1) {
            gameEntities.add(new Item(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, redpotion_texture));
        }
    }

    public void drawFireBall() {
        if (player.show_player_front) {
            batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y, 32, 32);
            fireball_rectangle.y -= 100 * Gdx.graphics.getDeltaTime();
        } else if (player.show_player_back) {
            batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y, 32, 32);
            fireball_rectangle.y += 100 * Gdx.graphics.getDeltaTime();
        } else if (player.show_player_left) {
            batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y, 32, 32);
            fireball_rectangle.x -= 100 * Gdx.graphics.getDeltaTime();
        } else if (player.show_player_right) {
            batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y, 32, 32);
            fireball_rectangle.x += 100 * Gdx.graphics.getDeltaTime();
        }
    }
}
