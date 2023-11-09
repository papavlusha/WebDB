package Logger;

import org.apache.logging.log4j.Logger;

public class LogManager {

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(LogManager.class);

    public static void logException(Exception exception) {
        logger.error("Exception occurred", exception);
    }
}