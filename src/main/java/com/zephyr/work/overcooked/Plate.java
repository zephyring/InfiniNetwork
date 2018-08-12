package com.zephyr.work.overcooked;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 1:05 PM
 */
@Data
public class Plate {
    private Set<Material> materials = new HashSet<>();
}
