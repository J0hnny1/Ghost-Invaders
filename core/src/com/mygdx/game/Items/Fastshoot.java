package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;

public class Fastshoot extends Item{
    public Fastshoot(float x, float y, int width, int height, Player player, float spawnTime, Texture texture, ArrayList<GameEntity> gameEntities) {
        super(x, y, width, height, player, spawnTime, texture);
    }

    @Override
    public void onContact() {
        player.setShootSpeedIncreased(true);
    }
}
