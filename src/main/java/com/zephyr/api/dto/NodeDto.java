package com.zephyr.api.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 7:59 PM
 */
@Data
public class NodeDto {
    private String id;
    private String ipAddress;
    private String name;
    private List<NeighborNode> neighbors;

    public NodeDto(String id, String ipAddress, String name) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.name = name;
    }
}
