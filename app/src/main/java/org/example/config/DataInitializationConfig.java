package org.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for database initialization logging.
 */
@Configuration
public class DataInitializationConfig {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializationConfig.class);
    
    /**
     * Log when database initialization is complete.
     * @return CommandLineRunner that logs completion message
     */
    @Bean
    public CommandLineRunner logDatabaseInit() {
        return args -> {
            log.info("Database initialized with test data");
        };
    }
}
