package com.redis.splunk.ingester.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

@Component
public class JedisConfig {
    @Value("${redis.host}")
    String host = "localhost";
    @Value("${redis.port}")
    Integer port = 6379;
    @Value("${stream.name}")
    String STREAM_NAME = "forward";
    @Value("${stream.group}")
    String GROUP_NAME = "splunk-app-group";
    private final static Logger logger = LogManager.getLogger(JedisConfig.class);

    @Bean
    public JedisPool getPool() {
        final JedisPool pool = new JedisPool(host, port);
        logger.info("host {} - port {}", host, port);
        try (Jedis jedis = pool.getResource()){
            logger.info("stream group created: {}", jedis.xgroupCreate(STREAM_NAME, GROUP_NAME, StreamEntryID.LAST_ENTRY, true));
        } catch (JedisDataException | JedisConnectionException e){}
        return pool;
    }

}