package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    Health hp = new Health(4,10);
    private final Texture player_spritesheet = new Texture("Male 17-1.png");
    TextureRegion player_walk_left = new TextureRegion(player_spritesheet, 0, 32, 32, 32);
    TextureRegion player_walk_right = new TextureRegion(player_spritesheet, 0, 64, 32, 32);
    TextureRegion player_walk_front = new TextureRegion(player_spritesheet, 0, 0, 32, 32);
    TextureRegion player_walk_back = new TextureRegion(player_spritesheet, 0, 96, 32, 32);
    boolean show_player_front = true;
    boolean show_player_back;
    boolean show_player_left;
    boolean show_player_right;
    Rectangle player_rectangle = new Rectangle(1280 / 2 - 32 / 2, 720 / 2, 32, 32);



}
