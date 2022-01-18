package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public Health hp = new Health(4, 10);
    private final Texture player_spritesheet = new Texture("Male 17-1.png");

    Rectangle player_rectangle = new Rectangle(1280 / 2 - 96 / 2, 720 / 2, 32, 32);
    public boolean killsEnemiesOnContact = false;
    //TextureRegions when standing
    TextureRegion player_left = new TextureRegion(player_spritesheet, 32, 32, 32, 32);
    TextureRegion player_right = new TextureRegion(player_spritesheet, 32, 64, 32, 32);
    TextureRegion player_front = new TextureRegion(player_spritesheet, 32, 0, 32, 32);
    TextureRegion player_back = new TextureRegion(player_spritesheet, 32, 96, 32, 32);

    //TextureRegions for Animations
    private final TextureRegion front1 = new TextureRegion(player_spritesheet, 0, 0, 32, 32);
    private final TextureRegion front3 = new TextureRegion(player_spritesheet, 64, 0, 32, 32);
    private final TextureRegion front2 = new TextureRegion(player_spritesheet, 32, 0, 32, 32);
    private final TextureRegion left1 = new TextureRegion(player_spritesheet, 0, 32, 32, 32);
    private final TextureRegion left2 = new TextureRegion(player_spritesheet, 32, 32, 32, 32);
    private final TextureRegion left3 = new TextureRegion(player_spritesheet, 64, 32, 32, 32);
    private final TextureRegion right1 = new TextureRegion(player_spritesheet, 0, 64, 32, 32);
    private final TextureRegion right2 = new TextureRegion(player_spritesheet, 32, 64, 32, 32);
    private final TextureRegion right3 = new TextureRegion(player_spritesheet, 64, 64, 32, 32);
    private final TextureRegion back1 = new TextureRegion(player_spritesheet, 0, 96, 32, 32);
    private final TextureRegion back2 = new TextureRegion(player_spritesheet, 32, 96, 32, 32);
    private final TextureRegion back3 = new TextureRegion(player_spritesheet, 64, 96, 32, 32);

    //arrays for animations
    TextureRegion[] walkFramesFront = {front1, front2, front3};
    TextureRegion[] walkFramesLeft = {left1, left2, left3};
    TextureRegion[] walkFramesRight = {right1, right2, right3};
    TextureRegion[] walkFramesBack = {back1, back2, back3};

    Animation<TextureRegion> walkFrontAnimation = new Animation<TextureRegion>(0.25f, walkFramesFront);
    Animation<TextureRegion> walkLeftAnimation = new Animation<TextureRegion>(0.25f, walkFramesLeft);
    Animation<TextureRegion> walkRightAnimation = new Animation<TextureRegion>(0.25f, walkFramesRight);
    Animation<TextureRegion> walkBackAnimation = new Animation<TextureRegion>(0.25f, walkFramesBack);
    float stateTime = 0f;


    public enum direction {
        FRONT, BACK, LEFT, RIGHT, WALKINGFRONT, WALKINGBACK, WALKINGLEFT, WALKINGRIGHT
    }

    direction playerdirection;

    public direction getPlayerdirection() {
        return playerdirection;
    }

    public void setPlayerdirection(direction playerdirection) {
        this.playerdirection = playerdirection;
    }

    public boolean KillsEnemiesOnContact() {
        return killsEnemiesOnContact;
    }

    public void setKillsEnemiesOnContact(boolean killsEnemiesOnContact) {
        this.killsEnemiesOnContact = killsEnemiesOnContact;
    }

    public void walkFront() {


    }
}
