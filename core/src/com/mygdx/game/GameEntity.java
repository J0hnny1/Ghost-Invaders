package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public interface GameEntity {
    /**
     * @return rectangle
     */
     Rectangle getRectangle();

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
     * what happens if GameEntity overlaps other Rectangle
     */
    void onContact();

    /**
     * @return entityType
     */
    EntityType getEntityType();

    /**
     *
     * @return Animation
     */
    Animation<TextureRegion> getAnimation();

    /**
     * statetime used for animations
     * @return statetime
     */
    float getStateTime();

    /**
     * sets statetime used for animations
     * @param stateTime statetime
     */
    void setStateTime(float stateTime);

    /**
     * type of game entity
     */
    enum EntityType {
        ENEMY,
        ITEM,
        BULLET,
        PINKENEMY,
        POISON,
        BOUNCYBULLET
    }

    Class getCKlass();

    int getEnemiesKilled();
}
