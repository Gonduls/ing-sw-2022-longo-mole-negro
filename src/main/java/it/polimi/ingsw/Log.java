package it.polimi.ingsw;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Defines a local Log class, for debugging
 */
public class Log {
    public static final Logger logger = Logger.getLogger("test");
    private FileHandler fh;
    private static boolean debug;

    /**
     * Adds a file to the logger, every file added will share all the log information from that point forward
     * @param fileName The name of the file to add
     */
    public Log(String fileName) {
        File f = new File(fileName);
        logger.setUseParentHandlers(false);

        if(!debug)
            return;

        try{
            if(!f.exists() && !f.createNewFile()) {
                System.out.println("Could not create file");
            }
            fh = new FileHandler(fileName, true);
        } catch (IOException e){
            logger.warning("Could not open file");
            logger.severe(e.getMessage());
        }

        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
     * To activate log activities, debug must be true.
     * If setDebug is not called, no logging is carried out.
     * @param value The value to set debug to
     */
    public static void setDebug(boolean value){
        debug = value;
    }
}
