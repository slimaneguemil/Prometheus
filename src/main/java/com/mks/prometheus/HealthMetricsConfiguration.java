package com.mks.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;

@Configuration
class HealthMetricsConfiguration {
    // This should be a field so it doesn't get garbage collected
    private CompositeHealthIndicator healthIndicator;

    public HealthMetricsConfiguration(HealthAggregator healthAggregator,
                                      List<HealthIndicator> healthIndicators,
                                      MeterRegistry registry) {

        healthIndicator = new CompositeHealthIndicator(healthAggregator);

        for (Integer i = 0; i < healthIndicators.size(); i++) {
            healthIndicator.addHealthIndicator(i.toString(), healthIndicators.get(i));
        }

        // presumes there is a common tag applied elsewhere that adds tags for app, etc.
        registry.gauge("health", emptyList(), healthIndicator, health -> {
            Status status = health.health().getStatus();
            switch (status.getCode()) {
                case "UP":
                    return 3;
                case "OUT_OF_SERVICE":
                    return 2;
                case "DOWN":
                    return 1;
                case "UNKNOWN":
                default:
                    return 0;
            }
        });
    }
}
