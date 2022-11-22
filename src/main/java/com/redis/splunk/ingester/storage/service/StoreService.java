package com.redis.splunk.ingester.storage.service;

import com.redis.splunk.ingester.configuration.JedisConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Map;
import java.util.UUID;

@Service
public class StoreService {
    @Autowired
    JedisConfig jedisConfig;
    @Value("${stream.name}")
    String STREAM = "forward";
    private final static Logger logger = LogManager.getLogger(StoreService.class);

    public String save(JSONObject object){
        JedisPool pool = jedisConfig.getPool();
        try(Jedis client = pool.getResource()) {
            // Create a transaction
            logger.info("starting a transaction");
            Transaction transaction = client.multi();

            String randomUUID = UUID.randomUUID().toString();
            logger.info("adding object {} to stream", randomUUID);
            transaction.xadd(STREAM, StreamEntryID.NEW_ENTRY, Map.of("Object_ID", randomUUID));

            logger.info("saving object {} as JSON", randomUUID);
            Response<String> response = transaction.jsonSet(randomUUID, object);

            transaction.exec();
            return response.get();
        } catch (JedisConnectionException e){
            logger.error(e.getMessage());
            return "NOK";
        }
    }
}
