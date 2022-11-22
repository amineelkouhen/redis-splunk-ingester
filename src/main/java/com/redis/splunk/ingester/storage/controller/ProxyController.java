package com.redis.splunk.ingester.storage.controller;

import com.redis.splunk.ingester.storage.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class ProxyController {
    @Autowired
    ProxyService proxyService;

    @RequestMapping("/**")
    public ResponseEntity interceptRequest(@RequestBody String body,
                                                   HttpServletRequest request) throws IOException {
        return proxyService.ingestPayload(body, request);
    }
}

