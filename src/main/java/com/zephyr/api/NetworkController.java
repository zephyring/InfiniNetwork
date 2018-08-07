package com.zephyr.api;

import com.zephyr.api.dto.NeighborNode;
import com.zephyr.api.dto.NodeDto;
import com.zephyr.api.resp.NetworkStatistics;
import com.zephyr.member.Node;
import com.zephyr.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 1:02 PM
 */
@RestController
@RequestMapping("/v1")
public class NetworkController {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkController.class);

    @Autowired
    private Network network;

    @GetMapping(path = "/holla")
    public String holla() {
        return "OK, now is " + new Date();
    }

    @GetMapping(path = "/network/statistics")
    public NetworkStatistics queryNetworkStatistics() {
        return network.queryStatistics();
    }

    @GetMapping(path = "/network/nodes")
    public Set<NodeDto> queryNodes() {
        Set<Node> nodes = network.getNodes();

        Set<NodeDto> ret = new LinkedHashSet<>();
        nodes.forEach(n -> {
            NodeDto dto = new NodeDto(n.getId(), n.getIpAddress(), n.getName());
            dto.setNeighbors(n.getNeighbours().stream()
                .map(nn -> new NeighborNode(nn.getId(), nn.getIpAddress(), nn.getName()))
                .collect(Collectors.toList()));
            ret.add(dto);
        });

        return ret;
    }
}
