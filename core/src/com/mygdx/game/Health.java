package com.mygdx.game;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Health Class
 */
public class Health {

    private int health, max_health;

    /** Health Constructor
     *
     * @param health current health
     * @param max_health maximum healrh
     */
    public Health(int health, int max_health) {
        this.health = health;
        this.max_health = max_health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Health(int health) {
        this.health = health;
    }

    /** get current health
     *
     * @return current health
     */
    public int getHealth() {
        return health;
    }

    /** increase health up to maximum health
     *
     * @param amount
     * @return current health
     */
    public int increase(int amount) {
        health = min(health + amount, max_health);
        return health;
    }

    /** decrease health down to 0
     *
     * @param amount
     * @return current health
     */
    public int decrease(int amount) {
        health = max(health - amount, 0);
        return health;
    }
}
