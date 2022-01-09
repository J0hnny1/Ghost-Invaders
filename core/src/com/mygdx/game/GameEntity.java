package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public interface GameEntity {
    /**
     * @return
     */
    public Rectangle getRectangle();

    /**
     * @return
     */
    public TextureRegion getTextureRegion();

    public void setPosition(float x, float y);

    public float getxSpeed();

    public float getySpeed();


    void setXspeed(float xspeed);

    void setYspeed(float yspeed);

    public float getx();

    public float gety();

    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime);

    public int getId();

    public void onContact();

    public entityType getEntityType();

    public enum entityType {
        ENEMY,
        ITEM,
        BULLET
    }
}
