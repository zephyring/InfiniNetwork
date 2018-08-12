package com.zephyr.work.overcooked;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 11:57 AM
 */
public enum Material {
    LETTUCE,
    CARROT,
    CUCUMBER,
    FISH,
    MEAT,

    ;

    private boolean chopped = false;

    public int getChopTimeMilli() {
        return Math.abs(hashCode()) % 1000 + 1;
    }

    public boolean isChopped() {
        return chopped;
    }

    public void setChopped(boolean chopped) {
        this.chopped = chopped;
    }
}
