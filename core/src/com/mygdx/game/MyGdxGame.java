package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture badlogic;
    OrthographicCamera camera;
    Rectangle player;
    Rectangle badlogic1;
    Texture enemyTexture;
    Sound m9;
    Texture testTexture;
    Rectangle test;
    public boolean maybe = true;
    Texture healthTexture;
    Rectangle healthIcon;
    Health health = new Health(4, 10);
    Texture male17;
    TextureRegion player_walk_left;
    TextureRegion player_walk_front;
    TextureRegion player_walk_right;
    TextureRegion player_walk_back;
    Rectangle enemy01;
    Sprite sprite;
    TextureRegion[][] regions;
    long start_time = System.currentTimeMillis();
    InputProcessor inputProcessor = new InputProcessor();
    boolean show_player_front = true;
    boolean show_player_back;
    boolean show_player_left;
    boolean show_player_right;
    Texture enemy09_texture;
    TextureRegion enemy09_front;
    int count = 32;
    Texture redpotion_texture;
    TextureRegion redpotion;
    Rectangle redpotion_rectangle;


    @Override
    public void create() {
        //Texturen
        badlogic = new Texture("badlogic.jpg");
        enemyTexture = new Texture("shit.png");
        testTexture = new Texture("test.png");
        healthTexture = new Texture("health.png");
        enemy09_texture = new Texture("Enemy 09-1.png");
        redpotion_texture = new Texture("red potion.png");


        male17 = new Texture("Male 17-1.png");
        player_walk_left = new TextureRegion(male17, 0, 32, 32, 32);
        player_walk_right = new TextureRegion(male17, 0, 64, 32, 32);
        player_walk_front = new TextureRegion(male17, 0, 0, 32, 32);
        player_walk_back = new TextureRegion(male17, 0, 96, 32, 32);

        enemy09_front = new TextureRegion(enemy09_texture, 0, 0, 32, 32);
        redpotion = new TextureRegion(redpotion_texture, 0, 0, 16, 16);


        //sound
        m9 = Gdx.audio.newSound(Gdx.files.internal("barreta_m9-Dion_Stapper-1010051237.mp3"));

        //Kamera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);

        //player
        player = new Rectangle(1280 / 2 - 32 / 2, 720 / 2, 32, 32);

        //enemy01
        enemy01 = new Rectangle(32, 200, 32, 32);

        healthIcon = new Rectangle();

        regions = TextureRegion.split(male17, 32, 32);
        sprite = new Sprite(regions[0][0]);

        redpotion_rectangle = new Rectangle(100, 500, 64, 64);
        // Input Prozessor
        Gdx.input.setInputProcessor(inputProcessor);


    }

    @Override
    public void render() {


        ScreenUtils.clear(216, 158, 85, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin(); //Start des Draw Prozess


        //batch.draw(healthTexture, 3, 720 - 32);
        //batch.draw(healthTexture, 35, 720 - 32);
        //batch.draw(healthTexture, 70, 720 - 32);
        batch.draw(enemy09_front, enemy01.x, enemy01.y, 96, 96);
        batch.draw(redpotion, redpotion_rectangle.x, redpotion_rectangle.y, 64, 64);


        if (show_player_right) batch.draw(player_walk_right, player.x, player.y, 96, 96);
        if (show_player_left) batch.draw(player_walk_left, player.x, player.y, 96, 96);
        if (show_player_back) batch.draw(player_walk_back, player.x, player.y, 96, 96);
        if (show_player_front) batch.draw(player_walk_front, player.x, player.y, 96, 96);

        for (int i = 0; i < health.getHealth(); i++) {
            batch.draw(healthTexture, i * 32, 720 - 32);
        }

        //Ende Des Draw Prozess
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            show_player_left = false;
            show_player_right = true;
            show_player_back = false;
            show_player_front = false;
            player.x += 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            show_player_right = false;
            show_player_left = true;
            show_player_back = false;
            show_player_front = false;
            player.x -= 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            show_player_right = false;
            show_player_left = false;
            show_player_front = false;
            show_player_back = true;
            player.y += 250 * Gdx.graphics.getDeltaTime();

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            show_player_right = false;
            show_player_left = false;
            show_player_back = false;
            show_player_front = true;
            player.y -= 250 * Gdx.graphics.getDeltaTime();

        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            System.out.println("\nenemy x: " + enemy01.x);
            System.out.println("enemy y: " + enemy01.y);
            System.out.println("player x: " + player.x);
            System.out.println("player y: " + player.y);

            System.out.println("Hp: " + health.getHealth());
        }


        // Wenn player ausserhalb des Screens im X bereich
        if (player.x < 0) player.x = 0;
        if (player.x > 1280 - 64) player.x = 1280 - 64;

        // Wenn player ausserhalb des Screens im Y bereich
        if (player.y < 0) player.y = 0;
        if (player.y + 64 > 720) player.y = 720 - 64;


        // if (player.overlaps(enemy01)) {
        //   m9.play(0.4f);
        //   health.decrease(1);
        //}

        if (player.overlaps(enemy01)) {

            if (System.currentTimeMillis() - start_time > 3000 || System.currentTimeMillis() - start_time == 0) {
                start_time = System.currentTimeMillis();
                System.out.println(health.decrease(1));
                System.out.println("contact sp3");
            }


        }

        if (player.overlaps(redpotion_rectangle)) {
                health.increase(1);
                System.out.println("Hp increase");


        }


    }

    @Override
    public void dispose() {
        batch.dispose();
        male17.dispose();
        healthTexture.dispose();
        testTexture.dispose();
        enemyTexture.dispose();

    }

    public void drawHealthIcon() {
        int c = health.getHealth();

    }
}
