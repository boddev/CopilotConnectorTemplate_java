package com.copilotconnector.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void logInformation(String message, Object... args) {
        logger.info(message, args);
    }

    public void logError(String message, Object... args) {
        logger.error(message, args);
    }

    public void logWarning(String message, Object... args) {
        logger.warn(message, args);
    }
}