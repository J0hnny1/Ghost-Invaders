package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Enemies.EnemyFocusedOnPlayer;
import com.mygdx.game.Items.Fastshoot;
import com.mygdx.game.Items.HealthPotion;
import com.mygdx.game.Items.Poison;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;


public class Controller extends ApplicationAdapter {

    // game entities arraylist
    final ArrayList<GameEntity> gameEntities = new ArrayList<>();
    //camera
    SpriteBatch batch;
    OrthographicCamera camera;
    //player
    Player player;
    //InputProcessor
    InputProcessor inputProcessor;
    //heart for hp bar
    Texture healthTexture;
    //enemy textures
    Texture white_enemy_texture, blue_enemy_texture, pink_enemy_texture, miniboss_texture, fastEnemy_texture;
    //timers
    long start_time_damageSplash = System.currentTimeMillis();
    long start_time = System.currentTimeMillis();
    long start_time_itemSpawn = System.currentTimeMillis();
    long start_time_spawn = System.currentTimeMillis();
    long start_time_bullet = System.currentTimeMillis();
    long start_time_poison, start_time_fastshoot, start_time_run, stop_time_run;
    float time_run, time_run_s_2;
    //Items
    Texture redpotion_texture, greenpotion_texture, yellowpotion_texture, bluepotion_texture;
    //background textures
    Texture background_texture_default, background_texture_desert, background_texture_desertCustom, background_texture_grass;
    //random for random generated numbers
    final Random random = new Random();
    //fireball texture
    Texture fireball_texture;
    //counters for increased hardness
    int min_enemies, max_enemies;
    int enemySpawnDelay = 1000;
    //game config variables
    int bulletspeed, movementspeed, shootcooldown;
    //boolean if player took damage to control damage effect
    boolean playerTookDamage;
    //Sounds
    Sound flameAttack, hit, death, sip;
    //config
    Preferences config;
    int itemscollected, enemieskilled, waveCount, itemsonfield, itemspawncooldown;
    boolean firstspawn = true;
    //boolean playerDead = false;
    boolean item_spawnratereduced = false;
    int itemscollected_in_run;
    int enemieskilled2;
    //stage, Widgets
    Stage stage;
    InputMultiplexer multiplexer;
    //fonts
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter, parameter2;
    BitmapFont font2, font3, font4;
    //gameState
    boolean fullscreen = false;
    final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    boolean cheatsEnabled;
    GUI gui = new GUI(this);

    enum GameState {
        STARTSCREEN, INGAME, SETTINGSMENU, PAUSEMENU, DEATHSCREEN, CHEATMENU
    }

    enum ConfigInit {
        ALL, CHEATS, SETTINGS
    }

    public GameState gameState = GameState.STARTSCREEN;

    @Override
    public void create() {
        //Camera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);
        //config
        config = Gdx.app.getPreferences("ghostinvadorsconfig");
        if (!config.getBoolean("ConfigExists")) initConfig(ConfigInit.ALL);
        //Spieler
        player = new Player(config.getInteger("PlayerHP"), config.getInteger("PlayerHP") + 6);
        //stage
        stage = new Stage();
        //call initializing methods
        initializeSounds();
        initTextures();
        //initButtons();
        gui.initButtons();
        initializeFonts();
        loadFromConfig();
        //input processing
        inputProcessor = new InputProcessor(player, this);
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        //start timer
        start_time_run = System.currentTimeMillis();
        cheatsEnabled = config.getBoolean("CheatsEnabled");
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
            if (e.getEntityType() == GameEntity.EntityType.ITEM || e.getEntityType() == GameEntity.EntityType.POISON) {
                Rectangle r = e.getRectangle();
                //batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
                e.setStateTime(e.getStateTime() + Gdx.graphics.getDeltaTime());
                TextureRegion currentFrame = e.getAnimation().getKeyFrame(e.getStateTime(), true);
                batch.draw(currentFrame, e.getx(), e.gety(), r.width, r.height);
            }
        }
        if (playerTookDamage) batch.setColor(Color.RED);
        //make player red if he takes damage
        if (System.currentTimeMillis() - start_time_damageSplash > 2000) {
            start_time_damageSplash = System.currentTimeMillis();
            batch.setColor(Color.WHITE);
            playerTookDamage = false;
        }
        //default player direction back
        if (player.playerdirection == null) player.playerdirection = Player.Direction.BACK;

