package com.zephyr.work.overcooked;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 2:18 PM
 */
public class FastChopper extends GeneralCook {
    private static final Logger LOG = LoggerFactory.getLogger(FastChopper.class);

    public FastChopper(String name, BlockingQueue<Order> orders, AtomicLong rewards) {
        super(name, orders, rewards);
    }

    @Override
    public void chop(Material material) {
        material.setChopped(true);

        LOG.info("Fast chopper {} is chopping {}", name, material);
    }
}
