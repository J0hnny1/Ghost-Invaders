package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Enemy implements GameEntity {
    private int id;
    private float x, y, xspeed, yspeed;
    private Health health;
    Rectangle rectangle;
    TextureRegion textureRegion;
    Texture texture;
    TextureRegion front1;
    TextureRegion front2;
    TextureRegion front3;
    float stateTime = 0f;
    TextureRegion[] walkFront;
    Animation<TextureRegion> walkFrontAnimation;

    @Override

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public Texture getTexture() {
        return null;
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

    public void setX(float x) {
        this.rectangle.x = x;
        this.x = x;
    }

    public void setY(float y) {
        this.rectangle.y = y;
        this.y = y;
    }

    @Override
    public float gety() {
        return y;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void onContact() {

    }

    public Enemy(int id, int health, int xspeed, int yspeed, int x, int y, Texture texture) {
        this.id = id;
        this.health = new Health(health);
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(x, y, 96, 96);
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture, 0, 0, 32, 32);
        front1 = new TextureRegion(texture, 0, 0, 32, 32);
        front2 = new TextureRegion(texture, 32, 0, 32, 32);
        front3 = new TextureRegion(texture, 64, 0, 32, 32);
        walkFront = new TextureRegion[]{front1, front2, front3};
        walkFrontAnimation = new Animation<>(0.25f, walkFront);
    }


    public entityType getEntityType() {
        return entityType.ENEMY;
    }

    @Override
    public Animation<TextureRegion> getAnimation() {
        return walkFrontAnimation;
    }

    @Override
    public float getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    @Override
    public float getSpawnTime() {
        return 0;
    }

    @Override
    public int setEnemiesKilled() {
        return 0;
    }

    @Override
    public int getEnemiesKilled() {
        return 0;
    }


    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {
        setPosition(x + xspeed * Gdx.graphics.getDeltaTime(), y + yspeed * Gdx.graphics.getDeltaTime());

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
}
