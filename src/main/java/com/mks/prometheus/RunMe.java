package com.mks.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RunMe implements CommandLineRunner {
    private final Counter beat1 , beat2 ;
    private final static Logger LOGGER= LoggerFactory.getLogger(RunMe.class);

    public RunMe(MeterRegistry registry) {

        this.beat1 = Counter
                .builder("demoservice_heartbeat_beat1")
                .description("a simple counter")
                .tags("beat", "beat1")
                .register(registry);
        this.beat2 = Counter
                .builder("demoservice_heartbeat_beat2")
                .description("a faster counter")
                .tags("beat", "beat2")
                .register(registry);;
    }


    @Override
    public void run(String... args) throws Exception {
        while(true){
            try {
                Thread.sleep(1000);
                beat1.increment(.5);
                beat2.increment(1);
                LOGGER.info("beat1= "+ beat1.count());
                LOGGER.error("simulaton of log error");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("interruption of program", e);
                return;
            }


        }
    }
}
