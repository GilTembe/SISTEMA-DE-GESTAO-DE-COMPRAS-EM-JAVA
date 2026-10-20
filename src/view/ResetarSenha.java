package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;

import controller.Cadastro_controller;
import model.Cadastro;
import model.Cliente;
import model.Produto;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResetarSenha {

	private JFrame frame;
	private JComboBox comboBoxperfil;
	private JComboBox comboBoxutilizadores;
	private ArrayList<Cadastro> listaDeLogin = new ArrayList<>();
	private ArrayList<Cadastro> listaFiltrada = new ArrayList<>();
	private String nome;
	private String apelido;
	private char primeiraLetra;
	private String senhaGerada;
	private String username;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public ResetarSenha() {
		initialize();
		carregarCadastro();

	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(221, 238, 238));
		frame.getContentPane().setBackground(new Color(221, 238, 238));
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(221, 238, 238));
		panel.setBounds(40, 52, 354, 156);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int opcao = comboBoxutilizadores.getSelectedIndex();

				Cadastro usuario = listaFiltrada.get(opcao);

				nome = usuario.getNome();
				apelido = usuario.getApelido();
				username = usuario.getUsername();

				primeiraLetra = nome.charAt(0);
				senhaGerada = primeiraLetra + apelido + "321";

				try {
					Cadastro_controller controller = new Cadastro_controller();
					controller.atualizarSenha(usuario.getIdusuarios(), senhaGerada);
					criarCredenciais();
					JOptionPane.showMessageDialog(null, "SENHA RESETADA COM SUCESSO!");
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "ERRO AO RESETAR A SENHA!!");
					frame.dispose();
				}
			}
		});
		btnNewButton.setBounds(138, 122, 88, 23);
		panel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("PERFIL");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(20, 31, 77, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("UTILIZADORES");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(20, 75, 101, 14);
		panel.add(lblNewLabel_1);

		comboBoxperfil = new JComboBox();
		comboBoxperfil.setModel(
				new DefaultComboBoxModel(new String[] { "OPERADOR", "SUPER OPERADOR", "AUDITOR", "ADMINISTRADOR" }));
		comboBoxperfil.setBounds(152, 27, 171, 22);

		comboBoxperfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarUtilizadores();
			}
		});
		panel.add(comboBoxperfil);

		comboBoxutilizadores = new JComboBox();
		comboBoxutilizadores.setBackground(new Color(255, 255, 255));
		comboBoxutilizadores.setBounds(152, 71, 171, 22);
		panel.add(comboBoxutilizadores);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void carregarCadastro() {
		listaDeLogin.clear();
		try {
			Cadastro_controller controller = new Cadastro_controller();
			listaDeLogin = controller.listaDeLogin();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		filtrarUtilizadores();
	}

	// hummmmmmmm
	private void filtrarUtilizadores() {
		comboBoxutilizadores.removeAllItems();
		listaFiltrada.clear();

		String perfilSelecionado = (String) comboBoxperfil.getSelectedItem();

		for (Cadastro users : listaDeLogin) {
			if (users.getperfil() != null && users.getperfil().equals(perfilSelecionado)) {
				listaFiltrada.add(users);
				comboBoxutilizadores.addItem(users.getNome());
			}
		}
	}

	private void criarCredenciais() {
		try {
			FileWriter writer = new FileWriter(nome + " " + "resetar senha" + ".txt");

			writer.write("Nome:" + nome + " " + apelido + "\n");
			writer.write("Perfil:" + comboBoxperfil.getSelectedItem() + "\n");
			writer.write("USERNAME:" + username + "\n");
			writer.write("Senha:" + senhaGerada + "\n");

			writer.close();

			JOptionPane.showMessageDialog(null, "SENHA GERADA COM SUCESSO!!");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "ERRO AO GERAR SENHA");
		}
		frame.dispose();
	}

}
