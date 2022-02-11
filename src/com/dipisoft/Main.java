package com.dipisoft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Socket socket = null;

        socket = new Socket("127.0.0.1", 8080);

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            System.out.print("Username> ");
            oos.writeObject(sc.nextLine());

            System.out.println((String)ois.readObject());

            String chatHistory = (String)ois.readObject();
            if (chatHistory.isEmpty()) chatHistory = "<Chat vacío>";
            System.out.println(chatHistory);

            int messagesCount = chatHistory.length() - chatHistory.replace("[", "").length();

            while (true) {
                System.out.print("> ");
                String clientResponse = sc.nextLine();
                oos.writeObject(clientResponse);
                if (clientResponse.startsWith("message:")) messagesCount++;

                String svResponse = (String) ois.readObject();
                System.out.println("<Server> " + svResponse);
                if (svResponse.equals("goodbye")) break;

                oos.writeObject(messagesCount);

                String newMessages = (String) ois.readObject();

                if (!newMessages.isEmpty()) {
                    System.out.println(newMessages);
                    messagesCount += newMessages.length() - newMessages.replace("[", "").length();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) socket.close();
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            System.out.println(">> Conexión finalizada.");
        }
        sc.close();
    }
}
