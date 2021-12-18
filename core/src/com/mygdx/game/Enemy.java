package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;


public class Enemy {
    private final Rectangle[] rectangles = new Rectangle[5];
    private final Texture text = new Texture("Enemy 09-1.png");
    private final TextureRegion enemy_textureRegion = new TextureRegion(text, 0, 0, 32, 32);
    private Random random = new Random();
    private float rx;
    private float ry;
    private Health hp = new Health(1, 2);

    /**
     *
     * @return Rectangles Array
     */
    public Rectangle[] getRectangles() {
        return rectangles;
    }

    public TextureRegion getEnemy_textureRegion() {
        return enemy_textureRegion;
    }


    /**
     * Filles Rectangle Array with new Rectangles with random coordinates
     */
    public void drawEnemys() {
        for (int i = 0; i < rectangles.length; i++) {
            rx = random.nextInt(1280 - 64);
            ry = random.nextInt(720) - 64;
            rectangles[i] = new Rectangle(rx, ry, 96, 96);
        }
    }

    /**
     *
     * @param i
     * @return Rectangle on index i
     */
    public Rectangle getRectangleAnStelle(int i) {
        return rectangles[i];
    }
}
