package com.bharatkhoj.metasearch.service.health;

import com.bharatkhoj.metasearch.domain.health.HealthResponse;
import com.bharatkhoj.metasearch.domain.health.PingResponse;

import java.io.IOException;


public interface HealthService {

    PingResponse getPingResponse() throws IOException;
    HealthResponse getHealthResponse() throws IOException;

}
