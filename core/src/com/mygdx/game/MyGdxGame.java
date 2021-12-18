package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;


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
    InputProcessor inputProcessor = new InputProcessor();
    Texture redpotion_texture;
    TextureRegion redpotion;
    Rectangle redpotion_rectangle;
    Enemy gegner;
    boolean zeichneGegner;
    Player spieler;


    @Override
    public void create() {
        //Texturen
        healthTexture = new Texture("health.png");
        enemy09_texture = new Texture("Enemy 09-1.png");
        redpotion_texture = new Texture("red potion.png");


        //Spieler
        spieler = new Player();


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

        //Spieler "Animationen"
        if (spieler.show_player_right)
            batch.draw(spieler.player_walk_right, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);
        if (spieler.show_player_left)
            batch.draw(spieler.player_walk_left, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);
        if (spieler.show_player_back)
            batch.draw(spieler.player_walk_back, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);
        if (spieler.show_player_front)
            batch.draw(spieler.player_walk_front, spieler.player_rectangle.x, spieler.player_rectangle.y, 96, 96);

        //Draw Hp Bar
        drawHealthIcons();

        //Wenn O gedrückt dann zeichne Gegner
        if (zeichneGegner) {
            for (int i = 0; i < gegner.getRectangles().length; i++) {
                batch.draw(gegner.getEnemy_textureRegion(), gegner.getRectangleAnStelle(i).x, gegner.getRectangleAnStelle(i).y, 96, 96);
            }
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
            gegner.drawEnemys();
            zeichneGegner = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            follow();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            for (int i = 0; i < gegner.getRectangles().length; i++) {
                System.out.println(gegner.getRectangleAnStelle(i).x);
            }
        }
        if (zeichneGegner) {
            follow();
        }


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

        if (zeichneGegner) {
            Rectangle r = gegner.getRectangleAnStelle(1);
            if (spieler.player_rectangle.overlaps(r)) {
                System.out.println("Contact with 1");
                spieler.hp.decrease(1);
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
}
