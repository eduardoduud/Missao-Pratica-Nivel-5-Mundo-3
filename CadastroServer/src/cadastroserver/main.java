package cadastroserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import controller.ProdutoJpaController;
import controller.UsuariosJpaController;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Server");

        ProdutoJpaController ctrl = new ProdutoJpaController(emf.createEntityManager());
        UsuariosJpaController ctrlUsu = new UsuariosJpaController(emf.createEntityManager());

        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            System.out.println("Servidor on");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                CadastroThread thread = new CadastroThread(ctrl, ctrlUsu, clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            emf.close();
        }
    }
}
