package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class Bullet {
    private Texture bullet_texture = new Texture("bullet.jpg");
    private Circle[] circles = new Circle[1];

    public Circle[] getCircles() {
        return circles;
    }

    public Texture getBullet_texture() {
        return bullet_texture;
    }

    public void createBullet() {
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle(100,100,40);
        }
    }
}
