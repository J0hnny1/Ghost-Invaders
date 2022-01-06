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


public class MyGdxGame extends ApplicationAdapter {

    //nur zu Testzwecken
    Texture enemy09_texture;
    TextureRegion enemy09_front;

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
    boolean zeichneGegner;
    Player player;
    Random random = new Random();
    Bullet bullet;
    boolean fireball_draw = false;
    Texture fireball_texture;
    Rectangle fireball_rectangle;
    Enemy gegnerEinzelnTest;

    // game entities or enemies ?
    ArrayList<GameEntity> gameEntities = new ArrayList<>();

    @Override
    public void create() {
        //Texturen
        healthTexture = new Texture("health.png");
        enemy09_texture = new Texture("Enemy 09-1.png");
        redpotion_texture = new Texture("red potion.png");

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

    }

    @Override
    public void render() {

        ScreenUtils.clear(216, 158, 85, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Start des Draw Prozess
        batch.begin();


        //Red Potion
        batch.draw(redpotion, redpotion_rectangle.x, redpotion_rectangle.y, 64, 64);

        batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y, 32, 32);
        //Spieler "Animationen"
        if (player.show_player_right)
            batch.draw(player.player_walk_right, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        if (player.show_player_left)
            batch.draw(player.player_walk_left, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        if (player.show_player_back)
            batch.draw(player.player_walk_back, player.player_rectangle.x, player.player_rectangle.y, 96, 96);
        if (player.show_player_front)
            batch.draw(player.player_walk_front, player.player_rectangle.x, player.player_rectangle.y, 96, 96);

        if (fireball_draw) {
            batch.draw(fireball_texture, fireball_rectangle.x, fireball_rectangle.y);
        }

        //Draw Hp Bar
        drawHealthIcons();


        if (fireball_draw) {
            drawFireBall();
        }

        // Zeichnen aller GameEntities
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            Rectangle r = e.getRectangle();
            batch.draw(e.getTextureRegion(), (int) e.getx(), (int) e.gety(), r.width, r.height);
        }

        //Ende Des Draw Prozess
        batch.end();

        // spawn enemies
        spawnRandomEnemies(5);


        //Movement der Gegner
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            Rectangle r = new Rectangle(e.getx(), e.gety(), 96, 96);

            e.move(player, gameEntities, Gdx.graphics.getDeltaTime());

            // check for collisions with the player TODO!
            /*
            if (spieler.player_rectangle.x + 96 == e.getx() || e.getx() + 96 == spieler.player_rectangle.x) {
                e.setXspeed(e.getxSpeed() * -1);
                e.setYspeed(e.getySpeed() * -1);
                System.out.println("awr");
            }
            if (r.overlaps(spieler.player_rectangle)) {
                e.setXspeed(e.getxSpeed() * -1);
                e.setYspeed(e.getySpeed() * -1);
            }
            */
        }

        if (player.player_rectangle.overlaps(redpotion_rectangle)) {
            player.hp.increase(1);
            System.out.println("Hp increase");
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
            drawFireBall();
            fireball_draw = true;
        }
        //Debug
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            gameEntities.add(new Enemy(gameEntities.size(), 0, 10, 14, 500, 100, enemy09_texture));
        }


    }

    @Override
    public void dispose() {
        batch.dispose();
        healthTexture.dispose();
    }

    public void drawHealthIcons() {
        for (int i = 0; i < player.hp.getHealth(); i++) {
            batch.draw(healthTexture, i * 32, 720 - 32);
        }
    }

    public void follow() {
        /*
        for (int i = 0; i < enemy_ghost.getEnemy_rectangles_arraylist().size(); i++) {
            if (enemy_ghost.getRectangleAnStelle(i).y != spieler.player_rectangle.y && enemy_ghost.getRectangleAnStelle(i).x != spieler.player_rectangle.x) {
                if (enemy_ghost.getRectangleAnStelle(i).x < spieler.player_rectangle.x) {
                    enemy_ghost.getRectangleAnStelle(i).x += 50 * Gdx.graphics.getDeltaTime();
                }
                if (enemy_ghost.getRectangleAnStelle(i).x > spieler.player_rectangle.x) {
                    enemy_ghost.getRectangleAnStelle(i).x -= 50 * Gdx.graphics.getDeltaTime();
                }
                //start_time_2 = System.currentTimeMillis();
                if (System.currentTimeMillis() - start_time_2 > 1000) {

                    if (enemy_ghost.getRectangleAnStelle(i).y > spieler.player_rectangle.y) {
                        enemy_ghost.getRectangleAnStelle(i).y -= 50 * Gdx.graphics.getDeltaTime();
                    }
                    if (enemy_ghost.getRectangleAnStelle(i).y < spieler.player_rectangle.y) {
                        enemy_ghost.getRectangleAnStelle(i).y += 50 * Gdx.graphics.getDeltaTime();

                    }
                }

            }
        }
        */

    }

    public void spawnRandomEnemies(int enemy_amount) {
        int r = random.nextInt(4);

        if (System.currentTimeMillis() - start_time_spawn > 10000) {
            start_time_spawn = System.currentTimeMillis();
            int speed = 0;

            switch (r) {
                case 0:
                    //top
                    for (int i = 0; i < enemy_amount; i++) {
                        speed = random.nextInt(70) + 80;
                        gameEntities.add(new Enemy(gameEntities.size(), 1, 0, -speed, random.nextInt(1280 - 96), 720 - 96, enemy09_texture));
                    }
                    break;
                case 1:
                    //bottom
                    for (int i = 0; i < enemy_amount; i++) {
                        speed = random.nextInt(70) + 80;
                        gameEntities.add(new Enemy(gameEntities.size(), 1, 0, speed, random.nextInt(1280 - 96), 0, enemy09_texture));
                    }
                    ;
                    break;
                case 2:
                    //left
                    for (int i = 0; i < enemy_amount; i++) {
                        speed = random.nextInt(70) + 80;
                        gameEntities.add(new Enemy(gameEntities.size(), 1, speed, 0, 0, random.nextInt(720 - 96), enemy09_texture));
                    }

                    break;
                case 3:
                    //right
                    for (int i = 0; i < enemy_amount; i++) {
                        speed = random.nextInt(70) + 80;
                        gameEntities.add(new Enemy(gameEntities.size(), 1, -speed, 0, 1280 - 96, random.nextInt(720 - 96), enemy09_texture));
                    }
                    break;
            }

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
