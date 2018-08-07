package com.zephyr.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/7/18
 * Time: 10:09 AM
 */
public abstract class AbstractWork implements Work {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractWork.class);

    private WorkStatus workStatus = WorkStatus.BACKLOG;

    @Override
    public void doWork() {
        workStatus = WorkStatus.PROCESSING;

        doWorkInternal();

        workStatus = WorkStatus.DONE;
    }

    @Override
    public WorkStatus status() {
        return workStatus;
    }

    protected abstract void doWorkInternal();
}
