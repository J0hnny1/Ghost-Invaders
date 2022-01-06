package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Enemy implements GameEntity {
    private float x, y, xspeed, yspeed;
    private float movementspeed;
    private Health health;
    Rectangle rectangle;
    TextureRegion textureRegion;

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getxSpeed() {
        return xspeed;
    }

    @Override
    public float getySpeed() {
        return yspeed;
    }

    @Override
    public float getx() {
        return x;
    }

    @Override
    public float gety() {
        return y;
    }

    public Enemy(int health, int movementspeed, int x, int y, Texture texture) {
        this.health = new Health(health);
        this.movementspeed = movementspeed;
        xspeed = 10;
        yspeed = 69;
        this.rectangle = new Rectangle(x, y, 96, 96);
        this.textureRegion = new TextureRegion(texture, 0, 0, 32, 32);
    }

    @Override
    public void move(float x_ziel, float y_ziel, float deltaTime) {
        setPosition(x,rectangle.y + getySpeed() * deltaTime);
        System.out.println("move?");
        if (y + 96 < 700 && y >= 0 && x + 96 < 1280 && x > 0) {

        }

        // spieler.player_rectangle.y -= 250 * Gdx.graphics.getDeltaTime();
    }
}
