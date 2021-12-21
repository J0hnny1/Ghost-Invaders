package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.sun.org.apache.xpath.internal.operations.Bool;


import java.util.Random;


public class MyGdxGame extends ApplicationAdapter {

    //nur zu Testzwecken
    Rectangle enemy01;
    Texture enemy09_texture;
    TextureRegion enemy09_front;

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture healthTexture;
    long start_time = System.currentTimeMillis();
    long start_time_2 = System.currentTimeMillis();
    long start_time_3 = System.currentTimeMillis();
    long start_time_4 = System.currentTimeMillis();
    InputProcessor inputProcessor = new InputProcessor();
    Texture redpotion_texture;
    TextureRegion redpotion;
    Rectangle redpotion_rectangle;
    Enemy gegner;
    boolean zeichneGegner;
    Player spieler;
    Random random = new Random();
    Bullet bullet;
    boolean fireball_draw = false;
    Texture fireball_texture;
    Rectangle fireball_rectangle;


    @Override
    public void create() {
        //Texturen
        healthTexture = new Texture("health.png");
        enemy09_texture = new Texture("Enemy 09-1.png");
        redpotion_texture = new Texture("red potion.png");

        fireball_texture = new Texture("myBall.png");

        //Spieler
        spieler = new Player();

        fireball_rectangle = new Rectangle(spieler.player_rectangle.x, spieler.player_rectangle.y, 32, 32);
        //Enemy Texture Region
        enemy09_front = new TextureRegion(enemy09_texture, 0, 0, 32, 32);

        //Red Potion Texture Region
        redpotion = new TextureRegion(redpotion_texture, 0, 0, 16, 16);


        //Kamera
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1280, 720);


        //enemy01
        enemy01 = new Rectangle(32, 200, 32, 32);

        redpotion_rectangle = new Rectangle(100, 500, 64, 64);
        // Input Prozessor
        Gdx.input.setInputProcessor(inputProcessor);

        //Erstelle Objekt gegners
        gegner = new Enemy();

