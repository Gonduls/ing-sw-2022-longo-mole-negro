package it.polimi.ingsw;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public final Logger logger;
    private FileHandler fh;
    private static boolean debug;

    public Log(String fileName) throws SecurityException {
        File f = new File(fileName);
        logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);

        if(!debug)
            return;

        try{
            if(!f.exists()) {
                f.createNewFile();
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

    public static void setDebug(boolean value){
        debug = value;
    }
}
