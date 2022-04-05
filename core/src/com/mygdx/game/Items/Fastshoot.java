package com.mygdx.game.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Controller;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;

public class Fastshoot extends Item{
    Controller controller;
    public Fastshoot(float x, float y, int width, int height, Player player, float spawnTime, Texture texture, ArrayList<GameEntity> gameEntities, Controller controller) {
        super(x, y, width, height, player, spawnTime, texture);
        this.controller = controller;
    }

    @Override
    public void onContact() {
        player.setShootSpeedIncreased(true);
        controller.setBulletspeed(controller.getBulletspeed() + 10);
        controller.setShootcooldown(controller.getShootcooldown() - 5);
        controller.setMovementspeed(controller.getMovementspeed() + 10);
    }
}
