package com.mks.prometheus;

import com.google.common.base.Stopwatch;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.SimpleTimer;
import io.prometheus.client.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Configuration
public class RunMe implements CommandLineRunner {
    private final Counter beat1 , beat2 ;
    private final static Logger LOGGER= LoggerFactory.getLogger(RunMe.class);
    private static SimpleTimer timer;
    Timer timer2, timer3 ;
    private final WebClient webClient;

    public RunMe(MeterRegistry registry, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://localhost:9001").build();
        this.timer2 = registry.timer("demoservice-timer2",
                "eventType", "test",
                "result", "perf1");

        this.timer3 =  Timer.builder("demoservice-timer3")
                .description("Duration in seconds for processing a request.")
                .sla(Duration.ofMillis(10), Duration.ofMillis(25),
                        Duration.ofMillis(50), Duration.ofMillis(100),
                        Duration.ofMillis(500), Duration.ofMillis(1000),
                        Duration.ofMillis(5000))
                .register(registry);


        this.beat1 = Counter
                .builder("demoservice_heartbeat_beat1")
                .description("a simple counter")
                .tags("beat", "beat1")
                .register(registry);
        this.beat2 = Counter
                .builder("demoservice_heartbeat_beat2")
                .description("a faster counter")
                .tags("beat", "beat2")
                .register(registry);



    }
    @Scheduled(cron = "${scheduled.job.time}")
    public void run(){
        //        timer = new SimpleTimer();
        //        //an API call goes here
        //        summary.observe(timer.elapsedSeconds());
    }

    public void intenseCalculation() {
        try {
            sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(String... args) throws Exception {

        final String uri = "http://localhost:9001/hello";


        while(true){

            long startTime = System.nanoTime();
            Stopwatch stopwatch = Stopwatch.createStarted(); // (guava stopwatch)
            intenseCalculation();
            Long t =stopwatch.stop().elapsed(TimeUnit.NANOSECONDS);
            LOGGER.info("timer= "+ t);
                //sleep(1000);
                beat1.increment(.5);
                beat2.increment(1);

            timer2.record(Duration.ofNanos(t));
            timer3.record(Duration.ofNanos(t));


//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);
//            LOGGER.info("get /hello = "+ result);

//            Mono<String> result2 = this.webClient.get().uri("/hello")
//                    .retrieve().bodyToMono(String.class);

//            result2.subscribe(System.out::println);
            }
        }



}
