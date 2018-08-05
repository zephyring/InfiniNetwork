package com.zephyr.api;

import com.zephyr.api.resp.NetworkStatistics;
import com.zephyr.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
}
