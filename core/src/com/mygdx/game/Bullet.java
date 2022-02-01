package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class Bullet implements GameEntity {
    private int id;
    private float x, y, xspeed, yspeed;
    Rectangle rectangle;
    Texture texture;
    public int enemieskilled2;

    public Bullet(int id, float x, float y, float xspeed, float yspeed, Texture texture) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.texture = texture;
        this.rectangle = new Rectangle(x, y, 32, 32);
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

    //TODO Zähler für getötete Gegner ohne das alles kaputt geht
    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {
        setPosition(x + xspeed * Gdx.graphics.getDeltaTime(), y + yspeed * Gdx.graphics.getDeltaTime());

        TreeSet<Integer> deleteEntities = new TreeSet<>();
        int thisIndex = gameEntities.indexOf(this);

        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            boolean isThis = e == this;

            // delete bullet when it leaves the screen
            if (isThis && (y + 32 > 700 || y < 0 || x + 32 > 1280 || x < 0)) {
                deleteEntities.add(i);
            }

            if (e.getEntityType() == entityType.ENEMY || e.getEntityType() == entityType.PINKENEMY) {
                if (e.getRectangle().overlaps(rectangle)) {
                    deleteEntities.add(i);
                    deleteEntities.add(thisIndex);
                }
            }
        }

        deleteEntities.descendingSet().forEach(i -> gameEntities.remove(i.intValue()));
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

    @Override
    public Animation<TextureRegion> getAnimation() {
        return null;
    }

    @Override
    public float getStateTime() {
        return 0;
    }

    @Override
    public void setStateTime(float stateTime) {

    }

    @Override
    public float getSpawnTime() {
        return 0;
    }

    @Override
    public int enemieskilled(int i) {
        return enemieskilled2 + i;
    }


}
