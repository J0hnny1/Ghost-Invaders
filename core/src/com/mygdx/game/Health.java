package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;


public class Health {

    private int health;
    //MyGdxGame ke = new MyGdxGame();
    long lastDamage;

    public Health(int i) {

    }


    public int getHealth() {
        return health;
    }

    public void addHealth() {
        health++;
        //ke.batch.draw(ke.healthTexture, 102, 400 - 32);

    }

    public void damage(int d) {

        lastDamage = TimeUtils.nanoTime();
        if (lastDamage > 1000000000){
            health = health - d;
            System.out.println("schaden genommen");
        }
        System.out.println("Hp: " + health);

        if (health == 0) {
            System.out.println("Du bist Tod Wichser");
        }


    }


}
