package com.zephyr.network;

import com.zephyr.api.resp.NetworkStatistics;
import com.zephyr.member.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 1:00 PM
 */
public class Network {
    private static final Logger LOG = LoggerFactory.getLogger(Network.class);

    private Set<Node> nodes = new LinkedHashSet<>();
    private Entropy entropy = new Entropy();
    private volatile Date lastUpdatedAt = new Date();

    public void registerNode(Node node) {
        nodes.add(node);
        updateEntropyAndSo(node);
    }

    public void deregisterNode(Node node) {
        nodes.remove(node);
        updateEntropyAndSo(node);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public NetworkStatistics queryStatistics() {
        return new NetworkStatistics(
            nodes.size(),
            entropy.getValue(),
            lastUpdatedAt
        );
    }

    private void updateEntropyAndSo(Node node) {
        entropy.increase(Math.abs(node.hashCode()) % 10);
        lastUpdatedAt = new Date();
    }
}
