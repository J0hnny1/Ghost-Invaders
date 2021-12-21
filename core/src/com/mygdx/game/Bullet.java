package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.graalvm.compiler.lir.StandardOp;

public class Bullet {
    private Texture bullet_texture = new Texture("bullet.jpg");
    private Rectangle[] rectangles_fireballs = new Rectangle[20];
    private boolean[] draw_fireball_array = new boolean[20];
    //private String direction;

    public Rectangle[] getCircles() {
        return rectangles_fireballs;
    }

    public Texture getBullet_texture() {
        return bullet_texture;
    }

    public void createBullet() {
        for (int i = 0; i < rectangles_fireballs.length; i++) {
            rectangles_fireballs[i] = new Rectangle(600,200,32,32);
        }
    }

    public void drawBullet(String direction) {

        switch (direction) {
            case "right":
                System.out.println("right");
                break;
            case "left":
                System.out.println("left");
                break;
            case "top":
                System.out.println("top");
                break;
            case "bottom":
                System.out.println("bottom");
        }

    }
}
