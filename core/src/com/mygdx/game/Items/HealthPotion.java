package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Player;

public class HealthPotion extends Item{


    public HealthPotion(float x, float y, int width, int height, Player player, float spawnTime, Texture texture) {
        super(x, y, width, height, player, spawnTime, texture);
    }

    @Override
    public void onContact() {
        player.health.increase(1);
    }

    @Override
    public float getSpawnTime() {
        return spawnTime;
    }
}
