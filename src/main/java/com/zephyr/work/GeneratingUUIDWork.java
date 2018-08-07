package com.zephyr.work;

import com.zephyr.network.Entropy;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/6/18
 * Time: 10:43 PM
 */
public class GeneratingUUIDWork extends AbstractWork {
    private static final Logger LOG = LoggerFactory.getLogger(GeneratingUUIDWork.class);

    private static final int ENTROPY_NEEDED = 3000;

    private Entropy entropy = new Entropy();

    @Override
    protected void doWorkInternal() {
        String uuid;

        int accumulated = 0;
        while (entropy.getValue() < ENTROPY_NEEDED) {
            uuid = UUID.randomUUID().toString();
            int delta = calculateEntropy(uuid);
            entropy.increase(delta);

            accumulated += delta;

            if (accumulated >= ENTROPY_NEEDED / 20) {
                LOG.info("Current entropy={}", entropy.getValue());
                accumulated = 0;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOG.error("Thread got interrupted", e);
            }
        }
    }

    private int calculateEntropy(String input) {
        return Math.abs(input.hashCode()) % 10 + 1;
    }

    @Test
    public void test() {
        doWorkInternal();
    }
}
