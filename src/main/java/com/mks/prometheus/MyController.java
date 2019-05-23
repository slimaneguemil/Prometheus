package com.mks.prometheus;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logger")
//@Timed
public class MyController {
    Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @Timed(value = "get.counter.requests", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
            "v1" })
    //@Timed(extraTags = {"type", "error"})
    @GetMapping("error")
    public void handleError(@RequestParam("message") String message){
        LOGGER.error("message is", message);
    }

    //@Timed(extraTags = {"type", "warn"})
    @GetMapping("warn")
    public void handleWarn(@RequestParam("message") String message){
        LOGGER.warn("message is", message);
    }

}
