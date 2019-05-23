package com.mks.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MonitoringConfig {

    @Autowired
    BuildProperties buildProperties;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> registerCommonTags(
            @Value("${management.application.name:your-application-name}") String applicationName
    ) {
        return registry -> registry.config()
                .commonTags(
                        "application", applicationName,
                        "build.group", buildProperties.getGroup(),
                        "build.artifact", buildProperties.getArtifact(),
                        "build.version", buildProperties.getVersion());
    }

}