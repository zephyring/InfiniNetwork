package com.zephyr.work;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/6/18
 * Time: 10:41 PM
 */

import java.io.Serializable;

public interface Work extends Serializable {
    void doWork();
    WorkStatus status();
}
