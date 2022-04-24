package com.mygdx.game;

import com.mygdx.game.Enemies.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class Bullet implements GameEntity {
    private final int id;
    private float x, y, xspeed, yspeed;
    Rectangle rectangle;
    Texture texture;
    public int enemieskilled2;
    boolean playerBullet;
    boolean bouncy;
    boolean bouncyCheat;

    public Bullet(int id, float x, float y, float xspeed, float yspeed, Texture texture, boolean playerBullet, Controller controller) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.texture = texture;
        this.rectangle = new Rectangle(x, y, 32, 32);
        this.playerBullet = playerBullet;
        this.bouncy = controller.getBouncyBullets();
        this.bouncyCheat = controller.getBouncyBulletsCheat();
    }

    public Bullet(int id, float x, float y, float xspeed, float yspeed, Texture texture, boolean playerBullet) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.texture = texture;
        this.rectangle = new Rectangle(x, y, 32, 32);
        this.playerBullet = playerBullet;
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

    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {
        setPosition(x + xspeed * Gdx.graphics.getDeltaTime(), y + yspeed * Gdx.graphics.getDeltaTime());

        TreeSet<Integer> deleteEntities = new TreeSet<>();
        int thisIndex = gameEntities.indexOf(this);

        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            boolean isThis = e == this;

            // delete bullet when it leaves the screen

            if (isThis && (y + 32 > 732 || y < -32 || x + 32 > 1280 + 32 || x < -32)) {
                if (!bouncy && !bouncyCheat) deleteEntities.add(i);
                else {
                    xspeed *= -1;
                    yspeed *= -1;
                }
            }


            //check collision with enemies
            if (e.getEntityType() == EntityType.ENEMY || e.getEntityType() == EntityType.PINKENEMY) {
                if (e.getRectangle().overlaps(rectangle) && playerBullet) {
                    Enemy enemy = (Enemy) e;
                    //enemy.getHealth().decrease(1);
                    enemy.getHealth().decrease(1);
                    //enemy.getHealth().getHealth()
                    if (enemy.getHealth().getHealth() <= 0) {
                        deleteEntities.add(i);
                        enemieskilled2++;
                    }
                    deleteEntities.add(thisIndex);
                }
            }

            if (!playerBullet && player.player_rectangle.overlaps(rectangle)) {
                player.damage(1);
                deleteEntities.add(thisIndex);
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
    public EntityType getEntityType() {
        return EntityType.BULLET;
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
    public int setEnemiesKilled() {
        return 0;
    }

    @Override
    public int getEnemiesKilled() {
        return enemieskilled2;
    }


}
