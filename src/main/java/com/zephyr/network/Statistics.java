package com.zephyr.network;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 8:06 PM
 */
@Data
public class Statistics {

    private AtomicLong addedNode = new AtomicLong(0);
    private AtomicLong removedNode = new AtomicLong(0);
    
}
