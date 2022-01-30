package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public Health hp = new Health(4, 10);

    Texture defaultTexture = new Texture("Male 17-1.png");
    Texture malered = new Texture("Male 01-1.png");
    Texture maleblue = new Texture("Male 04-4.png");
    Texture femalered = new Texture("Female 04-3.png");
    Texture femalepink = new Texture("Female 09-2.png");
    Texture femalepink2 = new Texture("Female 25-1.png");
    Texture player_spritesheet = defaultTexture;

    public Rectangle player_rectangle = new Rectangle(592, 360, 32, 32);
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

    Animation<TextureRegion> walkFrontAnimation = new Animation<>(0.25f, walkFramesFront);
    Animation<TextureRegion> walkLeftAnimation = new Animation<>(0.25f, walkFramesLeft);
    Animation<TextureRegion> walkRightAnimation = new Animation<>(0.25f, walkFramesRight);
    Animation<TextureRegion> walkBackAnimation = new Animation<>(0.25f, walkFramesBack);
    float stateTime = 0f;


    public enum direction {
        FRONT, BACK, LEFT, RIGHT, WALKINGFRONT, WALKINGBACK, WALKINGLEFT, WALKINGRIGHT
    }
    public enum playerTexture{
        DEFAULT, FEMALERED, FEMALEPINK, FEMALEPINK2, MALERED, MALEBLUE
    }

    direction playerdirection;

    public void setPlayerdirection(direction playerdirection) {
        this.playerdirection = playerdirection;
    }

    public void setTexture(playerTexture i) {
        switch (i) {
            case FEMALEPINK2 -> player_spritesheet = femalepink2;
            case FEMALEPINK -> player_spritesheet = femalepink;

            case FEMALERED -> player_spritesheet = femalered;
            case MALEBLUE -> player_spritesheet = maleblue;
            case MALERED -> player_spritesheet = malered;
        }
    }
}
