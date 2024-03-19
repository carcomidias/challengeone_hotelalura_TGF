package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Modelo.Usuarios;
import Views.Login;
import Views.MenuUsuario;

public class UsuariosController implements ActionListener {

    private Login loginView;

    public UsuariosController(Login loginView) {
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nome = loginView.getNome();
        String senha = loginView.getSenha();

        if (Usuarios.validarUsuario(nome, senha)) {
            MenuUsuario menuUsuario = new MenuUsuario();
            menuUsuario.setVisible(true);
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(loginView, "Usuário ou senha inválidos", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}