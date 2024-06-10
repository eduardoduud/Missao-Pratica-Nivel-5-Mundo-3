package cadastroserver;

import java.io.*;
import java.net.*;
import controller.ProdutoJpaController;
import controller.UsuariosJpaController;
import model.Usuarios;
import model.Produto;

public class CadastroServer extends Thread {
    private ProdutoJpaController ctrl;
    private UsuariosJpaController ctrlUsu;
    private Socket s1;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public CadastroServer(ProdutoJpaController ctrl, UsuariosJpaController ctrlUsu, Socket s1) {
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
        this.s1 = s1;
        try {
            out = new ObjectOutputStream(s1.getOutputStream());
            in = new ObjectInputStream(s1.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            Usuarios usuario = ctrlUsu.findUsuario(login, senha);
            if (usuario == null) {
                System.out.println("Usuário não encontrado. Conexão encerrada.");
                s1.close();
                return;
            }

            while (true) {
                String comando = (String) in.readObject();
                
                if (comando.equals("L")) {
                    out.writeObject(ctrl.findProdutoEntities());
                } else {
                    System.out.println("Comando desconhecido. Conexão encerrada.");
                    s1.close();
                    return;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
