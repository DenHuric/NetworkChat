package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final String USER_HOME_PATH = System.getProperty("user.home");
    private static final File LOG_DIR = new File(USER_HOME_PATH, "/NetworkChat/ClientLogs");
    private static final File LOG_FILE = new File(LOG_DIR, "file.log");
    private static Logger logger;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final Date date = new Date();


    private Logger() {

        if (LOG_DIR.mkdirs()) {
            System.out.println("**Log directory is successfully created**\n" +
                    "Directory path -> " + LOG_DIR.getAbsolutePath());
        } else {
            System.out.println("**Log directory already created**\n" +
                    "Directory path -> " + LOG_DIR.getAbsolutePath());
        }
        try {
            if (LOG_FILE.createNewFile()) {
                System.out.println("**Log file is successfully created**\n" +
                        "Log path -> " + LOG_FILE.getAbsolutePath());
            } else {
                System.out.println("**Log file already created**\n" +
                        "Log path -> " + LOG_FILE.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Logger GetLoggerInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public boolean WriteEventToLog(String event) {
        StringBuilder sb = new StringBuilder(event);
        sb.insert(0, formatter.format(date) + " ");
        sb.insert(sb.length(), "\n");
        try (FileOutputStream fos = new FileOutputStream(LOG_FILE, true)) {
            byte[] bytes = sb.toString().getBytes();
            fos.write(bytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}