package com.zephyr.work.overcooked;

import com.zephyr.work.AbstractWork;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 11:54 AM
 */
public class OvercookedWork extends AbstractWork {
    private static final Logger LOG = LoggerFactory.getLogger(OvercookedWork.class);

    private BlockingQueue<Order> comingOrders = new LinkedBlockingDeque<>(5);
    private OrderGenerator orderGenerator = new OrderGenerator(
        comingOrders,
        5000,
        5
    );
    private AtomicLong rewards = new AtomicLong(0);

    private ExecutorService cookExecutors = Executors.newFixedThreadPool(MAX_COOK_COUNT);
    private ExecutorService orderExecutor = Executors.newSingleThreadExecutor();

    private static final Long REWARDED_NEEDED = 200L;
    private static final int MAX_COOK_COUNT = 5;

    @Override
    protected void doWorkInternal() {
        orderExecutor.submit(() -> orderGenerator.start());

        Cook generalCook = new GeneralCook(
            String.valueOf("General cook 1"),
            comingOrders,
            rewards
        );
        Cook fastChopper = new FastChopper(
            String.valueOf("Fast chopper"),
            comingOrders,
            rewards
        );
        cookExecutors.submit(fastChopper);
        cookExecutors.submit(generalCook);

        long start = System.currentTimeMillis();

        while (rewards.get() < REWARDED_NEEDED) {
            try {
                Thread.sleep(1000);
                LOG.info("Current reward is {}", rewards.get());
            } catch (InterruptedException e) {
                LOG.error("Overcooked work got interrupted", e);
            }
        }

        LOG.info("Overcooked work finished with reward={}, timeElapsed={} ms", rewards.get(), System.currentTimeMillis() - start);
    }

    @Test
    public void test() {
        doWorkInternal();
    }
}
