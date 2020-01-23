package com.bharatkhoj.metasearch.service.health;

import com.bharatkhoj.metasearch.domain.health.HealthResponse;
import com.bharatkhoj.metasearch.domain.health.PingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service("HealthService")
public class HealthServiceImpl implements HealthService {

    public final static String GIT_COMMIT_ID="git.commit.id";
    Map<String,String> gitProperties = new HashMap<String,String>();

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private BuildProperties buildProperties;

    @Override
    public PingResponse getPingResponse() throws IOException {
        long curTime = System.currentTimeMillis();
        PingResponse response = new PingResponse();
        Resource resource = resourceLoader.getResource("classpath:git.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        response.setProgramName(buildProperties.getName());
        response.setVersion(buildProperties.getVersion());
        response.setRelease(props.getProperty(GIT_COMMIT_ID));
        response.setDatetime(System.currentTimeMillis());
        response.setStatus("success");
        response.setCode(200);
        response.getData().setMessage("My service is healthy");
        long timeDiff = System.currentTimeMillis() - curTime;
        response.getData().setDuration(timeDiff);
        return response;
    }

    public HealthResponse getHealthResponse() throws  IOException{
        long curTime = System.currentTimeMillis();
        HealthResponse response = new HealthResponse();
        Map<String, HealthResponse.DependentService> serviceHealth = new HashMap<>();
        Resource resource = resourceLoader.getResource("classpath:git.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        response.setProgramName(buildProperties.getName());
        response.setVersion(buildProperties.getVersion());
        response.setRelease(props.getProperty(GIT_COMMIT_ID));
        response.setDatetime(System.currentTimeMillis());
        response.setStatus("successfull");
        response.setCode(200);
        response.setMessage("ok");
        response.getData().setMessage("My service is healthy");
        long timeDiff = System.currentTimeMillis() - curTime;
        response.getData().setDuration(timeDiff);
        System.out.println(response.toString());
        return response;
    }
}
