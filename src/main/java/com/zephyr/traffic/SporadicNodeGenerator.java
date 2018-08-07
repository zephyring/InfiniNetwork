package com.zephyr.traffic;

import com.zephyr.member.Node;
import com.zephyr.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 1:49 PM
 */
public class SporadicNodeGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(SporadicNodeGenerator.class);

    private Network network;

    public SporadicNodeGenerator(Network network) {
        this.network = network;
    }

    @Scheduled(fixedDelayString = "5000")
    public void addNode() {
        network.registerNode(new Node());
    }

    @Scheduled(fixedDelayString = "5000")
    public void removeNode() {
        Set<Node> nodes = network.getNodes();

        final Iterator<Node> each = nodes.iterator();
        while (each.hasNext()) {
            Node node = each.next();
            if (node.getNeighbours().size() > 3) {
                each.remove();
                network.deregisterNode(node);
            }
        }
    }

}
