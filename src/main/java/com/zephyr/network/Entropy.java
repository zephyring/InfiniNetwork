package com.zephyr.network;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 2:02 PM
 */
@Data
public class Entropy {

    private AtomicLong value = new AtomicLong(0L);

    public long increase(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Entropy should always increase");
        }

        return value.addAndGet(delta);
    }

    public void reset() {
        this.value.set(0);
    }

    public long getValue() {
        return value.get();
    }
}
