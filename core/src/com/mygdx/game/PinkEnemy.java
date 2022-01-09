package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class PinkEnemy extends Enemy{
    public PinkEnemy(int id, int health, int xspeed, int yspeed, int x, int y, Texture texture) {
        super(id, health, xspeed, yspeed, x, y, texture);
    }

    //@Override
    //public void setPosition(float x, float y) {
   //     super.setPosition(x, y);
    //}

    @Override
    public void move(Player player, ArrayList<GameEntity> gameEntities, float deltaTime) {
        //setPosition(getx() + getxSpeed() * Gdx.graphics.getDeltaTime(), gety() + getySpeed() * Gdx.graphics.getDeltaTime());
        if (gety() != player.player_rectangle.y && getx() != player.player_rectangle.x) {
            if (getx() < player.player_rectangle.x) {
                setX(getx() + getxSpeed() * Gdx.graphics.getDeltaTime());
            } else if (getx() > player.player_rectangle.x) {
                setX(getx() - getxSpeed() * Gdx.graphics.getDeltaTime());
            }
            if (gety() > player.player_rectangle.y) {
                setY(gety() - getySpeed() * Gdx.graphics.getDeltaTime());
            } else if (gety() < player.player_rectangle.y) {
                setY(gety() + getySpeed() * Gdx.graphics.getDeltaTime());
            }

        }
    }
}
