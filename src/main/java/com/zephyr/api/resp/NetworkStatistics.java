package com.zephyr.api.resp;

import com.zephyr.network.Statistics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 1:52 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkStatistics {
    private int nodeCnt;
    private long entropy;
    private Date lastUpdatedAt;
    private Statistics statistics;

}
