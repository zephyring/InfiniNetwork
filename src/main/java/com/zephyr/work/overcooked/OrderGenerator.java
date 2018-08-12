package com.zephyr.work.overcooked;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 12:07 PM
 */
public class OrderGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(OrderGenerator.class);

    private Random random = new Random();
    private BlockingQueue<Order> consumers;
    private int rateMilli;
    private int rewardLevel;

    public OrderGenerator(BlockingQueue<Order> consumers, int rateMilli, int rewardLevel) {
        this.consumers = consumers;
        this.rateMilli = rateMilli;
        this.rewardLevel = rewardLevel;
    }

    public void start() {
        Material[] allMaterials = Material.values();

        while (true) {
            // number of different materials in an order
            int numM = Math.abs(random.nextInt(allMaterials.length)) + 1;

            Set<Material> materials = new HashSet<>();
            while (materials.size() < numM) {
                int choiceIdx = Math.abs(random.nextInt(allMaterials.length));
                materials.add(allMaterials[choiceIdx]);
            }


            try {
                int reward = calculateReward(materials, rewardLevel);
                LOG.info("Adding new order, materials={}, reward={}", materials, reward);
                consumers.put(new Order(
                    materials,
                    reward
                ));

                Thread.sleep(rateMilli);
            } catch (InterruptedException e) {
                LOG.error("Failed to generate new orders...", e);
            }
        }
    }

    private int calculateReward(Set<Material> materials, int base) {
        int ret = 0;

        for (Material m : materials) {
            ret += Math.abs(m.hashCode()) % base + 1;
        }

        return ret;
    }
}
