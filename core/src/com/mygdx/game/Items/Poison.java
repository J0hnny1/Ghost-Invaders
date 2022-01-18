package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

public class Poison extends Item{


    public Poison(float x, float y, int width, int height, Player player, float spawnTime, Texture texture) {
        super(x, y, width, height, player, spawnTime, texture);
    }

    @Override
    public void onContact() {
        player.killsEnemiesOnContact = true;
    }
}
