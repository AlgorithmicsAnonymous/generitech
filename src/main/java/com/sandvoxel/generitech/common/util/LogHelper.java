package com.sandvoxel.generitech.common.util;

import com.sandvoxel.generitech.Reference;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {
    private static Logger logger = LogManager.getLogger(Reference.MOD_NAME);

    public static void log(Level logLevel, String message) {
        logger.log(logLevel, "[" + Reference.MOD_NAME + "] " + message);
    }

    public static void all(String message) {
        log(Level.ALL, message);
    }

    public static void debug(String message) {
        log(Level.DEBUG, message);
    }

    public static void trace(String message) {
        log(Level.TRACE, message);
    }

    public static void fatal(String message) {
        log(Level.FATAL, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }

    public static void warn(String message) {
        log(Level.WARN, message);
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void off(String message) {
        log(Level.OFF, message);
    }

    public static void internal(String message) {
        if (Platform.isDevEnv())
            log(Level.INFO, message);
    }
}
