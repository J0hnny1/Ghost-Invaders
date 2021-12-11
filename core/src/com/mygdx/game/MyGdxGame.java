package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;


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
    boolean fullscreen = true;
    public boolean maybe = true;
    //Graphics.DisplayMode prim = Gdx.graphics.getDisplayMode();
    Texture healthTexture;
    Rectangle healthIcon;
    Health  health = new Health(4);


    @Override
    public void create() {
        //Texturen
        badlogic = new Texture("badlogic.jpg");
        playerTexture = new Texture("stardewT.png");
        enemyTexture = new Texture("shit.png");
        testTexture = new Texture("test.png");
        healthTexture = new Texture("health.png");

        wall2 = new Texture("badlogic.jpg");

        //sound
        m9 = Gdx.audio.newSound(Gdx.files.internal("barreta_m9-Dion_Stapper-1010051237.mp3"));

        //Kamera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 800, 400);

        //player
        player = new Rectangle();
        player.x = 800 / 2 - 64 / 2;
        player.y = 20;
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

        //batch.setColor(1,0,0,1);


        //batch.draw(wall2, wall.x, wall.y);

        batch.end(); //Ende Des Draw Prozess

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.y += 200 * Gdx.graphics.getDeltaTime();
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
            player.y -= 200 * Gdx.graphics.getDeltaTime();
            //System.out.println(player.x);
            //if (player.overlaps(badlogic1)) {
            //    System.out.println("Poggers");
            //}
        }

        //Aus Fullscreen raus gehen
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            if (fullscreen) {
                Gdx.graphics.setWindowedMode(800, 600);
                fullscreen = false;
            } //else Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


        }

        // Wenn player ausserhalb des Screens im X bereich
        if (player.x < 0) player.x = 0;
        if (player.x > 800 - 64) player.x = 800 - 64;

        // Wenn player ausserhalb des Screens im Y bereich
        if (player.y < 0) player.y = 0;
        if (player.y + 64 > 400) player.y = 400 - 64;


        //for (int i = 0; i < enemy.x + enemyTexture.getWidth(); i++){
        //    System.out.println("plsh help");
        //}
        //shit colision

        //if (player.y > enemy.y && enemy.x == player.x ){
        //    //player.y = enemy.y;
        //    System.out.println("aÜtjü");
        //}

        if (player.overlaps(enemy)) {
            health.damage(1);

        }
        //if (player.overlaps(test)) {
        //    System.out.println("Test Comment");
        //    player.y = test.y - 64;
       //     player.x = test.x + 93;
        //}


    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