        //!deathScreen && gameIsStarted && !settingsScreen
        if (gameState == GameState.INGAME) {
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
            if (e.getEntityType() == GameEntity.EntityType.ENEMY || e.getEntityType() == GameEntity.EntityType.PINKENEMY) {
                Rectangle r = e.getRectangle();
                e.setStateTime(e.getStateTime() + Gdx.graphics.getDeltaTime());
                TextureRegion currentFrame = e.getAnimation().getKeyFrame(e.getStateTime(), true);
                batch.draw(currentFrame, e.getx(), e.gety(), r.width, r.height);

                //batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        //draw all bullets
        for (GameEntity e : gameEntities) {
            if (e.getEntityType() == GameEntity.EntityType.BULLET) {
                Rectangle r = e.getRectangle();
                batch.draw(e.getTexture(), (int) e.getx(), (int) e.gety(), r.width, r.height);
            }
        }

        //WaveCounter
        if (!cheatsEnabled) font2.draw(batch, "" + waveCount, 1265 - 10, 700);
        else font4.draw(batch, "" + waveCount, 1265 - 10, 700);

        //Draw Hp Bar
        drawHealthIcons();

        batch.setColor(Color.LIGHT_GRAY);
        //pause screen  isPaused
        if (gameState != GameState.INGAME) {
            batch.setColor(Color.GRAY);
            //!settingsScreen
            if (gameState == GameState.PAUSEMENU) {
                font2.draw(batch, "Highscore: " + config.getString("highscore") + " Waves", 570, 50);
                font2.draw(batch, "Items Collected: " + config.getString("ItemsCollected"), 570, 50 + 25);
                gui.setPauseMenuButtonsVisibility(true);
            }
        } else {
            batch.setColor(Color.WHITE);
            gui.setPauseMenuButtonsVisibility(false);
            gui.setCheatMenuButtonsVisibility(false);
        }
        if (gameState == GameState.PAUSEMENU) {
            gui.setPauseMenuButtonsVisibility(true);
            gui.setCheatMenuButtonsVisibility(false);
            gui.setSettingsMenuButtonVisibility(false);
        }

        //Halt execution if game is not started by player
        if (gameState == GameState.STARTSCREEN) {
            batch.setColor(Color.LIGHT_GRAY);
            font3.draw(batch, "Ghost Invaders", 465, 400);
            font2.draw(batch, "Press Space to start", 570, 60);
            batch.end();
            return;
        }
        //deathScreen
        if (gameState == GameState.DEATHSCREEN) {
            batch.setColor(Color.GRAY);
            font2.draw(batch, "Press Space to continue", 570, 60);
            font3.draw(batch, "You Died", 530, 400);
            font2.draw(batch, "Enemies Killed: " + enemieskilled2, 570, 310 - 25);
            font2.draw(batch, "Items Collected: " + itemscollected_in_run, 570, 310);
            font2.draw(batch, "Time: " + ((int) time_run) + ":" + ((int) time_run_s_2), 570, 310 - 50);
            start_time_run = System.currentTimeMillis();
            batch.end();
            return;
        }
        //settingsScreen
        if (gameState == GameState.SETTINGSMENU) gui.setPauseMenuButtonsVisibility(false);


        //draw stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

//end of draw process
        batch.end();
        //Button Input
        if (gui.resume_button.isPressed()) gameState = GameState.INGAME;
        if (gui.exit_button.isPressed()) Gdx.app.exit();
        if (gui.cheatmenu_button.isPressed()) {
            gui.setCheatMenuButtonsVisibility(true);
            gui.setPauseMenuButtonsVisibility(false);
            //gui.setSettingsMenuButtonVisibility(false);
            gameState = GameState.CHEATMENU;
        }
        //check if Game is paused !isPaused
        if (gameState == GameState.INGAME) {
//loop through gameEntities arraylist
            for (int i = 0; i < gameEntities.size(); i++) {
                GameEntity e = gameEntities.get(i);
                Rectangle r = new Rectangle(e.getx(), e.gety(), 96, 96);

                e.move(player, gameEntities, Gdx.graphics.getDeltaTime());

                //damage the player if he has contact with enemy
                if (e.getEntityType() == GameEntity.EntityType.ENEMY && player.player_rectangle.overlaps(r)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        if (!config.getBoolean("GodMode") & !player.imunetoDamage) {
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
                if (e.getEntityType() == GameEntity.EntityType.PINKENEMY && r.overlaps(player.player_rectangle)) {
                    if (System.currentTimeMillis() - start_time > 2000) {
                        start_time = System.currentTimeMillis();
                        if (!config.getBoolean("GodMode") && !player.imunetoDamage) {
                            player.health.decrease(1);
                            hit.play();
                            playerTookDamage = true;
                        }

                        //noinspection SuspiciousListRemoveInLoop
                        gameEntities.remove(i);
                    }
                }
                //execute onContact of Item when in contact & remove item from screen
                if (e.getEntityType() == GameEntity.EntityType.ITEM && player.player_rectangle.overlaps(r) || e.getEntityType() == GameEntity.EntityType.POISON && player.player_rectangle.overlaps(r)) {
                    sip.play();
                    e.onContact();
                    //noinspection SuspiciousListRemoveInLoop
                    gameEntities.remove(i);
                    itemscollected++;
                    itemscollected_in_run++;
                    itemsonfield--;
                    if (e.getEntityType() == GameEntity.EntityType.POISON)
                        start_time_poison = System.currentTimeMillis();
                }
                //increase enemieskilled counter
                if (e.getEntityType() == GameEntity.EntityType.BULLET)
                    enemieskilled = enemieskilled + e.getEnemiesKilled();

            }
//end of arraylist loop

            // spawn enemies
            if (min_enemies < max_enemies) spawnEnemies(random.nextInt(min_enemies, max_enemies));
            else min_enemies--;

            //spawn Items
            if (itemsonfield < 5) spawnItems();

            //update stats
            updateHighscore();
            playerCollisionScreenBounds();
            //Check if poison effect is over
            if (System.currentTimeMillis() - start_time_poison > 2000) {
                player.killsEnemiesOnContact = false;
                player.imunetoDamage = false;
                start_time_poison = 0;
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

            if (enemieskilled != 0) enemieskilled2 = enemieskilled;

            //If player dies
            if (player.health.getHealth() == 0) {
                playerDeath(true);
                enemieskilled = 0;
                gameState = GameState.DEATHSCREEN;
                stop_time_run = System.currentTimeMillis();
                long time_run_ms = stop_time_run - start_time_run;
                float time_run_s = ((float) time_run_ms) / 1000;
                time_run = time_run_s / 60;
                time_run_s_2 = time_run_ms % 60;
            }

//Input
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.setPlayerdirection(Player.Direction.WALKINGRIGHT);
                player.player_rectangle.x += movementspeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.setPlayerdirection(Player.Direction.WALKINGLEFT);
                player.player_rectangle.x -= movementspeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.setPlayerdirection(Player.Direction.WALKINGFRONT);
                player.player_rectangle.y += movementspeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.setPlayerdirection(Player.Direction.WALKINGBACK);
                player.player_rectangle.y -= movementspeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    System.out.println("shotcoold " + shootcooldown);
                    System.out.println("\nbulletspeed" + bulletspeed);
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    switch (player.playerdirection) {
                        case BACK, WALKINGBACK -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -bulletspeed, fireball_texture));
                        case FRONT, WALKINGFRONT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, bulletspeed, fireball_texture));
                        case LEFT, WALKINGLEFT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -bulletspeed, 0, fireball_texture));
                        case RIGHT, WALKINGRIGHT -> gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, bulletspeed, 0, fireball_texture));
                    }
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 96, player.player_rectangle.y + 48, bulletspeed, 0, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x, player.player_rectangle.y + 48, -bulletspeed, 0, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y + 96, 0, bulletspeed, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (System.currentTimeMillis() - start_time_bullet > shootcooldown) {
                    flameAttack.play();
                    start_time_bullet = System.currentTimeMillis();
                    gameEntities.add(new Bullet(gameEntities.size(), player.player_rectangle.x + 48, player.player_rectangle.y, 0, -bulletspeed, fireball_texture));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) playerDeath(false);

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
        fireball_texture.dispose();
        flameAttack.dispose();
        death.dispose();
        sip.dispose();
        font2.dispose();
        background_texture_desert.dispose();
        background_texture_default.dispose();
        background_texture_grass.dispose();
        background_texture_desertCustom.dispose();
        miniboss_texture.dispose();
        fastEnemy_texture.dispose();
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
    public void spawnEnemies(int enemy_amount) {
        if (System.currentTimeMillis() - start_time_spawn > enemySpawnDelay || firstspawn) {
            boolean pink_spawned = false;
            boolean firstSpawn2 = true;
            firstspawn = false;
            start_time_spawn = System.currentTimeMillis();
            int speed1 = 0, speed2 = 0, speed_x = 0, speed_y = 0, x = 0, y = 0;

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
            int enemy_type = random.nextInt(3);
            int direction = random.nextInt(4);
            Texture texture = healthTexture;

            switch (enemy_type) {
                case 0 -> texture = white_enemy_texture;
                case 1 -> texture = blue_enemy_texture;
                case 2 -> texture = fastEnemy_texture;

            }
            for (int i = 0; i < enemy_amount; i++) {


                // get random speeds
                switch (enemy_type) {
                    case 0 -> speed2 = random.nextInt(80, 300);
                    case 1 -> {
                        speed1 = random.nextInt(80, 250);
                        speed2 = random.nextInt(20, 150);
                    }
                    case 2 -> speed2 = random.nextInt(240, 320);

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
                    gameEntities.add(new EnemyFocusedOnPlayer(gameEntities.size(), 1, random.nextInt(190, 230), random.nextInt(190, 230), x, y, pink_enemy_texture));
                    pink_spawned = true;
                }
                //add enemy
                if (enemy_type == 2) {
                    //TODO make enemies spawn in a line
                    /*
                    if (firstSpawn2) {
                        int startXpos = x;
                        firstSpawn2 = false;
                    } else (int startXpos = 0);

                    gameEntities.add(new Enemy(gameEntities.size(), 1, speed_x, speed_y, startXPos, y + 35, texture));
                    startXPos += 35;
                     */
                } else gameEntities.add(new Enemy(gameEntities.size(), 1, speed_x, speed_y, x, y, texture));
            }
            if (waveCount >= 5 && waveCount % 2 == 0) {
                gameEntities.add(new EnemyFocusedOnPlayer(gameEntities.size(), 3, random.nextInt(250, 300), random.nextInt(250, 300), x, y, miniboss_texture));
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
            } else if (random.nextInt(0, 100) <= 50) {
                gameEntities.add(new Fastshoot(ThreadLocalRandom.current().nextInt(0, 1280 - 64), ThreadLocalRandom.current().nextInt(0, 720 - 64), 64, 64, player, System.currentTimeMillis(), bluepotion_texture, gameEntities, this));
            }
            itemsonfield++;
        }
    }

    /**
     * callled when the player dies, resets the game state
     */
    public void playerDeath(boolean deathSound) {
        if (deathSound) death.play();
        player.health.setHealth(config.getInteger("PlayerHP"));
        gameEntities.clear();
        waveCount = 0;
        player.killsEnemiesOnContact = false;
        player.player_rectangle.x = 640 - 16;
        player.player_rectangle.y = 360;
        batch.setColor(Color.WHITE);
        player.setPlayerdirection(Player.Direction.BACK);
        loadFromConfig();
    }

    /**
     * update values for highscore and items collected in config file
     */
    public void updateHighscore() {
        if (waveCount > config.getInteger("highscore") && !cheatsEnabled) {
            config.putInteger("highscore", waveCount);
        }
        config.putInteger("ItemsCollected", config.getInteger("ItemsCollected") + itemscollected);
        config.putBoolean("CheatsEnabled", cheatsEnabled);
        itemscollected = 0;
        config.flush();
    }

    /**
     * initialize Configuration File with default values
     */
    public void initConfig(ConfigInit categorie) {
        config.putString("A", """
                All entries have to be the same data type as the default values.\s
                Time is set in milli seconds. \s
                To reset the config press f5 in game, set ConfigExists false, or delete this file.""".indent(1));

        switch (categorie) {
            case ALL -> {
                config.putInteger("shootcooldown", 700);
                config.putInteger("BulletSpeed", 400);
                config.putInteger("MinAmountOfEnemies", 4);
                config.putInteger("MaxAmountOfEnemies", 6);
                config.putBoolean("OnlyPinkEnemies", false);
                config.putInteger("PlayerHP", 4);
                config.putInteger("EnemyWaveCooldown", 12000);
                config.putInteger("ItemSpawnCooldown", 15000);
                config.putBoolean("ConfigExists", true);
                config.putInteger("MovementSpeed", 250);
                config.putBoolean("GodMode", false);
                config.putString("BackGroundTexture", "default");
                config.putString("PlayerTexture", "Male 17-1");
                config.putBoolean("CheatsEnabled", false);
            }
            case CHEATS -> {
                config.putInteger("shootcooldown", 700);
                config.putInteger("BulletSpeed", 290);
                config.putInteger("MinAmountOfEnemies", 4);
                config.putInteger("MaxAmountOfEnemies", 6);
                config.putBoolean("OnlyPinkEnemies", false);
                config.putInteger("PlayerHP", 4);
                config.putInteger("EnemyWaveCooldown", 12000);
                config.putInteger("ItemSpawnCooldown", 15000);
                config.putBoolean("ConfigExists", true);
                config.putInteger("MovementSpeed", 250);
                config.putBoolean("GodMode", false);
                config.putBoolean("CheatsEnabled", false);
            }
            case SETTINGS -> {
                config.putString("BackGroundTexture", "default");
                config.putString("PlayerTexture", "Male 17-1");
            }
        }
        config.flush();
    }

    /**
     * load values of config file into variables
     */
    public void loadFromConfig() {
        enemySpawnDelay = config.getInteger("EnemyWaveCooldown");
        shootcooldown = config.getInteger("shootcooldown");
        min_enemies = config.getInteger("MinAmountOfEnemies");
        max_enemies = config.getInteger("MaxAmountOfEnemies");
        itemspawncooldown = config.getInteger("ItemSpawnCooldown");
        bulletspeed = config.getInteger("BulletSpeed");
        movementspeed = config.getInteger("MovementSpeed");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * toggle state of Fullscreen
     */
    public void toggleFullscreen() {
        if (!fullscreen) {
            Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
            fullscreen = true;
            Gdx.graphics.setFullscreenMode(displayMode);

        } else {
            Gdx.graphics.setWindowedMode(1280, 720);
            fullscreen = false;
        }
    }

    /**
     * load all needed textures
     */
    public void initTextures() {
        //heart Texture
        healthTexture = new Texture("heart.png");

        //background textures
        background_texture_default = new Texture("default.png");
        background_texture_desert = new Texture("desert.png");
        background_texture_desertCustom = new Texture("desertCustom.png");
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
        miniboss_texture = new Texture("Enemy 15-6.png");
        fastEnemy_texture = new Texture("Enemy 16-5.png");
    }

    /**
     * creates fonts
     */
    public void initializeFonts() {
        if (waveCount >= 20 && !item_spawnratereduced) {
            itemspawncooldown = config.getInteger("ItemSpawnCooldown") - 5000;
            item_spawnratereduced = true;
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
        font4 = generator.generateFont(parameter);
        font4.setColor(Color.RED);
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

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public int getBulletspeed() {
        return bulletspeed;
    }

    public void setBulletspeed(int bulletspeed) {
        this.bulletspeed = bulletspeed;
    }

    public int getShootcooldown() {
        return shootcooldown;
    }

    public void setShootcooldown(int shootcooldown) {
        this.shootcooldown = shootcooldown;
    }

    public int getMovementspeed() {
        return movementspeed;
    }

    public void setMovementspeed(int movementspeed) {
        this.movementspeed = movementspeed;
    }
}
