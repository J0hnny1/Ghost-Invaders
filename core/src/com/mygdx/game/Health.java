package com.mygdx.game;

public class Health {

    private int health;

    public Health(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth() {
        health++;
    }

    public void damage(int d) {
        health = health -d;
        if (health == 0) {
            System.out.println("Du bist Tod Wichser");
        }
    }


}
