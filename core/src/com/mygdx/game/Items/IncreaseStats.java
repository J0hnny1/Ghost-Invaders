package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Controller;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;

public class IncreaseStats extends Item {
    Controller controller;
    int bulletspeed, shootcooldown, movementspeed;

    public IncreaseStats(float x, float y, int width, int height, Player player, Texture texture, ArrayList<GameEntity> gameEntities, Controller controller) {
        super(x, y, width, height, player,  texture);
        this.controller = controller;
        this.bulletspeed = controller.getBulletspeed();
        this.shootcooldown = controller.getShootcooldown();
        this.movementspeed = controller.getMovementspeed();
    }

    @Override
    public void onContact() {
        player.setShootSpeedIncreased(true);
        controller.setBulletspeed(controller.getBulletspeed() + 10);
        controller.setShootcooldown(controller.getShootcooldown() - 5);
        controller.setMovementspeed(controller.getMovementspeed() + 10);
    }
}
