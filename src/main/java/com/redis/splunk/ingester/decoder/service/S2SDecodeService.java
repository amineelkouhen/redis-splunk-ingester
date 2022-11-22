package com.redis.splunk.ingester.decoder.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S2SDecodeService {

    private final static Logger logger = LogManager.getLogger(S2SDecodeService.class);

    public JSONObject decode(InputStream payload, String body) throws IOException {
        // Extract fields from binary and transform to JSON
        // Proprietary Code - From Splunk
        logger.info("decoding binary payload");
        return new JSONObject().accumulate("event", body);
    }
}
