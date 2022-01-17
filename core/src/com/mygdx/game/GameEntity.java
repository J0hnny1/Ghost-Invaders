package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public interface GameEntity {
    /**
     * @return rectangle
     */
     Rectangle getRectangle();

    /**
     * @return TextureRegion
     */
     TextureRegion getTextureRegion();

    /**
     * sets x and y coordinates of Gameentity
     *
     * @param x coordinate
     * @param y coordinate
     */
     void setPosition(float x, float y);
     Texture getTexture();

    /**
     * @return speed in x direction
     */
     float getxSpeed();

    /**
     * @return speed in y direction
     */
     float getySpeed();


    /**
     * sets speed in x direction
     *
     * @param xspeed speed in x direction
     */
    void setXspeed(float xspeed);

    /**
     * sets speed in y direction
     *
     * @param yspeed speed in y direction
     */
    void setYspeed(float yspeed);

    /**
     * @return x position
     */
     float getx();

    /**
     * @return y position
     */
    float gety();

    /**
     * @param player       Player object
     * @param gameEntities Arraylist of GameEntity
     * @param deltaTime    deltaTime
     */
    void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime);

    /**
     * @return GamEntity id
     */
    int getId();

    /**
     * what happens if GameEntity overlaps other Rectangle
     */
    void onContact();

    /**
     * @return entityType
     */
    entityType getEntityType();

    enum entityType {
        ENEMY,
        ITEM,
        BULLET,
        PINKENEMY
    }
}
