package com.zephyr.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/29/18
 * Time: 3:47 PM
 */
@Data
@NoArgsConstructor
public class Node {
    private String id = UUID.randomUUID().toString();
    private String ipAddress;
    private String name;
    private Set<Node> neighbours = new LinkedHashSet<>();

    public Node(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Node node = (Node) o;

        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
