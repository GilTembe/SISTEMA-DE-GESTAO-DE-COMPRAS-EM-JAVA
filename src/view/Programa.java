package view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.Cadastro;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Programa {

	private JFrame frame;
	private Cadastro usuarioLogado;
	private JButton btnGerirCaixa;
	private JLabel lblutilizador;
	private JFrame loginFrame;
	// private Cadastro usuariLogado;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Programa(Cadastro usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		initialize();
		acesso();
		if (usuarioLogado != null) {
			lblutilizador.setText("UTILIZADOR: " + usuarioLogado.getNome() + " " + usuarioLogado.getApelido()
					+ "   PERFIL: " + usuarioLogado.getperfil());
		}
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	// botoes
	private JButton tglbtGerirFoenecedores;
	private JButton tglbtnGerirProdutos;
	private JButton tglbtnGerirEnvio;
	private JButton tglbtnGerirCompras;
	private JButton tglbtnGerirClientes;
	private JButton tglbtnGerirContas;
	private JButton btnGerirLog;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton_1;

	private void initialize() {
		frame = new JFrame();
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1295, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 268, 700);
		panel.setBackground(new Color(109, 182, 182));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		tglbtGerirFoenecedores = new JButton("GERIR FORNECEDORS");
		tglbtGerirFoenecedores.setBackground(new Color(255, 255, 255));
		tglbtGerirFoenecedores.setBounds(41, 79, 187, 50);
		panel.add(tglbtGerirFoenecedores);
		tglbtGerirFoenecedores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fornecedor_view cadastrarFornecedor = new Fornecedor_view(usuarioLogado);
				cadastrarFornecedor.setVisible(true);
				frame.dispose();
			}

		});

		tglbtnGerirProdutos = new JButton("GERIR PRODUTOS");
		tglbtnGerirProdutos.setBackground(new Color(255, 255, 255));
		tglbtnGerirProdutos.setBounds(41, 140, 187, 50);
		panel.add(tglbtnGerirProdutos);
		tglbtnGerirProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Produto_view cadastrarProduto = new Produto_view(usuarioLogado);
				cadastrarProduto.setVisible(true);
				frame.dispose();
			}

		});

		tglbtnGerirEnvio = new JButton("GERIR ENVIO");
		tglbtnGerirEnvio.setBackground(new Color(255, 255, 255));
		tglbtnGerirEnvio.setBounds(41, 201, 187, 50);
		panel.add(tglbtnGerirEnvio);
		tglbtnGerirEnvio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Envio_view cadastrarEnvio = new Envio_view(usuarioLogado);
				cadastrarEnvio.setVisible(true);
				frame.dispose();
			}

		});

		tglbtnGerirCompras = new JButton("GERIR COMPRAS");
		tglbtnGerirCompras.setBackground(new Color(255, 255, 255));
		tglbtnGerirCompras.setBounds(41, 262, 187, 50);
		panel.add(tglbtnGerirCompras);
		tglbtnGerirCompras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Compra_view cadastrarProduto = new Compra_view(usuarioLogado);
				cadastrarProduto.setVisible(true);
				frame.dispose();
			}

		});

		tglbtnGerirClientes = new JButton("GERIR CLIENTES");
		tglbtnGerirClientes.setBackground(new Color(255, 255, 255));
		tglbtnGerirClientes.setBounds(41, 323, 187, 50);
		panel.add(tglbtnGerirClientes);
		tglbtnGerirClientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cliente_view cadastrarCliente = new Cliente_view(usuarioLogado);
				cadastrarCliente.setVisible(true);
				frame.dispose();
			}

		});

		tglbtnGerirContas = new JButton("GERIR CONTAS");
		tglbtnGerirContas.setBackground(new Color(255, 255, 255));
		tglbtnGerirContas.setBounds(41, 445, 187, 50);
		panel.add(tglbtnGerirContas);
		tglbtnGerirContas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cadastro_view cadastrarConta = new Cadastro_view(usuarioLogado);
				cadastrarConta.setVisible(true);
				frame.dispose();
			}

		});

		btnGerirLog = new JButton("GERIR LOG");
		btnGerirLog.setBackground(new Color(255, 255, 255));
		btnGerirLog.setBounds(41, 506, 187, 50);
		panel.add(btnGerirLog);

		btnGerirCaixa = new JButton("GERIR CAIXA");
		btnGerirCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Caixa_view caixa = new Caixa_view(usuarioLogado);
				caixa.setVisible(true);
				frame.dispose();

			}
		});
		btnGerirCaixa.setBackground(Color.WHITE);
		btnGerirCaixa.setBounds(41, 384, 187, 50);
		panel.add(btnGerirCaixa);

		JButton btnNewButton = new JButton("LOG OUT");
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login_view login = new Login_view();
				login.setVisible(true);
				frame.dispose();

			}
		});
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(41, 572, 187, 50);
		panel.add(btnNewButton);
		btnGerirLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Log_view log = new Log_view(usuarioLogado);
				log.setVisible(true);
				frame.dispose();
			}

		});

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(180, 0, 1151, 80);
		panel_1.setBackground(new Color(109, 182, 182));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		lblutilizador = new JLabel("New label");
		lblutilizador.setBounds(142, 21, 426, 35);
		panel_1.add(lblutilizador);

		btnNewButton_1 = new JButton("ALTERAR SENHA");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlterarSenha_view alterar = new AlterarSenha_view(usuarioLogado, frame);
				alterar.setVisible(true);

			}
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setBounds(794, 27, 188, 22);
		panel_1.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(469, 242, 782, 302);
		panel_2.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SISTEMA DE GESTAO DE COMPRA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 33));
		lblNewLabel.setBounds(78, 70, 635, 104);
		panel_2.add(lblNewLabel);

//		lblNewLabel_1 = new JLabel("");
//		lblNewLabel_1.setIcon(new ImageIcon(Programa.class.getResource("/img (1).png")));
//		lblNewLabel_1.setBounds(562, 176, 654, 392);
//		frame.getContentPane().add(lblNewLabel_1);

	}

	public void acesso() {
		if (usuarioLogado == null)
			return;

		String perfil = usuarioLogado.getperfil();

		switch (perfil) {
		case "OPERADOR":
			tglbtnGerirContas.setEnabled(false);
			btnGerirLog.setEnabled(false);
			break;

		case "SUPER OPERADOR":
			tglbtnGerirContas.setEnabled(false);
			btnGerirLog.setEnabled(false);
			break;

		case "AUDITOR":
			tglbtnGerirContas.setEnabled(false);
			break;

		case "ADMINISTRADOR":
			btnGerirLog.setEnabled(false);
			break;

		}

	}
}
