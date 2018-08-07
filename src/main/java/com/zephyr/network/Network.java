package com.zephyr.network;

import com.zephyr.api.resp.NetworkStatistics;
import com.zephyr.member.Node;
import com.zephyr.work.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
    private Statistics statistics = new Statistics();
    private List<Work> backlogs = new LinkedList<>();

    public void registerNode(Node node) {
        nodes.add(node);
        updateEntropyAndSo(node);
        statistics.getAddedNode().incrementAndGet();
    }

    public void deregisterNode(Node node) {
        updateEntropyAndSo(node);
        statistics.getRemovedNode().incrementAndGet();
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public NetworkStatistics queryStatistics() {
        return new NetworkStatistics(
            nodes.size(),
            entropy.getValue(),
            lastUpdatedAt,
            statistics
        );
    }

    private void updateEntropyAndSo(Node node) {
        entropy.increase(Math.abs(node.hashCode()) % 10 + 1);
        lastUpdatedAt = new Date();
    }
}
