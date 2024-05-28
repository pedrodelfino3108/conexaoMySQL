package conexãoMySQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen extends JFrame implements ActionListener {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton resetPasswordButton;

	public LoginScreen() {
		setTitle("Login");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Centralizar a tela

		JPanel panel = new JPanel(new GridLayout(3, 2));
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Login");
		resetPasswordButton = new JButton("Resetar Senha");

		panel.add(new JLabel("Usuário:"));
		panel.add(usernameField);
		panel.add(new JLabel("Senha:"));
		panel.add(passwordField);
		panel.add(loginButton);
		panel.add(resetPasswordButton);

		loginButton.addActionListener(this);
		resetPasswordButton.addActionListener(this);

		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginScreen();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());
			if (login(username, password)) {
				JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

				dispose();
				new MainMenuScreen().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == resetPasswordButton) {
			String username = usernameField.getText();
			resetPassword(username);
		}
	}

	private boolean login(String username, String password) {
		String url = "jdbc:mysql://localhost:3306/seu_banco_de_dados";
		String user = "root";
		String pass = "Pedro129805@";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user, pass);
			String query = "SELECT * FROM usuarios WHERE username=? AND password=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private void resetPassword(String username) {
		String newPassword = JOptionPane.showInputDialog("Digite a nova senha:");
		if (newPassword != null && !newPassword.isEmpty()) {
			String url = "jdbc:mysql://localhost:3306/meu_banco_de_dados";
			String user = "root";
			String pass = "Pedro129805@";

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url, user, pass);
				String query = "UPDATE usuarios SET password=? WHERE username=?";
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setString(1, newPassword);
				statement.setString(2, username);
				int rowsUpdated = statement.executeUpdate();
				if (rowsUpdated > 0) {
					JOptionPane.showMessageDialog(this, "Senha resetada com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException | ClassNotFoundException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao resetar a senha!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Nova senha não pode ser vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class MainMenuScreen extends JFrame {
	public MainMenuScreen() {
		setTitle("Menu Principal");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		JMenu menuOpcoes = new JMenu("Opções");
		JMenuItem opcao1 = new JMenuItem("Opção 1");
		JMenuItem opcao2 = new JMenuItem("Opção 2");
		JMenuItem opcao3 = new JMenuItem("Opção 3");
		JMenuItem opcao4 = new JMenuItem("Opção 4");
		JMenuItem opcao5 = new JMenuItem("Opção 5");
		menuOpcoes.add(opcao1);
		menuOpcoes.add(opcao2);
		menuOpcoes.add(opcao3);
		menuOpcoes.add(opcao4);
		menuOpcoes.add(opcao5);
		menuBar.add(menuOpcoes);

		JButton botaoSair = new JButton("Sair do Sistema");
		botaoSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel panel = new JPanel();
		panel.add(botaoSair);

		setJMenuBar(menuBar);
		add(panel);
	}
}
