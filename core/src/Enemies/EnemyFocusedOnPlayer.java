package Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameEntity;
import com.mygdx.game.Player;

import java.util.ArrayList;

public class EnemyFocusedOnPlayer extends Enemy {
    public EnemyFocusedOnPlayer(int id, int health, int xspeed, int yspeed, int x, int y, Texture texture) {
        super(id, health, xspeed, yspeed, x, y, texture);
    }

    public EntityType getEntityType() {
        return EntityType.PINKENEMY;
    }

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
