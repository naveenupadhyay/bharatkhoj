package com.bharatkhoj.metasearch.web;

import com.bharatkhoj.metasearch.domain.health.HealthResponse;
import com.bharatkhoj.metasearch.domain.health.PingResponse;
import com.bharatkhoj.metasearch.service.health.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    HealthService healthService;

    @RequestMapping(
            value="/ping",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PingResponse getPing() throws Exception {
        return healthService.getPingResponse();
    }

    @RequestMapping(
            value="/health",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HealthResponse getHealth() throws Exception {
        return healthService.getHealthResponse();
    }
}
