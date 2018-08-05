package com.zephyr.network;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 2:04 PM
 */
public class EntropyTest {
    private static final Logger LOG = LoggerFactory.getLogger(EntropyTest.class);

    private Entropy entropy = new Entropy();

    @Before
    public void before() {
        entropy.reset();
    }

    @Test
    public void testIncreaseEntropy() {
        assertEquals(5, entropy.increase(5));
        assertEquals(8, entropy.increase(3));
    }
}
