package cadastroclient;

import java.io.*;
import java.net.*;
import java.util.List;

public class CadastroClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.writeObject("op1");
            out.writeObject("op1");

            out.writeObject("L");

            List<String> produtos = (List<String>) in.readObject();

            System.out.println("Produtos:");
            for (String produto : produtos) {
                System.out.println(produto);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
