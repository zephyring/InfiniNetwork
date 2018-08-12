package com.zephyr.work.overcooked;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 12:32 PM
 */
public interface Cook extends Runnable {
    Order takeOrder() throws InterruptedException;
    Plate takePlate();
    Material grabMaterial(Order order, Plate plate);
    void chop(Material material);
    void prepareMeal(Material material, Plate plate);
    void finishOrder(Order order);
}