        bullet = new Bullet();

    }

    @Override
    public void render() {

        ScreenUtils.clear(216, 158, 85, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Start des Draw Prozess
        batch.begin();

        //Zeichne Debug Gegner
        batch.draw(enemy09_front, enemy01.x, enemy01.y, 96, 96);
        //Red Potion
        batch.draw(redpotion, redpotion_rectangle.x, redpotion_rectangle.y, 64, 64);

        batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y,32,32);
        //Spieler "Animationen"
        if (spieler.show_player_right)
            batch.draw(spieler.player_walk_right, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);
        if (spieler.show_player_left)
            batch.draw(spieler.player_walk_left, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);
        if (spieler.show_player_back)
            batch.draw(spieler.player_walk_back, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);
        if (spieler.show_player_front)
            batch.draw(spieler.player_walk_front, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);

        if (fireball_draw) {
            batch.draw(fireball_texture,fireball_rectangle.x,fireball_rectangle.y);
        }

        //Draw Hp Bar
        drawHealthIcons();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //bullet.createBullet();
            //fireball_draw = true;
            drawFireBall();
        }


        //Zeichne Gegner
        if (zeichneGegner) {
            for (int i = 0; i < gegner.getRectangles().length; i++) {
                batch.draw(gegner.getEnemy_textureRegion(), gegner.getRectangleAnStelle(i).x, gegner.getRectangleAnStelle(i).y, 96, 96);
            }
        }

        if (fireball_draw) {
            drawFireBall();
        }

        //Ende Des Draw Prozess
        batch.end();


        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            spieler.show_player_left = false;
            spieler.show_player_right = true;
            spieler.show_player_back = false;
            spieler.show_player_front = false;
            spieler.player_rectangle.x += 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            spieler.show_player_right = false;
            spieler.show_player_left = true;
            spieler.show_player_back = false;
            spieler.show_player_front = false;
            spieler.player_rectangle.x -= 250 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            spieler.show_player_right = false;
            spieler.show_player_left = false;
            spieler.show_player_front = false;
            spieler.show_player_back = true;
            spieler.player_rectangle.y += 250 * Gdx.graphics.getDeltaTime();

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            spieler.show_player_right = false;
            spieler.show_player_left = false;
            spieler.show_player_back = false;
            spieler.show_player_front = true;
            spieler.player_rectangle.y -= 250 * Gdx.graphics.getDeltaTime();

        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            gegner.createEnemys();
            zeichneGegner = true;
        }


        if (zeichneGegner) {
            follow();
        }
        if (zeichneGegner) {
            for (int i = 0; i < gegner.getRectangles().length; i++) {
                Rectangle r1 = gegner.getRectangleAnStelle(i);
                //Rectangle r2 = gegner.getRectangleAnStelle(i + 1);
                //if (r1.x == r2.x) r1.x = r2.x +96;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            follow();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireball_draw = true;
        }
        drawRandomEnemies();
        //if (fireball_draw) {
        //    fireball_rectangle.x += 100 * Gdx.graphics.getDeltaTime();

        //}

        // Wenn player ausserhalb des Screens im X bereich
        if (spieler.player_rectangle.x < 0) spieler.player_rectangle.x = 0;
        if (spieler.player_rectangle.x > 1280 - 64) spieler.player_rectangle.x = 1280 - 64;

        // Wenn player ausserhalb des Screens im Y bereich
        if (spieler.player_rectangle.y < 0) spieler.player_rectangle.y = 0;
        if (spieler.player_rectangle.y + 64 > 720) spieler.player_rectangle.y = 720 - 64;


        if (spieler.player_rectangle.overlaps(enemy01)) {
            if (System.currentTimeMillis() - start_time > 3000 || System.currentTimeMillis() - start_time == 0) {
                start_time = System.currentTimeMillis();
                System.out.println(spieler.hp.decrease(1));
                System.out.println("contact sp3");
            }
        }

        if (spieler.player_rectangle.overlaps(redpotion_rectangle)) {
            spieler.hp.increase(1);
            System.out.println("Hp increase");
        }

        if (spieler.player_rectangle.overlaps(fireball_rectangle)) {
            System.out.println("Fireball");
        }

        if (zeichneGegner) {
            for (int i = 0; i < 5; i++) {
                Rectangle r = gegner.getRectangleAnStelle(i);
                if (spieler.player_rectangle.overlaps(r)) {
                    System.out.println("Contact with enemy");

                    if (System.currentTimeMillis() - start_time_3 > 3000 || System.currentTimeMillis() - start_time == 0) {
                        start_time_3 = System.currentTimeMillis();
                        System.out.println(spieler.hp.decrease(1));
                        System.out.println("contact sp3");
                    }
                }
            }

        }


    }

    @Override
    public void dispose() {
        batch.dispose();
        healthTexture.dispose();
    }

    public void drawHealthIcons() {
        for (int i = 0; i < spieler.hp.getHealth(); i++) {
            batch.draw(healthTexture, i * 32, 720 - 32);
        }
    }

    public void follow() {
        for (int i = 0; i < 5; i++) {
            if (gegner.getRectangleAnStelle(i).y != spieler.player_rectangle.y && gegner.getRectangleAnStelle(i).x != spieler.player_rectangle.x) {
                if (gegner.getRectangleAnStelle(i).x < spieler.player_rectangle.x) {
                    gegner.getRectangleAnStelle(i).x += 50 * Gdx.graphics.getDeltaTime();
                }
                if (gegner.getRectangleAnStelle(i).x > spieler.player_rectangle.x) {
                    gegner.getRectangleAnStelle(i).x -= 50 * Gdx.graphics.getDeltaTime();
                }
                //start_time_2 = System.currentTimeMillis();
                if (System.currentTimeMillis() - start_time_2 > 1000) {

                    if (gegner.getRectangleAnStelle(i).y > spieler.player_rectangle.y) {
                        gegner.getRectangleAnStelle(i).y -= 50 * Gdx.graphics.getDeltaTime();
                    }
                    if (gegner.getRectangleAnStelle(i).y < spieler.player_rectangle.y) {
                        gegner.getRectangleAnStelle(i).y += 50 * Gdx.graphics.getDeltaTime();

                    }
                }

            }
        }

    }

    public void drawRandomEnemies() {
        int r = random.nextInt(4);

        if (System.currentTimeMillis() - start_time_4 > 10000 || System.currentTimeMillis() - start_time == 0) {
            start_time_4 = System.currentTimeMillis();
            switch (r) {
                case 0:
                    gegner.createEnemysTop();
                    follow();
                    break;
                case 1:
                    gegner.createEnemysBottom();
                    follow();
                    break;
                case 2:
                    gegner.createEnemysLeft();
                    follow();
                    break;
                case 3:
                    gegner.createEnemysRight();
                    follow();
                    break;
            }
        }
    }

    public void drawFireBall() {
        if (spieler.show_player_front) {
            batch.draw(fireball_texture,fireball_rectangle.x,fireball_rectangle.y,32,32);
            fireball_rectangle.y -= 100 * Gdx.graphics.getDeltaTime();
        } else if (spieler.show_player_back) {
            batch.draw(fireball_texture,fireball_rectangle.x,fireball_rectangle.y,32,32);
            fireball_rectangle.y += 100 * Gdx.graphics.getDeltaTime();
        } else if (spieler.show_player_left) {
            batch.draw(fireball_texture,fireball_rectangle.x,fireball_rectangle.y,32,32);
            fireball_rectangle.x -= 100 * Gdx.graphics.getDeltaTime();
        } else if (spieler.show_player_right) {
            batch.draw(fireball_texture,fireball_rectangle.x,fireball_rectangle.y,32,32);
            fireball_rectangle.x += 100 * Gdx.graphics.getDeltaTime();
        }
    }
}
