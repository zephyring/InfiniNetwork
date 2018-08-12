package com.zephyr.work.overcooked;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/12/18
 * Time: 11:56 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Set<Material> materials;
    private int reward;
}
