package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;
import java.util.TreeSet;

public class ClearScreen extends Item {
    ArrayList<GameEntity> gameEntities;

    public ClearScreen(float x, float y, int width, int height, Player player, Texture texture, ArrayList<GameEntity> gameEntities) {
        super(x, y, width, height, player, texture);
        this.gameEntities = gameEntities;
    }

    @Override
    public void onContact() {
        TreeSet<Integer> deleteEntities = new TreeSet<>();
        int thisIndex = gameEntities.indexOf(this);


        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity e = gameEntities.get(i);
            if (e.getEntityType() == EntityType.ENEMY || e.getEntityType() == EntityType.PINKENEMY) {
                gameEntities.remove(i);
                deleteEntities.add(i);
            }

        }
        deleteEntities.add(thisIndex);

        deleteEntities.descendingSet().forEach(i -> gameEntities.remove(i.intValue()));

    }
}
