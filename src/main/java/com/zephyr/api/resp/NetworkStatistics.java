package com.zephyr.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
