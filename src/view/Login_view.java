package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import controller.Cadastro_controller;
import model.Cadastro;
import controller.Log_controller;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class Login_view {

	private JFrame frame;
	private JTextField textusername;
	private JPasswordField passwordField;
	private Cadastro usuarioLogado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_view window = new Login_view();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public Login_view() {
		initialize();
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 2000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(567, 148, 361, 512);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("LOG-IN");
		lblNewLabel.setForeground(new Color(160, 207, 207));
		lblNewLabel.setFont(new Font("THE BOLD FONT (FREE VERSION)", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(80, 55, 201, 71);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(10, 148, 341, 220);
		panel.add(panel_1);
		panel_1.setLayout(null);

		textusername = new JTextField();
		textusername.setBackground(new Color(255, 255, 255));
		textusername.setBounds(35, 54, 277, 34);
		panel_1.add(textusername);
		textusername.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("USERNAME");
		lblNewLabel_1.setFont(new Font("Segoe UI Semilight", Font.BOLD, 13));
		lblNewLabel_1.setBounds(35, 32, 86, 14);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("SENHA");
		lblNewLabel_2.setFont(new Font("Segoe UI Semilight", Font.BOLD, 13));
		lblNewLabel_2.setBounds(35, 133, 106, 14);
		panel_1.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(35, 162, 277, 34);
		panel_1.add(passwordField);

		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String usarname = textusername.getText();
					String senhaDigitada = new String(passwordField.getPassword());
					Cadastro usuario = Cadastro_controller.autenticar(senhaDigitada, usarname);

					if (usuario != null) {
						char primeiraLetra = usuario.getNome().charAt(0);
						String senhaPadrao = primeiraLetra + usuario.getApelido() + "321";

						if (senhaDigitada.equals(senhaPadrao)) {
							AlterarSenha_view trocaSenha = new AlterarSenha_view(usuario, frame);
							trocaSenha.setVisible(true);

						} else {
							Log_controller log = new Log_controller();
							log.registarLog(usuario.getNome(), usuario.getperfil(), "LOGIN");

							Programa tela = new Programa(usuario);
							tela.setVisible(true);
							frame.dispose();
						}

					} else {
						JOptionPane.showMessageDialog(null, "DADOS INVALIDOS");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnEntrar.setForeground(new Color(0, 0, 0));
		btnEntrar.setBackground(new Color(160, 207, 207));
		btnEntrar.setBounds(47, 401, 258, 22);
		panel.add(btnEntrar);
	}
}
