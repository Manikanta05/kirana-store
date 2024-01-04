package com.mani.kiranastore.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppLogger {
    static Logger logger = LogManager.getLogger(AppLogger.class);

    public static void setLog(String message){
        logger.info(message);
    }

    public static void setError(String message){
        logger.error(message);
    }
}
