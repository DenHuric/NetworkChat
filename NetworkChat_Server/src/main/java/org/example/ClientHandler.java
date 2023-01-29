package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private static Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Socket clientSocket = null;
    private static int clients_count = 0;
    Logger logger = Logger.GetLoggerInstance();
    private String userName;

    public ClientHandler(Socket socket, Server server) {
        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream(), true);
            this.inMessage = new Scanner(socket.getInputStream());
            userName = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            userName = inMessage.nextLine();
            server.sendMessageToAllClients("New user " + userName + " connected");
            server.sendMessageToAllClients("Users online: " + clients_count);

            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if ("/exit".equals(clientMessage)) {
                        String msg = "User " + userName + " leave the chat";
                        server.sendMessageToAllClients(msg);
                        logger.WriteEventToLog(msg);
                        break;
                    }
                    server.sendMessageToAllClients(userName + ": " + clientMessage);
                    logger.WriteEventToLog(userName + ": " + clientMessage);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);
        clients_count--;
        server.sendMessageToAllClients("Users online: " + clients_count);
    }
}