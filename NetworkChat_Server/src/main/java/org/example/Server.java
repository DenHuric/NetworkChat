package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    Settings settings = new Settings("src/main/resources/Settings.xml");
    private String serverName = settings.getServerName();
    private int serverPort = settings.getServerPort();
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private Logger logger = Logger.GetLoggerInstance();

    public Server() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server successfully started.\n" +
                    "hostname: " + serverName + "\n" +
                    "Port: " + serverPort);
            logger.WriteEventToLog("Server successfully started.\n" +
                    "hostname: " + serverName + "\n" +
                    "Port: " + serverPort);
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.WriteEventToLog(ex.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Server is stopped");
                logger.WriteEventToLog("Server is stopped");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
        System.out.println(msg);
        //logger.WriteEventToLog(msg);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}