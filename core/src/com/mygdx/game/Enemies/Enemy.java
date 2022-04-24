package com.mygdx.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Bullet;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Health;
import com.mygdx.game.Player;

import java.util.ArrayList;
import java.util.Random;

public class Enemy implements GameEntity {
    private final int id;
    private float x, y, xspeed, yspeed;
    private final Health health;
    Rectangle rectangle;
    TextureRegion textureRegion;
    Texture texture;
    TextureRegion front1;
    TextureRegion front2;
    TextureRegion front3;
    float stateTime = 0f;
    TextureRegion[] walkFront;
    Animation<TextureRegion> walkFrontAnimation;
    boolean shoots;
    Texture fireball2_texture = new Texture("myBall2.png");
    long start_time_bullet = System.currentTimeMillis();
    Random random = new Random();

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

    public Enemy(int id, int health, int xspeed, int yspeed, int x, int y, Texture texture, boolean shoots) {
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
        this.shoots = shoots;
    }


    public EntityType getEntityType() {
        return EntityType.ENEMY;
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


        if (shoots && System.currentTimeMillis() - start_time_bullet > 1000) {
            //gameEntities.add(new Bullet(gameEntities.size(), rectangle.x + 48, rectangle.y, 0, -100, fireball2_texture, false));
            start_time_bullet = System.currentTimeMillis();
            if (x > y) {
                if (random.nextBoolean())gameEntities.add(new Bullet(gameEntities.size(), rectangle.x + 96, rectangle.y + 48, 160, 0, fireball2_texture, false));
                else gameEntities.add(new Bullet(gameEntities.size(), rectangle.x, rectangle.y + 48, -160, 0, fireball2_texture, false));
            }else if (random.nextBoolean()) gameEntities.add(new Bullet(gameEntities.size(), rectangle.x + 48, rectangle.y, 0, -160, fireball2_texture, false));
            else gameEntities.add(new Bullet(gameEntities.size(), rectangle.x + 48, rectangle.y + 96, 0, 160, fireball2_texture, false));
        }


    }

    public Health getHealth() {
        return health;
    }
}
