package com.redis.splunk.ingester.storage.service;

import com.redis.splunk.ingester.decoder.service.S2SDecodeService;
import com.redis.splunk.ingester.transformer.service.TransformService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

@Service
public class ProxyService {
    @Autowired
    S2SDecodeService decodeService;
    @Autowired
    TransformService transformService;
    @Autowired
    StoreService storeService;

    private final static Logger logger = LogManager.getLogger(ProxyService.class);
    final static String S2S = "/services/collector/s2s";

    public ResponseEntity ingestPayload(String body, HttpServletRequest request) throws IOException {
        String requestUrl = request.getRequestURI();

        if(requestUrl.equals(S2S))
        {
            JSONObject decoded = decodeService.decode(request.getInputStream(), body);
            JSONObject transformed = transformService.transform(decoded, request);
            String saved = storeService.save(transformed);

            if (saved.equals("OK")) {
                logger.info("transaction done");
                return ResponseEntity.ok(HttpStatus.OK);
            } else
                return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
        else
        {
            JSONObject transformed = transformService.transform(new JSONObject(URLDecoder.decode(body, "UTF-8")), request);
            String saved = storeService.save(transformed);

            if (saved.equals("OK")) {
                logger.info("transaction done");
                return ResponseEntity.ok(HttpStatus.OK);
            } else
                return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
