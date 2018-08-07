package com.zephyr.network;

import com.zephyr.member.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 2:38 PM
 */
public class NeighborDiscovery {
    private static final Logger LOG = LoggerFactory.getLogger(NeighborDiscovery.class);

    private Network network;

    public NeighborDiscovery(Network network) {
        this.network = network;
    }

    @Scheduled(fixedDelayString = "5000")
    public void discoverNeighbors() {
        Set<Node> nodes = network.getNodes();

        // pair nodes up when their ids are similar
        calculateNeighbors(nodes, 0.2);
    }

    private void calculateNeighbors(Set<Node> nodes, double threshold) {
        List<Node> arr = nodes.stream()
            .collect(Collectors.toList());

        for (int i = 0; i < arr.size() - 1; i++) {
            Node n1 = arr.get(i);
            for (int j = i + 1; j < arr.size(); j++) {
                Node n2 = arr.get(j);
                if (isNeighbor(n1, n2, threshold)) {
                    LOG.info("Found similar neighbours for nodeId={}, {}",
                        n1.getId(),
                        n2.getId()
                    );
                    n1.getNeighbours().add(n2);
                    n2.getNeighbours().add(n1);
                }
            }
        }
    }

    /*
        return true if their ids are similar
     */
    public static boolean isNeighbor(Node n1, Node n2, double threshold) {
        String id1 = n1.getId(),
            id2 = n2.getId();

        if (id1.length() != id2.length()) {
            return false;
        }

        double sameCnt = 0;
        for (int i = 0; i < id1.length(); i++) {
            if (id1.charAt(i) == id2.charAt(i)) {
                sameCnt++;
            }
        }

        return sameCnt / id1.length() > threshold;
    }
}
