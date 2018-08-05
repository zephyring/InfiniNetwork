package com.zephyr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/29/18
 * Time: 3:47 PM
 */
@SpringBootApplication
public class InfiniNetwork {
    private static final Logger LOG = LoggerFactory.getLogger(InfiniNetwork.class);

    public static void main(String[] args) {
        SpringApplication.run(InfiniNetwork.class, args);
    }
}
