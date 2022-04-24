package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Player;

public class HealthPotion extends Item{


    public HealthPotion(float x, float y, int width, int height, Player player, Texture texture) {
        super(x, y, width, height, player, texture);
    }

    @Override
    public void onContact() {
        player.health.increase(1);
    }

}
