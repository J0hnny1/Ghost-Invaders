package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Item implements GameEntity {
    Rectangle item_rectangle;
    float x;
    float y;
    Texture texture;
    TextureRegion textureRegion;

    public Item(float x, float y, int width, int height, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture, 0, 0, 16, 16);
        this.item_rectangle = new Rectangle(x, y, width, height);
    }

    @Override
    public Rectangle getRectangle() {
        return item_rectangle;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return this.textureRegion;
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
        return x;
    }

    @Override
    public float gety() {
        return y;
    }

    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {
        // does nothing intentionally
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public entityType getType() {
        return entityType.ITEM;
    }
}
