package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Controller;
import com.mygdx.game.Player;

public class BouncyBullets extends Item {
    Controller controller;

    public BouncyBullets(float x, float y, int width, int height, Player player, Texture texture, Controller controller) {
        super(x, y, width, height, player, texture);
        this.controller = controller;
    }

    @Override
    public void onContact() {
        controller.bouncyBullets = true;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BOUNCYBULLET;
    }
}
