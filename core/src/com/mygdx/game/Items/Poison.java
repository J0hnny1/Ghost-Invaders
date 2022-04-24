package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Player;

public class Poison extends Item {


    public Poison(float x, float y, int width, int height, Player player, Texture texture) {
        super(x, y, width, height, player, texture);
    }

    @Override
    public void onContact() {
        player.killsEnemiesOnContact = true;
        player.immunetoDamage = true;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.POISON;
    }
}
