package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.github.acanthite.gdx.graphics.g2d.FreeTypeSkin;
import com.mygdx.game.Items.HealthPotion;
import com.mygdx.game.Items.Poison;
import com.mygdx.game.Items.fastshoot;
import com.mygdx.game.Widgets.*;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Controller extends ApplicationAdapter {

    // game entities arraylist
    ArrayList<GameEntity> gameEntities = new ArrayList<>();
    //camera
    SpriteBatch batch;
    OrthographicCamera camera;
    //player
    Player player;
    //InputProcessor
    InputProcessor inputProcessor;
    //heart for hp bar
    Texture healthTexture;
    //enemie textures
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
    long star_time_run;
    long stop_time_run;
    float time_run;
    float time_run_s_2;
    //Items
    Texture redpotion_texture;
    Texture greenpotion_texture;
    Texture yellowpotion_texture;
    Texture bluepotion_texture;
    //background textures
    Texture background_texture_default;
    Texture background_texture_desert;
    Texture background_texture_desertCustom;
    Texture background_texture_white;
    Texture background_texture_grass;
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
    //config
    Preferences config;
    int itemscollected;
    int enemieskilled;
    int waveCount;
    boolean firstspawn = true;
    int itemsonfield;
    //boolean playerDead = false;
    int itemspawncooldown;
    boolean spawnratereduced = false;
    int itemscollected_in_run;
    int enemieskilled2;
    //stage, Widgets
    Stage stage;
    InputMultiplexer multiplexer;
    Table table;
    TextField textfield_minamountofenemies;
    TextField textfield_maxamountofenemies;
    CheckBox checkBox_onlyPinkGuys;
    CheckBox godModeToggle;
    TextButtonC back_button;
    Skin default_skin;
    TextButtonC resume_button;
    TextButtonC fullscreen_button;
    TextButtonC settings_button;
    TextButtonC exit_button;
    TextButtonC apply_button;
    TextButtonC reset_button;
    SelectBox<String> background_selectbox;
    Label label_minamountofenemies;
    Label label_maxamountofenemies;
    //fonts
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter2;
    BitmapFont font2;
    BitmapFont font3;
    //gameState
    boolean fullscreen = false;
    boolean gameIsStarted = false;
    boolean deathScreen = false;
    boolean isPaused = false;
    boolean settingsScreen = false;
    ClickListener clicklestener = new ClickListener();

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
        //stage
        stage = new Stage();
        //call initializing methods
        initializeSounds();
        initializeTextures();
        initializeButtons();
        initializeFonts();
        loadFromConfig();
        //input processing
        inputProcessor = new InputProcessor(player);
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        //start timer
        star_time_run = System.currentTimeMillis();
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
        //batch.draw(background_texture_default, 0, 0);
        switch (config.getString("BackGroundTexture")) {
            case "default" -> batch.draw(background_texture_default, 0, 0);
            case "desert" -> batch.draw(background_texture_desert, 0, 0);
            case "desertCustom" -> batch.draw(background_texture_desertCustom, 0, 0);
            case "grass" -> batch.draw(background_texture_grass, 0, 0);
        }

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
        //make player red if he takes damage
        if (System.currentTimeMillis() - start_time_damageSplash > 2000) {
            start_time_damageSplash = System.currentTimeMillis();
            batch.setColor(Color.WHITE);
            playerTookDamage = false;
        }
        //default player direction back
        if (player.playerdirection == null) player.playerdirection = Player.direction.BACK;

        if (!inputProcessor.deathScreen && inputProcessor.gameIsStarted) {

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
        }

        // draw all enemies
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
        font2.draw(batch, "" + waveCount, 1265 - 10, 700);

        //Draw Hp Bar
        drawHealthIcons();

        batch.setColor(Color.LIGHT_GRAY);

        //pause screen
        boolean buttonsDisplayed = false;
        if (inputProcessor.isPaused) {
            batch.setColor(Color.GRAY);
            if (!settingsScreen) {
                font2.draw(batch, "Highscore: " + config.getString("highscore") + " Waves", 570, 50);
                font2.draw(batch, "Items Collected: " + config.getString("ItemsCollected"), 570, 50 + 25);
            }
            if (!buttonsDisplayed) {
                setPauseMenuButtonsVisibility(true);
                buttonsDisplayed = true;
            }
            //settingsScreen = false;
        } else batch.setColor(Color.WHITE);
        if (!inputProcessor.isPaused) {
            setPauseMenuButtonsVisibility(false);
            buttonsDisplayed = false;
        }


        //Halt execution if game is not started by player
        if (!inputProcessor.gameIsStarted) {
            batch.setColor(Color.LIGHT_GRAY);
            font3.draw(batch, "Ghost Invaders", 465, 400);
            font2.draw(batch, "Press Space to start", 570, 60);
            batch.end();
            return;
        }
        if (inputProcessor.deathScreen) {
            batch.setColor(Color.GRAY);
            font2.draw(batch, "Press Space to continue", 570, 60);
            font3.draw(batch, "You Died", 530, 400);
            font2.draw(batch, "Enemies Killed: " + enemieskilled2, 570, 310 - 25);
            font2.draw(batch, "Items Collected: " + itemscollected_in_run, 570, 310);
            font2.draw(batch, "Time: " + ((int) time_run) + ":" + ((int) time_run_s_2), 570, 310 - 50);
            star_time_run = System.currentTimeMillis();
            batch.end();
            return;
        }
        if (settingsScreen) {
            //font2.draw(batch, "Press Escape to exit", 570, 60);
            setPauseMenuButtonsVisibility(false);

        }

        //draw stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

//end of draw process
        batch.end();

        //Button Input
        if (resume_button.isPressed()) inputProcessor.isPaused = false;
        if (exit_button.isPressed()) Gdx.app.exit();

        //check if Game is paused
        if (!inputProcessor.isPaused) {

//loop through gameEntities arraylist
            for (int i = 0; i < gameEntities.size(); i++) {
                GameEntity e = gameEntities.get(i);
                Rectangle r = new Rectangle(e.getx(), e.gety(), 96, 96);

                e.move(player, gameEntities, Gdx.graphics.getDeltaTime());

                //damage the player if he has contact with enemy
                if (e.getEntityType() == GameEntity.entityType.ENEMY && player.player_rectangle.overlaps(r)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        if (!config.getBoolean("GodMode")) {
                            player.health.decrease(1);
                            hit.play();
                            playerTookDamage = true;
                        }

                    }
                    if (player.killsEnemiesOnContact) {
                        //noinspection SuspiciousListRemoveInLoop
                        gameEntities.remove(i);

                    }
                }
                if (e.getEntityType() == GameEntity.entityType.PINKENEMY && r.overlaps(player.player_rectangle)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        if (!config.getBoolean("GodMode")) {
                            player.health.decrease(1);
                            hit.play();
                            playerTookDamage = true;
                        }

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
                    itemscollected_in_run++;
                    itemsonfield--;
                }
                //increase enemieskilled counter
                if (e.getEntityType() == GameEntity.entityType.BULLET)
                    enemieskilled = enemieskilled + e.getEnemiesKilled();

            }
//end of arraylist loop

            // spawn enemies
            spawnRandomEnemies(random.nextInt(min_enemies, max_enemies));

            //spawn Item
            if (itemsonfield < 5) spawnItems();

            //Check if poison effect is over
            if (start_time_poison != 0) {
                if (System.currentTimeMillis() - start_time_poison > 7000) {
                    player.killsEnemiesOnContact = false;
                    start_time_poison = 0;
                }
            }
            //check if fastshoot is active
            if (player.shootSpeedIncreased && shootcooldown >= 200) {
                shootcooldown = shootcooldown - 250;
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
            updateConfig();


            if (enemieskilled != 0) enemieskilled2 = enemieskilled;

            //If player dies
            if (player.health.getHealth() == 0) {
                playerDeath(true);
                enemieskilled = 0;
                //playerDead = true;
                inputProcessor.deathScreen = true;
                stop_time_run = System.currentTimeMillis();
                long time_run_ms = stop_time_run - star_time_run;
                float time_run_s = time_run_ms / 1000;
                time_run = time_run_s / 60;
                time_run_s_2 = time_run_ms % 60;
                //TODO fix timer
            }

            playerCollisionScreenBounds();


//Input
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.setPlayerdirection(Player.direction.WALKINGRIGHT);
                player.player_rectangle.x += config.getInteger("MovementSpeed") * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.setPlayerdirection(Player.direction.WALKINGLEFT);
                player.player_rectangle.x -= config.getInteger("MovementSpeed") * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.setPlayerdirection(Player.direction.WALKINGFRONT);
                player.player_rectangle.y += config.getInteger("MovementSpeed") * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.setPlayerdirection(Player.direction.WALKINGBACK);
                player.player_rectangle.y -= config.getInteger("MovementSpeed") * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    switch (player.playerdirection) {
                        case BACK, WALKINGBACK -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -290, fireball_texture));
                        case FRONT, WALKINGFRONT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, 290, fireball_texture));
                        case LEFT, WALKINGLEFT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -290, 0, fireball_texture));
                        case RIGHT, WALKINGRIGHT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, 290, 0, fireball_texture));
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, 290, 0, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -290, 0, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, 290, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -290, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) playerDeath(false);
            if (Gdx.input.isKeyPressed(Input.Keys.F5)) initConfig();

        }

    }

    /**
     * disposes textures to avoid memory leak
     */
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
        background_texture_default.dispose();
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
    public void spawnRandomEnemies(int enemy_amount) {
        if (System.currentTimeMillis() - start_time_spawn > enemySpawnDelay || firstspawn) {
            boolean pink_spawned = false;
            firstspawn = false;
            start_time_spawn = System.currentTimeMillis();

            //increase difficulty
            if (enemySpawnDelay > 6000) {
                if (waveCount <= 17) enemySpawnDelay = 10000;
                else enemySpawnDelay -= random.nextInt(600, 800);
            }
            //check to avoid min and max value for random to be same
            if (random.nextBoolean()) {
                if (min_enemies + 1 < max_enemies && max_enemies >= 10) {
                    min_enemies++;
                }
                if (max_enemies < 13 && random.nextBoolean()) {
                    max_enemies++;
                }
            }
            int enemy_type = random.nextInt(2);
            int direction = random.nextInt(4);
            Texture texture = healthTexture;

            switch (enemy_type) {
                case 0 -> texture = white_enemy_texture;
                case 1 -> texture = blue_enemy_texture;

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
                if (random.nextBoolean() && waveCount > 14 && !pink_spawned || config.getBoolean("OnlyPinkEnemies")) {
                    gameEntities.add(new PinkEnemy(gameEntities.size(), 1, 200, 200, x, y, pink_enemy_texture));
                    pink_spawned = true;

                }
                //add enemy
                gameEntities.add(new Enemy(gameEntities.size(), 1, speed_x, speed_y, x, y, texture));
            }
            waveCount++;
        }
    }


    /**
     * called to spawn random item
     */
    public void spawnItems() {
        if (System.currentTimeMillis() - start_time_itemSpawn > itemspawncooldown) {
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
        //min_enemies = config.getInteger("MinAmountOfEnemies");
        //max_enemies = config.getInteger("MaxAmountOfEnemies");
        batch.setColor(Color.WHITE);
        player.setPlayerdirection(Player.direction.BACK);
        //enemySpawnDelay = config.getInteger("EnemyWaveCooldown(in ms)");
        loadFromConfig();
    }

    /**
     * update values for highscore and items collected in config file
     */
    public void updateConfig() {
        if (waveCount > config.getInteger("highscore")) {
            config.putInteger("highscore", waveCount);
        }
        config.putInteger("ItemsCollected", config.getInteger("ItemsCollected") + itemscollected);
        itemscollected = 0;
        config.flush();
    }

    /**
     * initialize Configuration File with default values
     */
    public void initConfig() {
        config.putString("A", """
                All entries have to be the same data type as the default values.\s
                Time is set in milli seconds. The minimum and maximum amount of enemies can not be the same.\s
                To reset the config press f5 in game, set ConfigExists false, or delete this file.""".indent(1));
        config.putInteger("shootcooldown", 700);
        config.putInteger("MinAmountOfEnemies", 4);
        config.putInteger("MaxAmountOfEnemies", 6);
        config.putBoolean("OnlyPinkEnemies", false);
        config.putInteger("PlayerHP", 4);
        config.putInteger("MaxHealth", 10);
        config.putInteger("EnemyWaveCooldown(in ms)", 12000);
        config.putInteger("ItemSpawnCooldown(in ms)", 15000);
        config.putBoolean("ConfigExists", true);
        config.putInteger("MovementSpeed", 250);
        config.putBoolean("GodMode", false);
        config.putString("BackGroundTexture", "default.png");
        config.flush();
    }

    /**
     * load values of config file into variables
     */
    public void loadFromConfig() {
        enemySpawnDelay = config.getInteger("EnemyWaveCooldown(in ms)");
        shootcooldown = config.getInteger("shootcooldown");
        min_enemies = config.getInteger("MinAmountOfEnemies");
        max_enemies = config.getInteger("MaxAmountOfEnemies");
        itemspawncooldown = config.getInteger("ItemSpawnCooldown(in ms)");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Controller() {
    }

    /**
     * toggle state of Fullscreen
     */
    public void toggleFullscreen() {
        if (!fullscreen) {
            Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
            fullscreen = true;
            if (!Gdx.graphics.setFullscreenMode(displayMode)) {
                // switching to full-screen mode failed
            }

        } else {
            Gdx.graphics.setWindowedMode(1280, 720);
            fullscreen = false;
        }
    }

    /**
     * initialize buttons for pause and settings screen,
     * positions and sizes are set and buttons are set to invisible,
     * if needed a InputListener is added
     */
    public void initializeButtons() {
        //skin
        default_skin = new FreeTypeSkin(Gdx.files.internal("skin.json"));
        //Pause Menu
        resume_button = new TextButtonC("Resume", default_skin, 597, 450, 85, 25, false);
        stage.addActor(resume_button);
        fullscreen_button = new TextButtonC("Fullscreen", default_skin, 597, 450 - 35, 85, 25, false);
        stage.addActor(fullscreen_button);
        settings_button = new TextButtonC("Settings", default_skin, 597, 450 - 70, 85, 25, false);
        stage.addActor(settings_button);
        exit_button = new TextButtonC("Exit", default_skin, 597, 450 - 105, 85, 25, false);
        stage.addActor(exit_button);
        //Settings Menu
        godModeToggle = new CheckBoxC("Godmode", default_skin, 570, 600, !config.getBoolean("GodMode"), false);
        stage.addActor(godModeToggle);
        back_button = new TextButtonC("Back", default_skin, 600 - 90, 60, 86, 25, false);
        stage.addActor(back_button);
        apply_button = new TextButtonC("Apply", default_skin,600,60,86,25,false);
        stage.addActor(apply_button);
        checkBox_onlyPinkGuys = new CheckBoxC("Only Pink Enemies", default_skin, 570, 600 - 23, !config.getBoolean("OnlyPinkEnemies"), false);
        stage.addActor(checkBox_onlyPinkGuys);
        reset_button = new TextButtonC("Reset", default_skin, 600 + 90, 60, 86, 25, false);
        stage.addActor(reset_button);
        textfield_minamountofenemies = new TextFieldC(" ", default_skin,570,600-90,86,25,false);
        stage.addActor(textfield_minamountofenemies);
        label_minamountofenemies = new LabelC("Minimum Amount of Enemies per Wave: ", default_skin,570,600-60,false);
        stage.addActor(label_minamountofenemies);
        textfield_maxamountofenemies = new TextField("Minimum amount of Enemies", default_skin);
        label_maxamountofenemies = new LabelC("Maximum Amount of Enemies per Wave: ", default_skin,570,600-100,false);
        stage.addActor(label_maxamountofenemies);
        //background selectbox
        background_selectbox = new SelectBoxC(default_skin,570,600-140,100,25,false);
        background_selectbox.setItems("default", "desert", "desertCustom", "grass", "white");
        background_selectbox.setSelected(config.getString("BackGroundTexture"));
        stage.addActor(background_selectbox);


        fullscreen_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleFullscreen();
                return true;
            }
        });
        settings_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settingsScreen = true;
                setPauseMenuButtonsVisibility(false);
                setSettingsMenuButtonsVisibility(true);
                return true;
            }
        });
        back_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settingsScreen = false;
                setPauseMenuButtonsVisibility(true);
                setSettingsMenuButtonsVisibility(false);
                stage.setKeyboardFocus(null);
                return true;
            }
        });
        apply_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                config.putBoolean("OnlyPinkEnemies", !checkBox_onlyPinkGuys.isChecked());
                config.putBoolean("GodMode", !godModeToggle.isChecked());
                config.putString("BackGroundTexture", background_selectbox.getSelected());
                stage.setKeyboardFocus(null);
                playerDeath(false);
                System.out.println(textfield_minamountofenemies.getText());
                return true;
            }
        });
        reset_button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                initConfig();
                checkBox_onlyPinkGuys.setChecked(!config.getBoolean("OnlyPinkEnemies"));
                godModeToggle.setChecked(config.getBoolean("GodMode"));
                stage.setKeyboardFocus(null);
                playerDeath(false);
                return true;
            }
        });

    }

    /**
     * load all needed textures
     */
    public void initializeTextures() {
        //heart Texture
        healthTexture = new Texture("heart.png");

        //background textures
        background_texture_default = new Texture("default.png");
        background_texture_desert = new Texture("desert.png");
        background_texture_desertCustom = new Texture("desertCustom.png");
        background_texture_white = new Texture("white.png");
        background_texture_grass = new Texture("grass.png");

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
    }

    /**
     * creates fonts
     */
    public void initializeFonts() {
        //Fonts

        if (waveCount >= 20 && !spawnratereduced) {
            itemspawncooldown = config.getInteger("ItemSpawnCooldown(in ms)") - 5000;
            spawnratereduced = true;
        }

        //font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("QuintessentialRegular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 65;
        parameter2.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        font2 = generator.generateFont(parameter);
        font3 = generator.generateFont(parameter2);
    }

    /**
     * loads game sounds
     */
    public void initializeSounds() {
        flameAttack = Gdx.audio.newSound(Gdx.files.internal("Flame Attack (Terraria Sound) - Sound Effect for editing.mp3"));
        hit = Gdx.audio.newSound(Gdx.files.internal("Male Player Hit (Nr. 1 _ Terraria Sound) - Sound Effect for editing.mp3"));
        death = Gdx.audio.newSound(Gdx.files.internal("Player Killed (Terraria Sound) - Sound Effect for editing.mp3"));
        sip = Gdx.audio.newSound(Gdx.files.internal("Potion Use_Drink (Terraria Sound) - Sound Effect for editing.mp3"));
    }

    /**
     * sets button visibility of buttons in Pause menu
     *
     * @param visible if buttons should be visible or not
     */
    public void setPauseMenuButtonsVisibility(boolean visible) {
        if (visible) {
            resume_button.setVisible(true);
            fullscreen_button.setVisible(true);
            settings_button.setVisible(true);
            exit_button.setVisible(true);
        } else {
            resume_button.setVisible(false);
            fullscreen_button.setVisible(false);
            settings_button.setVisible(false);
            exit_button.setVisible(false);
        }
    }

    /**
     * sets button visibility of buttons in Cheat menu
     *
     * @param visible if buttons should be visible or not
     */
    public void setSettingsMenuButtonsVisibility(boolean visible) {
        if (visible) {
            back_button.setVisible(true);
            checkBox_onlyPinkGuys.setVisible(true);
            godModeToggle.setVisible(true);
            apply_button.setVisible(true);
            reset_button.setVisible(true);
            textfield_minamountofenemies.setVisible(true);
            background_selectbox.setVisible(true);
            label_minamountofenemies.setVisible(true);
            label_maxamountofenemies.setVisible(true);
        } else {
            back_button.setVisible(false);
            checkBox_onlyPinkGuys.setVisible(false);
            godModeToggle.setVisible(false);
            apply_button.setVisible(false);
            reset_button.setVisible(false);
            textfield_minamountofenemies.setVisible(false);
            background_selectbox.setVisible(false);
            label_minamountofenemies.setVisible(false);
            label_maxamountofenemies.setVisible(false);
        }
    }

    /**
     * blocks player from leaving screen
     */
    public void playerCollisionScreenBounds() {
        // if player leaves screen in x direction
        if (player.player_rectangle.x < 0) player.player_rectangle.x = 0;
        if (player.player_rectangle.x > 1280 - 96) player.player_rectangle.x = 1280 - 96;

        // if player leaves screen in y direction
        if (player.player_rectangle.y < 0) player.player_rectangle.y = 0;
        if (player.player_rectangle.y + 96 > 720) player.player_rectangle.y = 720 - 96;

    }
}
