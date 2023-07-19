package com.example.library.log;


import com.example.library.controller.AuthController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Loggerr {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public static void log(){
        logger.info("[getMessage] info message");
        logger.warn("[getMessage] warn message");
        logger.error("[getMessage] error message");
        logger.debug("[getMessage] debug message");
        logger.trace("[getMessage] trace message");
    }

}
