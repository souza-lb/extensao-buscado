package br.com.leonardo.manager;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {
    private static final String LOG_DIR = "log";
    private static final Logger logger = Logger.getLogger(LoggerManager.class.getName());

    public static void setupLogging() {
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) {
            logDir.mkdir();
        }

        try {
            FileHandler fileHandler = new FileHandler("./log/buscado.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        logger.severe(message);
    }
}
