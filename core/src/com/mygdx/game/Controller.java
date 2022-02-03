package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Items.HealthPotion;
import com.mygdx.game.Items.Poison;
import com.mygdx.game.Items.fastshoot;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Controller extends ApplicationAdapter {

    // game entities
    ArrayList<GameEntity> gameEntities = new ArrayList<>();
    //camera
    SpriteBatch batch;
    OrthographicCamera camera;
    //player
    Player player;
    //player textures
    //InputProcessor
    InputProcessor inputProcessor;
    //heart for hp bar
    Texture healthTexture;
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
    long start_time_poison;
    long start_time_fastshoot;
    long start_time_deathscreen;
    //Items
    Texture redpotion_texture;
    Texture greenpotion_texture;
    Texture yellowpotion_texture;
    Texture bluepotion_texture;
    //background textures
    Texture background_texture;
    //random for random generated numbers
    Random random = new Random();
    //fireball texture
    Texture fireball_texture;
    //counters for increased hardness
    int min_enemies;
    int max_enemies;
    int enemySpawnDelay = 1000;
    //boolean if player took damage to control damage effect
    boolean playerTookDamage;
    //cooldown for shooting
    int shootcooldown;
    //Sounds
    Sound flameAttack;
    Sound hit;
    Sound death;
    Sound sip;
    BitmapFont font;
    //config
    Preferences config;
    int itemscollected;
    int enemieskilled;
    int waveCount;
    boolean firstspawn = true;
    int itemsonfield;
    boolean playerDead = false;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter2;
    BitmapFont font2;
    BitmapFont font3;
    @Override
    public void create() {
        //Camera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);

        //config
        config = Gdx.app.getPreferences("ghostinvadorsconfig");
        if (!config.getBoolean("ConfigExists")) initConfig();


        //Spieler
        player = new Player(config.getInteger("PlayerHP"), config.getInteger("MaxHealth"));

        inputProcessor = new InputProcessor(player);

        //Input Processor
        Gdx.input.setInputProcessor(inputProcessor);
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
        bluepotion_texture = new Texture("blue  potion.png");

        //enemy textures
        white_enemy_texture = new Texture("Enemy 09-1.png");
        blue_enemy_texture = new Texture("Enemy 11-1.png");
        pink_enemy_texture = new Texture("Enemy 12-1.png");

        //Sounds
        flameAttack = Gdx.audio.newSound(Gdx.files.internal("Flame Attack (Terraria Sound) - Sound Effect for editing.mp3"));
        hit = Gdx.audio.newSound(Gdx.files.internal("Male Player Hit (Nr. 1 _ Terraria Sound) - Sound Effect for editing.mp3"));
        death = Gdx.audio.newSound(Gdx.files.internal("Player Killed (Terraria Sound) - Sound Effect for editing.mp3"));
        sip = Gdx.audio.newSound(Gdx.files.internal("Potion Use_Drink (Terraria Sound) - Sound Effect for editing.mp3"));

        //font for stats
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        enemySpawnDelay = config.getInteger("EnemyWaveCooldown(in ms)");
        shootcooldown = config.getInteger("shootcooldown");
        min_enemies = config.getInteger("MinAmountOfEnemies");
        max_enemies = config.getInteger("MaxAmountOfEnemies");

        //font shit
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Quintessential-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 60;
        parameter2.color = Color.RED;
        parameter2.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        font2 = generator.generateFont(parameter);
        font3 = generator.generateFont(parameter2);

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
                //batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
                e.setStateTime(e.getStateTime() + Gdx.graphics.getDeltaTime());
                TextureRegion currentFrame = e.getAnimation().getKeyFrame(e.getStateTime(), true);
                batch.draw(currentFrame, e.getx(), e.gety(), r.width, r.height);
            }
        }
        // draw player
        if (playerTookDamage) batch.setColor(Color.RED);

        if (System.currentTimeMillis() - start_time_damageSplash > 2000) {
            start_time_damageSplash = System.currentTimeMillis();
            batch.setColor(Color.WHITE);
            playerTookDamage = false;
        }

        if (player.playerdirection == null) player.playerdirection = Player.direction.BACK;
        if (playerDead && System.currentTimeMillis() - start_time_deathscreen > 5000) {
            font3.draw(batch,"You Died",520,400);
            font2.draw(batch, "Enemies Killed: " ,520,400-60);
            font2.draw(batch, "Items Collected: " ,520,400-90);
        }

        switch (player.playerdirection) {
            case WALKINGBACK -> {
                player.stateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame = player.walkFrontAnimation.getKeyFrame(player.stateTime, true);
                batch.draw(currentFrame, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            }
            case WALKINGFRONT -> {
                player.stateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame = player.walkBackAnimation.getKeyFrame(player.stateTime, true);
                batch.draw(currentFrame, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            }
            case WALKINGLEFT -> {
                player.stateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame = player.walkLeftAnimation.getKeyFrame(player.stateTime, true);
                batch.draw(currentFrame, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            }
            case WALKINGRIGHT -> {
                player.stateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame = player.walkRightAnimation.getKeyFrame(player.stateTime, true);
                batch.draw(currentFrame, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            }
            case BACK -> batch.draw(player.player_front, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            case FRONT -> batch.draw(player.player_back, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            case LEFT -> batch.draw(player.player_left, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
            case RIGHT -> batch.draw(player.player_right, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        }


        // draw all enemies
        //batch.setColor(1, 1, 1, 0.9f);
        //
        batch.setColor(Color.WHITE);
        for (GameEntity e : gameEntities) {
            if (e.getEntityType() == GameEntity.entityType.ENEMY || e.getEntityType() == GameEntity.entityType.PINKENEMY) {
                Rectangle r = e.getRectangle();
                e.setStateTime(e.getStateTime() + Gdx.graphics.getDeltaTime());
                TextureRegion currentFrame = e.getAnimation().getKeyFrame(e.getStateTime(), true);
                batch.draw(currentFrame, e.getx(), e.gety(), r.width, r.height);

                //batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        // draw all bullets
        for (GameEntity e : gameEntities) {
            if (e.getEntityType() == GameEntity.entityType.BULLET) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTexture(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        //WaveCounter
        font2.draw(batch, "" + waveCount, 1265 - font.getScaleX(), 700);


        //Draw Hp Bar
        drawHealthIcons();

        //playerTexture
        switch (inputProcessor.player_texture_index) {
            case 1 -> player.setTexture(Player.playerTexture.MALERED);
            case 2 -> player.setTexture(Player.playerTexture.FEMALEPINK);
            case 3 -> player.setTexture(Player.playerTexture.FEMALEPINK2);
            case 4 -> player.setTexture(Player.playerTexture.MALEBLUE);
            case 5 -> player.setTexture(Player.playerTexture.FEMALERED);
        }

        //draw pause screen
        if (inputProcessor.isPaused()) {
            batch.setColor(Color.GRAY);
            font2.draw(batch, "Highscore: " + config.getString("highscore") + " Waves", 10, 600 + 50);
            font2.draw(batch, "Items Collected: " + config.getString("ItemsCollected"), 10, 585 + 40);
            //font.draw(batch, "Enemies killed: " + config.getString("enemieskilled"), 10, 570 + 50);
        } else batch.setColor(Color.WHITE);

//end of draw process
        batch.end();


        //check if Game is paused
        if (!inputProcessor.isPaused()) {

//loop through gameEntities arraylist
            for (int i = 0; i < gameEntities.size(); i++) {
                GameEntity e = gameEntities.get(i);
                Rectangle r = new Rectangle(e.getx(), e.gety(), 96, 96);

                e.move(player, gameEntities, Gdx.graphics.getDeltaTime());

                //damage the player if he has contact with enemy
                if (e.getEntityType() == GameEntity.entityType.ENEMY && player.player_rectangle.overlaps(r)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        player.health.decrease(1);
                        hit.play();
                        playerTookDamage = true;
                    }
                    if (player.killsEnemiesOnContact) {
                        //noinspection SuspiciousListRemoveInLoop
                        gameEntities.remove(i);

                    }
                }
                if (e.getEntityType() == GameEntity.entityType.PINKENEMY && r.overlaps(player.player_rectangle)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        player.health.decrease(1);
                        hit.play();
                        playerTookDamage = true;
                        //noinspection SuspiciousListRemoveInLoop
                        gameEntities.remove(i);
                    }
                }

                //execute onContact of Item when in contact & remove item from screen
                if (e.getEntityType() == GameEntity.entityType.ITEM && player.player_rectangle.overlaps(r)) {
                    sip.play();
                    e.onContact();
                    //noinspection SuspiciousListRemoveInLoop
                    gameEntities.remove(i);
                    itemscollected++;
                    itemsonfield--;
                }
                if (e.getEntityType() == GameEntity.entityType.ENEMY || e.getEntityType() == GameEntity.entityType.PINKENEMY)
                    enemieskilled = e.getEnemiesKilled();


            }
//end of arraylist loop

            // spawn enemies

            spawnRandomEnemies(random.nextInt(min_enemies,max_enemies), 1);

            //spawn Item
            if (itemsonfield < 4) spawnItems();

            //Check if poison effect is over
            if (start_time_poison != 0) {
                if (System.currentTimeMillis() - start_time_poison > 7000) {
                    player.killsEnemiesOnContact = false;
                    start_time_poison = 0;
                }
            }
            if (player.shootSpeedIncreased){
                shootcooldown = shootcooldown - 200;
                player.shootSpeedIncreased = false;
            }
            //Check if fastshoot effect is over
            if (start_time_fastshoot != 0) {
                if (System.currentTimeMillis() - start_time_fastshoot > 7000) {
                    player.shootSpeedIncreased = false;
                    start_time_fastshoot = 0;
                    shootcooldown = config.getInteger("shootcooldown");
                }
            }


            //update stats
            updateStats();


            //If player dies
            if (player.health.getHealth() == 0) {
                start_time_deathscreen = System.currentTimeMillis();
                playerDeath(true);
                playerDead = true;
            }


            // Wenn player ausserhalb des Screens im X bereich
            if (player.player_rectangle.x < 0) player.player_rectangle.x = 0;
            if (player.player_rectangle.x > 1280 - 96) player.player_rectangle.x = 1280 - 96;

            // Wenn player ausserhalb des Screens im Y bereich
            if (player.player_rectangle.y < 0) player.player_rectangle.y = 0;
            if (player.player_rectangle.y + 96 > 720) player.player_rectangle.y = 720 - 96;

//Input
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.setPlayerdirection(Player.direction.WALKINGRIGHT);
                player.player_rectangle.x += 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.setPlayerdirection(Player.direction.WALKINGLEFT);
                player.player_rectangle.x -= 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.setPlayerdirection(Player.direction.WALKINGFRONT);
                player.player_rectangle.y += 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.setPlayerdirection(Player.direction.WALKINGBACK);
                player.player_rectangle.y -= 250 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    switch (player.playerdirection) {
                        case BACK, WALKINGBACK -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -600, fireball_texture));
                        case FRONT, WALKINGFRONT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, 600, fireball_texture));
                        case LEFT, WALKINGLEFT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -600, 0, fireball_texture));
                        case RIGHT, WALKINGRIGHT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, 600, 0, fireball_texture));
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, 280, 0, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -280, 0, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();

                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, 280, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -280, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) playerDeath(false);
            if (Gdx.input.isKeyPressed(Input.Keys.F5)) initConfig();


        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        healthTexture.dispose();
        white_enemy_texture.dispose();
        blue_enemy_texture.dispose();
        pink_enemy_texture.dispose();
        greenpotion_texture.dispose();
        redpotion_texture.dispose();
        yellowpotion_texture.dispose();
        background_texture.dispose();
        fireball_texture.dispose();
        flameAttack.dispose();
        death.dispose();
        sip.dispose();
        font2.dispose();
    }

    /**
     * draws health bar according to players Hp
     */
    public void drawHealthIcons() {
        for (int i = 0; i < player.health.getHealth(); i++) {
            batch.draw(healthTexture, i * 40, 720 - 40, 40, 40);
        }
    }

    /**
     * called to spawn a random amount of enemies on random positions
     *
     * @param enemy_amount amount of enemies to spawn
     */
    public void spawnRandomEnemies(int enemy_amount, int pink_enemy_amount) {
        if (System.currentTimeMillis() - start_time_spawn > enemySpawnDelay || firstspawn) {
            boolean pink_spawned = false;
            firstspawn = false;
            if (enemySpawnDelay > 3000 || enemySpawnDelay == 1000) {
                if (waveCount <= 15) enemySpawnDelay = 10000;
                else enemySpawnDelay -= random.nextInt(500, 1000);
            }
            start_time_spawn = System.currentTimeMillis();

            int enemy_type = random.nextInt(2);
            int direction = random.nextInt(4);
            Texture texture = healthTexture;

            switch (enemy_type) {
                case 0 -> texture = white_enemy_texture;
                case 1 -> {
                    texture = blue_enemy_texture;
                    //check to avoid min and max value for random to be same
                    if (min_enemies + 1 < max_enemies) {
                        min_enemies++;
                    }
                    if (max_enemies < 13 && random.nextBoolean()) {
                        max_enemies++;
                    }
                }
            }

            for (int i = 0; i < enemy_amount; i++) {

                int speed1 = 0, speed2 = 0, speed_x = 0, speed_y = 0, x = 0, y = 0;

                // get random speeds
                switch (enemy_type) {
                    case 0 -> //speed1 = 0;
                            speed2 = random.nextInt(80, 300);
                    case 1 -> {
                        speed1 = random.nextInt(80, 250);
                        speed2 = random.nextInt(20, 150);
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


                if (random.nextBoolean() && waveCount > 9 && !pink_spawned|| config.getBoolean("OnlyPinkEnemies")) {
                    //for (int j = 0; j < pink_enemy_amount;j++);
                    gameEntities.add(new PinkEnemy(gameEntities.size(), 1, 200, 200, x, y, pink_enemy_texture));
                    pink_spawned = true;

                }

                gameEntities.add(new Enemy(gameEntities.size(), 1, speed_x, speed_y, x, y, texture));

            }
            waveCount++;
        }

    }


    /**
     * called to spawn random items after 12s
     */
    public void spawnItems() {
        if (System.currentTimeMillis() - start_time_itemSpawn > config.getInteger("ItemSpawnCooldown")) {
            start_time_itemSpawn = System.currentTimeMillis();

            if (random.nextInt(0, 100) <= 40) {
                gameEntities.add(new HealthPotion(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, start_time_itemSpawn, redpotion_texture));
            } else if (random.nextInt(0, 100) <= 40) {
                gameEntities.add(new Poison(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, System.currentTimeMillis(), greenpotion_texture));
                start_time_poison = System.currentTimeMillis();
            } else if (random.nextInt(0, 100) <= 50) {
                gameEntities.add(new fastshoot(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, System.currentTimeMillis(), bluepotion_texture, gameEntities));
                start_time_fastshoot = System.currentTimeMillis();
            }
            itemsonfield++;
        }
    }

    /**
     * callled when the player dies, resets the game state
     */
    public void playerDeath(boolean deathSound) {
        if (deathSound) death.play();
        player.health.setHealth(4);
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

    public void updateStats() {
        if (waveCount > config.getInteger("highscore")) {
            config.putInteger("highscore", waveCount);
        }
        config.putInteger("ItemsCollected", config.getInteger("ItemsCollected") + itemscollected);
        itemscollected = 0;
        config.flush();
    }

    public void initConfig() {
        config.putString("A", """

                All entries have to be the same data type as the default values.\s
                Time is set in milli seconds. The minimum and maximum amount of enemies can not be the same.\s
                To reset the config press f5 in game, set ConfigExists false, or delete this file.""".indent(1));
        config.putInteger("shootcooldown", 700);
        config.putInteger("MinAmountOfEnemies", 5);
        config.putInteger("MaxAmountOfEnemies", 9);
        config.putBoolean("OnlyPinkEnemies", false);
        config.putInteger("PlayerHP", 4);
        config.putInteger("MaxHealth", 10);
        config.putInteger("EnemyWaveCooldown(in ms)", 8000);
        config.putInteger("ItemSpawnCooldown(in ms)", 15000);
        config.putBoolean("ConfigExists", true);
        config.putInteger("ItemSpawnCooldown", 15000);
        //config.putString("playerSkin","default");
        //config.putInteger("maxSpeedWhiteGhost",200);
        //config.putInteger("maxSpeedBlueGhost",200);
        config.flush();
    }
}
