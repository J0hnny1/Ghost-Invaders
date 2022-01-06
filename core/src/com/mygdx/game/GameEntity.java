package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface GameEntity {
    /**
     *
     * @return
     */
    public Rectangle getRectangle();

    /**
     *
     * @return
     */
    public TextureRegion getTextureRegion();

    public void setPosition(float x, float y);

    public float getxSpeed();

    public float getySpeed();

    public float getx();

    public float gety();

    public void move(float x_ziel, float y_ziel, float deltaTime);
}
