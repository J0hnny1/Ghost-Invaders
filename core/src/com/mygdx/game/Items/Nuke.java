package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;
import java.util.Random;

public class Nuke extends Item {
    Random random = new Random();
    ArrayList<GameEntity> gameEntity;

    public Nuke(float x, float y, int width, int height, Player player, float spawnTime, Texture texture, ArrayList<GameEntity> gameEntity) {
        super(x, y, width, height, player, spawnTime, texture);
        this.gameEntity = gameEntity;
    }


    @Override
    public void onContact() {
        /*
        for (int i = 0; i < gameEntity.size() -1; i++) {
            GameEntity e = gameEntity.get(i);
            if (e.getEntityType() == entityType.ENEMY && player.player_rectangle.x - e.getx() < 500 && player.player_rectangle.y - e.gety() < 500) {
                gameEntity.remove(i);
                System.out.println("Remove Enemy");
            }
        }

         */
    }
}
