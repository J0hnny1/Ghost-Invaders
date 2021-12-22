package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Enemy {

    //private final Rectangle[] rectangles = new Rectangle[5];
    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private Boolean[] check_enemy_draw = new Boolean[5];
    private final Texture text = new Texture("Enemy 09-1.png");
    private final TextureRegion enemy_textureRegion = new TextureRegion(text, 0, 0, 32, 32);
    private Random random = new Random();
    private Health hp = new Health(1, 2);

    /**
     * @return Rectangles Array
     */
    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public TextureRegion getEnemy_textureRegion() {
        return enemy_textureRegion;
    }


    /**
     * Filles Rectangle Array with new Rectangles with random coordinates
     */
    public void createEnemys() {
        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.add(new Rectangle(random.nextInt(1280 - 64), random.nextInt(720) - 64, 96, 96));
            check_enemy_draw[i] = true;
        }
    }

    public void createEnemysTop() {
        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.add(new Rectangle(random.nextInt(1280 - 64), ThreadLocalRandom.current().nextInt(720 - 64, 720 + 1), 96, 96));
            check_enemy_draw[i] = true;
        }
    }

    public void createEnemysBottom() {
        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.add(new Rectangle(random.nextInt(1280 - 64), ThreadLocalRandom.current().nextInt(-64, 32 + 1), 96, 96));
            check_enemy_draw[i] = true;
        }
    }

    public void createEnemysLeft() {
        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.add(new Rectangle(ThreadLocalRandom.current().nextInt(-64, 64 + 1), random.nextInt(720 - 64), 96, 96));
            check_enemy_draw[i] = true;
        }
    }

    public void createEnemysRight() {
        for (int i = 0; i < rectangles.size(); i++) {
            rectangles.add(new Rectangle(ThreadLocalRandom.current().nextInt(1280 - 64, 1280 + 64 + 1), random.nextInt(720) - 64, 96, 96));
            check_enemy_draw[i] = true;
        }
    }

    /**
     * @param i
     * @return Rectangle on index i
     */
    public Rectangle getRectangleAnStelle(int i) {
        return rectangles.get(i);
    }
}
