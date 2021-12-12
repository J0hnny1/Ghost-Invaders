package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Texture badlogic;
    Texture playerTexture;
    OrthographicCamera camera;
    Rectangle player;
    Rectangle badlogic1;
    Texture enemyTexture;
    Rectangle enemy;
    Sound m9;
    Rectangle wall;
    Texture wall2;
    Texture testTexture;
    Rectangle test;
    boolean fullscreen = false;
    public boolean maybe = true;
    Texture healthTexture;
    Rectangle healthIcon;
    Health health = new Health(4, 420);
    Texture male17;
    TextureRegion region;
    TextureRegion region2;
    Rectangle sp2;
    Rectangle sp3;
    Sprite sprite;
    TextureRegion[][] regions;
    int frame;
    int zeile;
    //Instant start_time = Instant.now();
    //Instant stop_time;;
    long start_time = System.currentTimeMillis();
    long stop_time;
    long diff;
    long diff_s;


    @Override
    public void create() {
        //Texturen
        badlogic = new Texture("badlogic.jpg");
        playerTexture = new Texture("stardewT.png");
        enemyTexture = new Texture("shit.png");
        testTexture = new Texture("test.png");
        healthTexture = new Texture("health.png");

        male17 = new Texture("Male 17-1.png");
        region = new TextureRegion(male17, 0, 32, 32, 32);
        region2 = new TextureRegion(male17, 0, 0, 32, 32);


        //sound
        m9 = Gdx.audio.newSound(Gdx.files.internal("barreta_m9-Dion_Stapper-1010051237.mp3"));

        //Kamera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);

        //player
        player = new Rectangle();
        player.x = 1280 / 2 - 64 / 2;
        player.y = 720 / 2;
        player.width = 64;
        player.height = 64;

        //badlogic
        badlogic1 = new Rectangle(200, 200, 64, 64);

        //enemy
        enemy = new Rectangle(400, 200, 64, 64);

        //wall
        wall = new Rectangle(700, 200, 20, 90);

        test = new Rectangle(100, 100, 93, 93);

        healthIcon = new Rectangle();

        sp2 = new Rectangle(0, 0, 32, 32);
        sp3 = new Rectangle(96, 0, 96, 96);
        regions = TextureRegion.split(male17, 32, 32);
        sprite = new Sprite(regions[0][0]);


    }

    @Override
    public void render() {
        ScreenUtils.clear(216, 158, 85, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin(); //Start des Draw Prozess

        //batch.draw(playerTexture, player.x, player.y);
        batch.draw(playerTexture, player.x, player.y);
        //batch.draw(badlogic, badlogic1.x, badlogic1.y);
        batch.draw(enemyTexture, enemy.x, enemy.y);
        batch.draw(testTexture, test.x, test.y);
        batch.draw(healthTexture, 3, 400 - 32);
        batch.draw(healthTexture, 35, 400 - 32);
        batch.draw(healthTexture, 70, 400 - 32);
        batch.draw(region, sp2.x, sp2.y, 96, 96);
        batch.draw(region2, sp3.x, sp3.y, 96, 96);


        //setzt Farbe aller geladenen Texturen
        //batch.setColor(1,0,0,1);


        //Ende Des Draw Prozess
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.x += 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.x -= 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.y += 250 * Gdx.graphics.getDeltaTime();
            //m9.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            System.out.println("\nenemy x: " + enemy.x);
            System.out.println("enemy y: " + enemy.y);
            System.out.println("player x: " + player.x);
            System.out.println("player y: " + player.y);

            System.out.println("Hp: " + health.getHealth());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.y -= 250 * Gdx.graphics.getDeltaTime();
            //System.out.println(player.x);
            //if (player.overlaps(badlogic1)) {
            //    System.out.println("Poggers");
            //}
        }

        //Fullscreen Toggle
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
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


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            //stop_time = Instant.now();
            //System.out.println(Duration.between(start_time,stop_time));
            //System.out.println(ChronoUnit.SECONDS.between(start_time, stop_time));
            //stop_time = System.currentTimeMillis();
            //diff = stop_time - start_time;


            System.out.println("Ms start: " + start_time);
            System.out.println("Ms stop " + stop_time);
            System.out.println("Differenz " + diff);
            System.out.println("Diff S " + diff_s);

        }

        // Wenn player ausserhalb des Screens im X bereich
        if (player.x < 0) player.x = 0;
        if (player.x > 1280 - 64) player.x = 1280 - 64;

        // Wenn player ausserhalb des Screens im Y bereich
        if (player.y < 0) player.y = 0;
        if (player.y + 64 > 720) player.y = 720 - 64;


        //for (int i = 0; i < enemy.x + enemyTexture.getWidth(); i++){
        //    System.out.println("plsh help");
        //}
        //shit colision

        //if (player.y > enemy.y && enemy.x == player.x ){
        //    //player.y = enemy.y;
        //    System.out.println("aÜtjü");
        //}

        if (player.overlaps(enemy)) {
            m9.play(0.4f);
            health.decrease(1);
        }

        if (player.overlaps(sp3)) {

            if (System.currentTimeMillis() - start_time > 3000 || System.currentTimeMillis() - start_time == 0) {
                start_time = System.currentTimeMillis();
                System.out.println(health.decrease(1));
                System.out.println("contact sp3");
            }


        }


    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
