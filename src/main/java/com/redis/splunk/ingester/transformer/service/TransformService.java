package com.redis.splunk.ingester.transformer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Service
public class TransformService {

    private final static Logger logger = LogManager.getLogger(TransformService.class);

    public JSONObject transform(JSONObject payload, HttpServletRequest request) {
        JSONObject event = payload.getJSONObject("event");

        logger.info("adding path and method to event");
        enrich(event, "path", request.getRequestURI());
        enrich(event, "method", request.getMethod());

        logger.info("adding headers to event");
        JSONObject headersObject = new JSONObject();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            enrich(headersObject, headerName, request.getHeader(headerName));
        }

        enrich(event, "headers", headersObject);
        return payload;
    }

    private JSONObject enrich(JSONObject object, String key, Object value) {
        return object.accumulate(key, value);
    }
}
