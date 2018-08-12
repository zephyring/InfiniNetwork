package com.zephyr.work.overcooked;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 1:15 PM
 */

/**
 *  a general cook can do everything, except it's slow for everything
 */
public class GeneralCook implements Cook {
    private static final Logger LOG = LoggerFactory.getLogger(GeneralCook.class);

    protected String name;
    protected BlockingQueue<Order> orders;
    private static final int ACTION_AVG_TIME_MILLI = 1000;
    protected AtomicLong rewards;

    public GeneralCook(String name, BlockingQueue<Order> orders, AtomicLong rewards) {
        this.name = name;
        this.orders = orders;
        this.rewards = rewards;
    }

    @Override
    public Order takeOrder() throws InterruptedException {
        prepareAction("takeOrder");

        Order order = orders.take();

        LOG.info("Cook {} is taking order, order={}", name, order);

        return order;
    }

    @Override
    public Plate takePlate() {
        prepareAction("takePlate");

        LOG.info("{} is taking plate...", name);

        return new Plate();
    }

    @Override
    public Material grabMaterial(Order order, Plate plate) {
        prepareAction("grabMaterial");

        // randomly select one from order that's not in plate yet
        Set<Material> candidates = order.getMaterials();
        Set<Material> alreadyInPlate = plate.getMaterials();

        Set<Material> choice = candidates.stream()
            .filter(m -> !alreadyInPlate.contains(m))
            .collect(Collectors.toSet());

        Iterator<Material> iter = choice.iterator();
        Material material = null;
        if (iter.hasNext()) {
            material = iter.next();
        }

        LOG.info("Cook {} is grabbing {}", name, material);

        return material;
    }

    @Override
    public void chop(Material material) {
        prepareAction("chop");

        try {
            Thread.sleep(material.getChopTimeMilli());
        } catch (InterruptedException e) {
            LOG.error("Failed to chop {}", material);
        }

        material.setChopped(true);

        LOG.info("Cook {} is chopping {}", name, material);
    }

    @Override
    public void prepareMeal(Material material, Plate plate) {
        prepareAction("prepareMeal");

        if (!material.isChopped()) {
            throw new IllegalStateException("Material needs to be chopped before putting into plate");
        }

        if (plate.getMaterials().contains(material)) {
            throw new IllegalStateException("This plate already has " + material);
        }

        LOG.info("Cook {} is preparing {} for meal", name, material);

        plate.getMaterials().add(material);
    }

    @Override
    public void finishOrder(Order order) {
        prepareAction("finishOrder");

        int reward = order.getReward();
        LOG.info("Cook {} is finishing order, gaining {} reward", name, reward);
        rewards.addAndGet(reward);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order order = takeOrder();
                Plate plate = takePlate();

                while (plate.getMaterials().size() != order.getMaterials().size()) {
                    Material material = grabMaterial(order, plate);
                    chop(material);
                    prepareMeal(material, plate);
                }

                finishOrder(order);

            } catch (InterruptedException e) {
                LOG.error("General cook failed to take order...", e);
            } catch (Exception e) {
                LOG.error("Cook {} failed to do work", name, e);
            }
        }
    }

    private void prepareAction(String action) {
        try {
            LOG.info("Cook {} is jumping around to {}", name, action);
            Thread.sleep(ACTION_AVG_TIME_MILLI);
        } catch (InterruptedException e) {
            LOG.error("General cook action interrupted");
        }
    }
}
