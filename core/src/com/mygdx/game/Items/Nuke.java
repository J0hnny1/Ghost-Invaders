package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Player;

import java.util.Random;

public class Nuke extends Item {
    Random random = new Random();

    public Nuke(float x, float y, int width, int height, Player player, Texture texture) {
        super(x, y, width, height, player, texture);
    }

    @Override
    public void onContact() {

    }
}
