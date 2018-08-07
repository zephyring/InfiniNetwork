package com.zephyr.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zephyr.network.NeighborDiscovery;
import com.zephyr.network.Network;
import com.zephyr.traffic.SporadicNodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/5/18
 * Time: 1:49 PM
 */
@Configuration
@EnableScheduling
public class AppConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public Network network() {
        return new Network();
    }

    @Bean
    public NeighborDiscovery neighborDiscovery(
        Network network
    ) {
        return new NeighborDiscovery(network);
    }

    @Bean
    public SporadicNodeGenerator sporadicNodeGenerator(
        Network network
    ) {
        return new SporadicNodeGenerator(network);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
