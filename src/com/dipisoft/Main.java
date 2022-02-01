package com.dipisoft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Socket socket = null;

        socket = new Socket("127.0.0.1", 8080);

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        try {
            while (true) {
                String response = (String) ois.readObject();
                System.out.println("<Server> " + response);
                if (!response.equals("goodbye")) {
                    System.out.print("> ");
                    String text = sc.nextLine();
                    oos.writeObject(text);
                } else break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) socket.close();
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            System.out.println("\nConexión cerrada.");
        }

        sc.close();

//        try {
//            System.out.println("Historial de chat:\n" + ois.readObject());
//
////            String text = "";
////            while (!text.equals("bye")) {
////                System.out.print("> ");
////                text = sc.nextLine();
////                oos.writeObject(text);
////            }
//            String response = (String)ois.readObject();
//            while (!response.equals("goodbye")) {
//                System.out.print("> ");
//                oos.writeObject(sc.nextLine());
//                response = (String)ois.readObject();
//            }

    }
}
