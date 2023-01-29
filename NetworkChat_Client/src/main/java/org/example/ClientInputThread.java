package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInputThread extends Thread {
    private final Socket socket;

    public ClientInputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Logger logger = Logger.GetLoggerInstance();
            while (!isInterrupted()) {
                logger.WriteEventToLog(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
