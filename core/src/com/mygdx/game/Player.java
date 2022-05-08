package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Player {
    public Health health;
    int hp;
    int maxhp;
    boolean shootSpeedIncreased = false;
    public boolean killsEnemiesOnContact = false;
    Preferences config = Gdx.app.getPreferences("ghostinvadorsconfig");
    Texture texture = new Texture(config.getString("PlayerTexture") + ".png");
    public final Rectangle player_rectangle = new Rectangle(592, 360, 32, 32);
    float stateTime = 0f;
    public boolean immunetoDamage = false;
    long start_time = System.currentTimeMillis();
    boolean playerTookDamage;
    Sound damageSound = Gdx.audio.newSound(Gdx.files.internal("damage.wav"));
    Controller controller;
    Sound flameAttack = Gdx.audio.newSound(Gdx.files.internal("fireball-explosion.wav"));
    private long start_time_bullet = System.currentTimeMillis();
    ArrayList<GameEntity> gameEntities;

    public enum Direction {
        FRONT, BACK, LEFT, RIGHT, WALKINGFRONT, WALKINGBACK, WALKINGLEFT, WALKINGRIGHT
    }

    Direction playerdirection;

    public Player(int hp, int maxhp, Controller controller, ArrayList<GameEntity> gameEntities) {
        this.hp = hp;
        this.maxhp = maxhp;
        health = new Health(hp, maxhp);
        texture = new Texture(config.getString("PlayerTexture") + ".png");
        playerdirection = Direction.BACK;
        this.controller = controller;
        this.gameEntities = gameEntities;
    }


    //TextureRegions when standing
    final TextureRegion player_left = new TextureRegion(texture, 32, 32, 32, 32);
    final TextureRegion player_right = new TextureRegion(texture, 32, 64, 32, 32);
    final TextureRegion player_front = new TextureRegion(texture, 32, 0, 32, 32);
    final TextureRegion player_back = new TextureRegion(texture, 32, 96, 32, 32);

    //TextureRegions for Animations
    private final TextureRegion front1 = new TextureRegion(texture, 0, 0, 32, 32);
    private final TextureRegion front3 = new TextureRegion(texture, 64, 0, 32, 32);
    private final TextureRegion front2 = new TextureRegion(texture, 32, 0, 32, 32);
    private final TextureRegion left1 = new TextureRegion(texture, 0, 32, 32, 32);
    private final TextureRegion left2 = new TextureRegion(texture, 32, 32, 32, 32);
    private final TextureRegion left3 = new TextureRegion(texture, 64, 32, 32, 32);
    private final TextureRegion right1 = new TextureRegion(texture, 0, 64, 32, 32);
    private final TextureRegion right2 = new TextureRegion(texture, 32, 64, 32, 32);
    private final TextureRegion right3 = new TextureRegion(texture, 64, 64, 32, 32);
    private final TextureRegion back1 = new TextureRegion(texture, 0, 96, 32, 32);
    private final TextureRegion back2 = new TextureRegion(texture, 32, 96, 32, 32);
    private final TextureRegion back3 = new TextureRegion(texture, 64, 96, 32, 32);

    //arrays for animations
    final TextureRegion[] walkFramesFront = {front1, front2, front3};
    final TextureRegion[] walkFramesLeft = {left1, left2, left3};
    final TextureRegion[] walkFramesRight = {right1, right2, right3};
    final TextureRegion[] walkFramesBack = {back1, back2, back3};

    final Animation<TextureRegion> walkFrontAnimation = new Animation<>(0.25f, walkFramesFront);
    final Animation<TextureRegion> walkLeftAnimation = new Animation<>(0.25f, walkFramesLeft);
    final Animation<TextureRegion> walkRightAnimation = new Animation<>(0.25f, walkFramesRight);
    final Animation<TextureRegion> walkBackAnimation = new Animation<>(0.25f, walkFramesBack);


    public void setShootSpeedIncreased(boolean shootSpeedIncreased) {
        this.shootSpeedIncreased = shootSpeedIncreased;
    }

    public void setPlayerdirection(Direction playerdirection) {
        this.playerdirection = playerdirection;
    }

    public void damage(int amount) {
        if (System.currentTimeMillis() - start_time > 2000) {
            start_time = System.currentTimeMillis();
            if (!config.getBoolean("GodMode") && !immunetoDamage) {
                health.decrease(amount);
                damageSound.play();
                playerTookDamage = true;
            }
        }
    }

    public void shoot(Direction shootDirection, boolean shotGun) {
        int bulSpeedX, bulSpeedY;
        float xPos, yPos;
        if (shootDirection == Direction.RIGHT || shootDirection == Direction.LEFT) {
            bulSpeedY = 0;
            yPos = player_rectangle.y + 48;
            if (shootDirection == Direction.RIGHT) {
                bulSpeedX = controller.bulletspeed;
                xPos = player_rectangle.x + 96;
            } else {
                bulSpeedX = -controller.bulletspeed;
                xPos = player_rectangle.x;
            }
        } else {
            bulSpeedX = 0;
            xPos = player_rectangle.x + 48;
            if (shootDirection == Direction.BACK) {
                bulSpeedY = controller.bulletspeed;
                yPos = player_rectangle.y + 96;
            } else {
                bulSpeedY = -controller.bulletspeed;
                yPos = player_rectangle.y;
            }
        }

        if (System.currentTimeMillis() - start_time_bullet > controller.shootcooldown) {
            if (shotGun) {
                yPos -= shootDirection == Direction.LEFT || shootDirection == Direction.RIGHT ? 80 : 0;
                xPos -= shootDirection == Direction.FRONT || shootDirection == Direction.BACK ? 80 : 0;
                int yPosD = shootDirection == Direction.LEFT || shootDirection == Direction.RIGHT ? 25 : 0;
                int xPosD = shootDirection == Direction.FRONT || shootDirection == Direction.BACK ? 25 : 0;
                for (int i = 0; i < 4; i++) {
                    gameEntities.add(new Bullet(gameEntities.size(), xPos += xPosD, yPos += yPosD, bulSpeedX, bulSpeedY, true, controller));
                }
            } else gameEntities.add(new Bullet(gameEntities.size(), xPos, yPos, bulSpeedX, bulSpeedY, true, controller));

            flameAttack.play();
            start_time_bullet = System.currentTimeMillis();
        }
    }
}
