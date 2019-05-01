package com.mks.prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logger")
public class LoggerController {

private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFactory.class);

    @GetMapping("error")
    public String logErrorMessage(@RequestParam("message") String message)
    {
        LOGGER.error("Received ERROR:{}", message);
        return message;
    }


    @GetMapping("warn")
    public String logWarnMessage(@RequestParam("message") String message)
    {
        LOGGER.error("Received WARN:{}", message);
        return message;
    }

}
