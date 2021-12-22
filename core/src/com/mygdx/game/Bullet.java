package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.graalvm.compiler.lir.StandardOp;

import java.util.ArrayList;

public class Bullet {
    private Texture bullet_texture = new Texture("bullet.jpg");
    ArrayList<Rectangle> rectangles_fireballs = new ArrayList<>();
    private boolean[] draw_fireball_array = new boolean[20];
    private int c = 0;

    public ArrayList<Rectangle> getCircles() {
        return rectangles_fireballs;
    }

    public Texture getBullet_texture() {
        return bullet_texture;
    }

    public void createBullet() {
        for (int i = 0; i < rectangles_fireballs.size(); i++) {
            rectangles_fireballs.add(new Rectangle(600,200,32,32));
            c++;
        }
    }

    public void drawBullet(String direction) {
        for (int i = 0; i < draw_fireball_array.length; i++) {
            if (rectangles_fireballs.get(i) == null) {
                draw_fireball_array[i] = true;
            }
        }

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
