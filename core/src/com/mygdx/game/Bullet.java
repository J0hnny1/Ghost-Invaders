package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Bullet implements GameEntity {

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public void setPosition(float x, float y) {

    }

    @Override
    public float getxSpeed() {
        return 0;
    }

    @Override
    public float getySpeed() {
        return 0;
    }

    @Override
    public void setXspeed(float xspeed) {

    }

    @Override
    public void setYspeed(float yspeed) {

    }

    @Override
    public float getx() {
        return 0;
    }

    @Override
    public float gety() {
        return 0;
    }

    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void onContact() {

    }

    @Override
    public entityType getEntityType() {
        return null;
    }
}
