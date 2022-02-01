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

        System.out.print("Introduzca su nombre de usuario: ");
        oos.writeObject(sc.nextLine());

        try {
            System.out.println("Historial de chat:\n" + ois.readObject());

            String text = "";
            while (!text.equals("bye")) {
                System.out.print("\n> ");
                text = sc.nextLine();
                oos.writeObject(text);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) socket.close();
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            System.out.println("Conexión cerrada.");
        }

        sc.close();
    }
}
