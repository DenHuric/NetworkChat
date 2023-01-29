package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Settings settings = new Settings("src/main/resources/Settings.xml");
    private String serverName = settings.getServerName();
    private int serverPort = settings.getServerPort();
    private Socket clientSocket;
    private String userName;
    private Scanner inMassage;
    private InputStreamReader inputStreamReader = null;
    private OutputStreamWriter outputStreamWriter = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private PrintWriter outMassage;

    public Client() {
        try {
            clientSocket = new Socket(serverName, serverPort);
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
            ex.getStackTrace();
        }

        bufferedReader = new BufferedReader(inputStreamReader);
        bufferedWriter = new BufferedWriter(outputStreamWriter);

        Logger logger = Logger.GetLoggerInstance();
        ClientInputThread inThread = new ClientInputThread(clientSocket);
        inThread.start();
        System.out.println("Server connection established");
        logger.WriteEventToLog("Server connection established");
        try {
            outMassage = new PrintWriter(clientSocket.getOutputStream(), true);
            inMassage = new Scanner(clientSocket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter user name.");
            userName = scanner.nextLine();
            logger.WriteEventToLog("User " + userName + " is connected");
            System.out.println(userName + ", welcome to chat!");
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("Enter a message or /exit to leave the chat.");
            while (true) {
                String outMsg = scanner.nextLine();
                bufferedWriter.write(outMsg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                logger.WriteEventToLog(userName + " : " + outMsg);
                System.out.println(bufferedReader.readLine());
                if ("/exit".equals(outMsg)) {
                    break;
                }
            }
            ClientInputThread.interrupted();
            bufferedReader.close();
            scanner.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
