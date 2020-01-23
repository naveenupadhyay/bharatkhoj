package com.bharatkhoj.metasearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author naveenu
 *
 */
@SpringBootApplication
@ComponentScan
@EntityScan
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, 
		HibernateJpaAutoConfiguration.class, ElasticsearchRepositoriesAutoConfiguration.class, 
		ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class Application extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("server started sucessfully");
    }
}