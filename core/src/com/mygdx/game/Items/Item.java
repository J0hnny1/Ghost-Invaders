package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;

public class Item implements GameEntity {
    Rectangle item_rectangle;
    float x;
    float y;
    Texture texture;
    TextureRegion textureRegion;
    Player player;

    public Item(float x, float y, int width, int height, Player player, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture, 0, 0, 16, 16);
        this.item_rectangle = new Rectangle(x, y, width, height);
        this.player = player;
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
    public Texture getTexture() {
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
    public void onContact() {

    }

    @Override
    public entityType getEntityType() {
        return entityType.ITEM;
    }
}
