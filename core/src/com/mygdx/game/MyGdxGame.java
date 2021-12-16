package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    Rectangle player;
    Texture enemyTexture;

    Texture healthTexture;
    Rectangle healthIcon;
    Health health = new Health(4, 10);
    Texture male17;
    TextureRegion player_walk_left;
    TextureRegion player_walk_front;
    TextureRegion player_walk_right;
    TextureRegion player_walk_back;
    Rectangle enemy01;

    long start_time = System.currentTimeMillis();
    long start_time_2 = System.currentTimeMillis();
    InputProcessor inputProcessor = new InputProcessor();
    boolean show_player_front = true;
    boolean show_player_back;
    boolean show_player_left;
    boolean show_player_right;
    Texture enemy09_texture;
    TextureRegion enemy09_front;
    Texture redpotion_texture;
    TextureRegion redpotion;
    Rectangle redpotion_rectangle;
    Random random = new Random();



    @Override
    public void create() {
        //Texturen
        enemyTexture = new Texture("shit.png");
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


        //Kamera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);

        //player
        player = new Rectangle(1280 / 2 - 32 / 2, 720 / 2, 32, 32);

        //enemy01
        enemy01 = new Rectangle(32, 200, 32, 32);

        healthIcon = new Rectangle();


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

        drawHealthIcons();

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

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {

        }
        follow();

        // Wenn player ausserhalb des Screens im X bereich
        if (player.x < 0) player.x = 0;
        if (player.x > 1280 - 64) player.x = 1280 - 64;

        // Wenn player ausserhalb des Screens im Y bereich
        if (player.y < 0) player.y = 0;
        if (player.y + 64 > 720) player.y = 720 - 64;


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
        enemyTexture.dispose();

    }

    public void drawHealthIcons() {
        for (int i = 0; i < health.getHealth(); i++) {
            batch.draw(healthTexture, i * 32, 720 - 32);
        }
    }

    public void createEnemys() {
        //batch.draw(enemy01,random.nextInt(1280),random.nextInt(720));
    }

    public void follow() {
        if (enemy01.y != player.y && enemy01.x != player.x) {
            if (enemy01.x < player.x) {
                enemy01.x += 150 * Gdx.graphics.getDeltaTime();
            }
            if (enemy01.x > player.x) {
                enemy01.x -= 150 * Gdx.graphics.getDeltaTime();
            }
            //start_time_2 = System.currentTimeMillis();
            if (System.currentTimeMillis() - start_time_2 > 1000) {

                if (enemy01.y > player.y) {
                    enemy01.y -= 150 * Gdx.graphics.getDeltaTime();
                }
                if (enemy01.y < player.y) {
                    enemy01.y += 150 * Gdx.graphics.getDeltaTime();

                }
            }

        }
    }
}
