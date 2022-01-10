package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Bullet implements GameEntity {
    private int id;
    private float x, y, xspeed, yspeed;
    Rectangle rectangle;
    Texture texture;

    public Bullet(int id, float x, float y, float xspeed, float yspeed, Texture texture) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.texture = texture;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.rectangle.x = x;
        this.rectangle.y = y;

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
    public void setXspeed(float xspeed) {
        this.xspeed = xspeed;
    }

    @Override
    public void setYspeed(float yspeed) {
        this.yspeed = yspeed;
    }

    @Override
    public float getx() {
        return x;
    }

    @Override
    public float gety() {
        return y;
    }

    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {
        //setPosition(x + xspeed * Gdx.graphics.getDeltaTime(), y + yspeed * Gdx.graphics.getDeltaTime());
        if (y + 96 > 700) {
            yspeed *= -1;
            this.setPosition(this.x, 700 - 96);
        } else if (y < 0) {
            yspeed *= -1;
            this.setPosition(this.x, 0);
        }

        if (x + 96 > 1280) {
            xspeed *= -1;
            this.setPosition(1280 - 96, this.y);
        } else if (x < 0) {
            xspeed *= -1;
            this.setPosition(0, this.y);
        }

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void onContact() {

    }

    @Override
    public entityType getEntityType() {
        return entityType.BULLET;
    }
}
