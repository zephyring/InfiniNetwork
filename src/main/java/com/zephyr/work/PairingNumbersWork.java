package com.zephyr.work;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/16/18
 * Time: 10:04 PM
 */

/**
 * given a random number generator, keep pairing up same numbers
 */
public class PairingNumbersWork extends AbstractWork {
    private static final Logger LOG = LoggerFactory.getLogger(PairingNumbersWork.class);

    private static final int PAIRING_RATE_MILLI = 1000;
    private static final int PAIRING_RANDOM = 5;
    private static final int PRODUCE_RATE_MILLI = 200;
    private static final int MAX_COUNTER = 1000;

    private Random random = new Random(System.currentTimeMillis());
    private BlockingQueue<Long> backlogs = new LinkedBlockingDeque<>();
    private long counter;

    @Override
    protected void doWorkInternal() {
        Thread pairingThread = new Thread(doPairing());
        pairingThread.setDaemon(true);
        pairingThread.start();

        Thread producingThread = new Thread(produceNumbers());
        producingThread.setDaemon(true);
        producingThread.start();

        try {
            pairingThread.join();
            producingThread.join();
        } catch (InterruptedException e) {
            LOG.error("Thread interrupted in main, interrupted={}", Thread.interrupted(), e);
        }
    }

    private Runnable doPairing() {
        return () -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(PAIRING_RATE_MILLI * (random.nextInt(PAIRING_RANDOM) + 1));
                } catch (InterruptedException e) {
                    LOG.error("Pairing thread interrupted, threadInterrupted={}", Thread.interrupted(), e);
                    Thread.currentThread().interrupt();
                }

                if (counter >= MAX_COUNTER) {
                    Thread.currentThread().interrupt();
                }

                List<Long> batch = new LinkedList<>();
                backlogs.drainTo(batch);

                Map<Long, List<Long>> mapped = batch.stream()
                    .collect(Collectors.groupingBy(i -> i));

                mapped.forEach((k, v) -> {
                    boolean odd = v.size() % 2 != 0;
                    IntStream.range(0, v.size() / 2).forEach(i -> {
                        backlogs.offer(2 * k);
                        counter++;
                        LOG.info("Pairing up {} and adding back {}, counter={}", k, 2 * k, counter);
                    });
                    if (odd) {
                        backlogs.offer(k);
                        LOG.info("Adding back unpaired {}", k);
                    }
                });
            }
        };
    }

    private Runnable produceNumbers() {
        return () -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(PRODUCE_RATE_MILLI);
                } catch (InterruptedException e) {
                    LOG.error("Producing numbers thread got interrupted, threadInterrupted={}", Thread.interrupted(), e);
                    Thread.currentThread().interrupt();
                }

                if (counter >= MAX_COUNTER) {
                    Thread.currentThread().interrupt();
                }

                backlogs.offer(1L);
            }
        };
    }

    @Test
    public void test() {
        doWorkInternal();
    }
}
