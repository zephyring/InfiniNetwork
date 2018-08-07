package com.zephyr.api.dto;

import lombok.Data;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 7:57 PM
 */
@Data
public class NeighborNode {
    private String id;
    private String ipAddress;
    private String name;

    public NeighborNode(String id, String ipAddress, String name) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.name = name;
    }
}
