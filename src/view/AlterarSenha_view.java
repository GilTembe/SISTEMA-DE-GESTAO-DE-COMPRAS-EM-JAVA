package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;

import controller.Cadastro_controller;
import model.Cadastro;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class AlterarSenha_view {

	private JFrame frame;
	private Cadastro usuarioLogado;
	private JFrame loginFrame;
	private JTextField textsenhaantiga;
	private JPasswordField textnovaSenha;
	private JPasswordField textconfirmarSenha;
	private JTextField textusername;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public AlterarSenha_view(Cadastro usuarioLogado, JFrame loginFrame) {
		initialize();
		this.usuarioLogado = usuarioLogado;
		this.loginFrame = loginFrame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 508, 381);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 11, 495, 332);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ALTERAR SENHA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Poppins Black", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(160, 207, 207));
		lblNewLabel.setBounds(135, 0, 225, 51);
		panel.add(lblNewLabel);

		JLabel lblNovaSenha = new JLabel("NOVA SENHA");
		lblNovaSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNovaSenha.setBounds(219, 157, 93, 14);
		panel.add(lblNovaSenha);

		JLabel lblConfirmaSenha = new JLabel("CONFIRMA SENHA");
		lblConfirmaSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblConfirmaSenha.setBounds(196, 215, 116, 14);
		panel.add(lblConfirmaSenha);

		JButton btnsalvar = new JButton("SALVAR");
		btnsalvar.setBackground(new Color(160, 207, 207));
		btnsalvar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnsalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!validarCampos()) {
					return;
				}

				String antiga = textsenhaantiga.getText();
				String nova = textnovaSenha.getText();
				String confirmar = textconfirmarSenha.getText();
				String username = textusername.getText();

				if (!nova.equals(confirmar)) {
					JOptionPane.showMessageDialog(null, "AS SENHAS NAO SAO IGUAIS");
					return;
				}

				if (nova.equals(usuarioLogado.getSenha())) {
					JOptionPane.showMessageDialog(null, "A SENHA NOVA NAO PODE SER IDENTICA A ANTIGA");
					return;
				}

				if (!antiga.equals(usuarioLogado.getSenha())) {
					JOptionPane.showMessageDialog(null, "SENHA ANTIGA INCORRETA");
					return;
				}

				if (!username.equals(usuarioLogado.getUsername())) {
					JOptionPane.showMessageDialog(null, "USERNAME INVALIDO");
					return;
				}

				try {
					Cadastro_controller controller = new Cadastro_controller();
					controller.atualizarSenha(usuarioLogado.getIdusuarios(), nova);
					JOptionPane.showMessageDialog(null, "SENHA ALTERADA COM SUCESSO");

					frame.dispose();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
				}
				Login_view login = new Login_view();
				login.setVisible(true);

			}
		});
		btnsalvar.setBounds(185, 283, 152, 23);
		panel.add(btnsalvar);

		textsenhaantiga = new JTextField();
		textsenhaantiga.setColumns(10);
		textsenhaantiga.setBounds(135, 126, 247, 20);
		panel.add(textsenhaantiga);

		JLabel lblSenhaAntiga = new JLabel("SENHA ANTIGA");
		lblSenhaAntiga.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSenhaAntiga.setBounds(205, 101, 107, 14);
		panel.add(lblSenhaAntiga);

		textnovaSenha = new JPasswordField();
		textnovaSenha.setBounds(135, 183, 247, 20);
		panel.add(textnovaSenha);

		textconfirmarSenha = new JPasswordField();
		textconfirmarSenha.setBounds(135, 240, 247, 20);
		panel.add(textconfirmarSenha);

		textusername = new JTextField();
		textusername.setColumns(10);
		textusername.setBounds(135, 70, 247, 20);
		panel.add(textusername);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(216, 45, 76, 14);
		panel.add(lblUsername);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	
	private boolean validarCampos() {
		String username = textusername.getText();
		String senhaAntiga = textsenhaantiga.getText();
		String novaSenha = String.valueOf(textnovaSenha.getPassword());
		String confirmarSenha = String.valueOf(textconfirmarSenha.getPassword());

		if (username.isEmpty() || senhaAntiga.isEmpty() || novaSenha.isEmpty() || confirmarSenha.isEmpty()
				|| username.isBlank() || senhaAntiga.isBlank() || novaSenha.isBlank() || confirmarSenha.isBlank()) {
			JOptionPane.showMessageDialog(null, "UM DOS CAMPOS NAO FOI PREENCHIDO");
			return false;
		}
		return true;
	}
}
