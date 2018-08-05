package com.zephyr.network;

import com.zephyr.config.AppConfig;
import com.zephyr.member.Node;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 2:47 PM
 */
@ContextConfiguration(
    classes = {
        AppConfig.class
    }
)
public class NeighborDiscoveryTest {
    private static final Logger LOG = LoggerFactory.getLogger(NeighborDiscoveryTest.class);

    @Autowired
    private NeighborDiscovery neighborDiscovery;

    @Test
    public void testIsNeighbor() {
        Node n1 = new Node("abc"),
            n2 = new Node("abc"),
            n3 = new Node("abd"),
            n4 = new Node("acc"),
            n5 = new Node("ade"),
            n6 = new Node("bcd");

        assertTrue(NeighborDiscovery.isNeighbor(n1, n2, 0.9));
        assertTrue(NeighborDiscovery.isNeighbor(n1, n3, 0.6));
        assertFalse(NeighborDiscovery.isNeighbor(n1, n3, 0.7));
        assertTrue(NeighborDiscovery.isNeighbor(n1, n4, 0.6));
        assertFalse(NeighborDiscovery.isNeighbor(n1, n4, 0.7));
        assertTrue(NeighborDiscovery.isNeighbor(n1, n5, 0.3));
        assertFalse(NeighborDiscovery.isNeighbor(n1, n5, 0.4));
        assertFalse(NeighborDiscovery.isNeighbor(n1, n6, 0.1));
    }
}
