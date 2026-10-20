package view;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Caixa_controller;
import controller.Cliente_controller;
import model.Cadastro;
import model.Caixa;
import model.Cliente;

import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;
import java.awt.Font;

public class Cliente_view {

	private JFrame frame;
	private JTextField textnomeCliente;
	private JTextField textapelidoCliente;
	private JComboBox<Caixa> comboCaixa;
	private Cadastro usuarioLogado;
	private JLabel lblusuario;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JButton tglbtnremover;
	private JButton tglbtnlistar;
	private JButton tglbtneditar;
	private JButton tglbtnenviar;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JTextField textpesquisa;
	private JLabel lblNewLabel_1;
	private JLabel lblGestaoDeClientes;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Cliente_view(Cadastro usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		initialize();
		carregarCaixas();
		verificarPermissoes();
		if (usuarioLogado != null) {
			lblusuario.setText("UTILIZADOR: " + usuarioLogado.getNome() + " " + usuarioLogado.getApelido()
					+ "   PERFIL: " + usuarioLogado.getperfil());
		}
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1278, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(51, 144, 492, 495);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textnomeCliente = new JTextField();
		textnomeCliente.setColumns(10);
		textnomeCliente.setBounds(45, 94, 279, 20);
		panel.add(textnomeCliente);

		textapelidoCliente = new JTextField();
		textapelidoCliente.setColumns(10);
		textapelidoCliente.setBounds(45, 150, 279, 20);
		panel.add(textapelidoCliente);

		JLabel lblPrecoUnitario = new JLabel("NOME CLIENTE");
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoUnitario.setBounds(57, 69, 94, 14);
		panel.add(lblPrecoUnitario);

		JLabel lblDataConfecao = new JLabel("APELIDO CLIENTE");
		lblDataConfecao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataConfecao.setBounds(45, 125, 117, 14);
		panel.add(lblDataConfecao);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(45, 245, 285, 116);
		panel.add(panel_1);
		panel_1.setLayout(null);

		tglbtnenviar = new JButton("ENVIAR");
		tglbtnenviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validarCampos()) {
					return;
				}
				String nomeCliente = textnomeCliente.getText();
				String apelidoCliente = textapelidoCliente.getText();
				Caixa caixa = (Caixa) comboCaixa.getSelectedItem();

				try {
					Cliente_controller controller = new Cliente_controller();
					controller.adicionarCliente(nomeCliente, apelidoCliente, caixa.getIdCaixa(),
							usuarioLogado.getNome(), usuarioLogado.getperfil());

					JOptionPane.showMessageDialog(null, "CLIENTE ADICIONADO COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO ADICIONAR: " + ex.getMessage());
				}
			}
		});
		tglbtnenviar.setBackground(new Color(64, 128, 128));
		tglbtnenviar.setBounds(10, 11, 122, 38);
		panel_1.add(tglbtnenviar);

		tglbtneditar = new JButton("EDITAR");
		tglbtneditar.setBackground(new Color(64, 128, 128));
		tglbtneditar.setBounds(142, 11, 122, 38);
		panel_1.add(tglbtneditar);
		tglbtneditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM CLIENTE NA TABELA");
					return;
				}

				int idCliente = (int) table.getValueAt(linhaSeleccionada, 0);
				String nomeCliente = textnomeCliente.getText();
				String apelidoCliente = textapelidoCliente.getText();
				Caixa caixa = (Caixa) comboCaixa.getSelectedItem();

				try {
					Cliente_controller controller = new Cliente_controller();
					controller.actualizarCliente(idCliente, nomeCliente, apelidoCliente, caixa.getIdCaixa(),
							usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "CLIENTE EDITADO COM SUCESSO");
					listar();
					limpar();
					tglbtnlistar.doClick();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO EDITAR: " + ex.getMessage());
				}
			}
		});

		tglbtnlistar = new JButton("LISTAR");
		tglbtnlistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		tglbtnlistar.setBackground(new Color(64, 128, 128));
		tglbtnlistar.setBounds(142, 60, 122, 35);
		panel_1.add(tglbtnlistar);

		tglbtnremover = new JButton("REMOVER");
		tglbtnremover.setBackground(new Color(64, 128, 128));
		tglbtnremover.setBounds(10, 60, 122, 35);
		panel_1.add(tglbtnremover);

		JButton btntela = new JButton("TELA PRINCIPAL");
		btntela.setBackground(new Color(64, 128, 128));
		btntela.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Programa programa = new Programa(usuarioLogado);
				programa.setVisible(true);
				frame.dispose();
			}

		});
		btntela.setBounds(45, 383, 132, 34);
		panel.add(btntela);

		comboCaixa = new JComboBox();
		comboCaixa.setBackground(new Color(226, 241, 241));
		comboCaixa.setBounds(45, 204, 127, 22);

		panel.add(comboCaixa);

		JLabel lblNewLabel = new JLabel("NOME CAIXA");
		lblNewLabel.setBounds(193, 208, 127, 14);
		panel.add(lblNewLabel);

		lblGestaoDeClientes = new JLabel("GESTAO DE CLIENTES");
		lblGestaoDeClientes.setBounds(45, 11, 179, 28);
		panel.add(lblGestaoDeClientes);
		lblGestaoDeClientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeClientes.setForeground(new Color(120, 188, 188));
		lblGestaoDeClientes.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));

		btnNewButton = new JButton("LIMPAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.setBounds(209, 383, 115, 34);
		panel.add(btnNewButton);

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(51, 61, 430, 26);
		frame.getContentPane().add(lblusuario);

		panel_2 = new JPanel();
		panel_2.setBounds(672, 237, 516, 322);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "NOME", "APELIDO", "NOME CAIXA" }));
		scrollPane.setViewportView(table);
		// PESQUISA
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(735, 154, 235, 33);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);

		lblNewLabel_1 = new JLabel("PESQUISA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(989, 163, 112, 14);
		frame.getContentPane().add(lblNewLabel_1);
		textpesquisa.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				filtrar();
			}

			public void removeUpdate(DocumentEvent e) {
				filtrar();
			}

			public void changedUpdate(DocumentEvent e) {
				filtrar();
			}
		});
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = table.getSelectedRow();
				textnomeCliente.setText(table.getValueAt(linha, 1).toString());
				textapelidoCliente.setText(table.getValueAt(linha, 2).toString());
				tglbtnenviar.setEnabled(false);
			}
		});

		tglbtnremover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM CLIENTE NA TABELA");
					return;
				}
				try {
					int idCliente = (int) table.getValueAt(linhaSeleccionada, 0);
					Cliente_controller controller = new Cliente_controller();
					controller.remover(idCliente, usuarioLogado.getNome(), usuarioLogado.getperfil());
					;
					JOptionPane.showMessageDialog(null, "CLIENTE REMOVIDO COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO REMOVER" + ex.getMessage());
				}
			}
		});

	}

	public void verificarPermissoes() {
		if (usuarioLogado == null)
			return;

		switch (usuarioLogado.getperfil()) {

		case "OPERADOR":
			tglbtnremover.setEnabled(false);
			tglbtneditar.setEnabled(false);
			break;

		case "SUPER OPERADOR":
			break;

		case "AUDITOR":
			break;

		case "ADMINISTRADOR":

			break;
		}
	}

	private void carregarCaixas() {
		try {
			comboCaixa.removeAllItems();
			ArrayList<Caixa> lista = Caixa_controller.listaDeCaixas();
			for (Caixa caixa : lista) {
				comboCaixa.addItem(caixa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// validacao dos campos
	private boolean validarCampos() {
		String nomeCliente = textnomeCliente.getText();
		String apelidoCliente = textapelidoCliente.getText();
		if (nomeCliente.isEmpty() || apelidoCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "UM DOS CAMPOS NAO FOI PREENCHIDO");
			return false;
		}
		return true;
	}

	// metodo limpar
	public void limpar() {
		textnomeCliente.setText("");
		textapelidoCliente.setText("");
		tglbtnenviar.setEnabled(true);
	}

	// metodo listar
	public void listar() {
		DefaultTableModel listarNaTabela = (DefaultTableModel) table.getModel();
		listarNaTabela.setRowCount(0);
		Cliente_controller controller = new Cliente_controller();

		try {
			ArrayList<Cliente> listaDeClientes = controller.listaDeClientes();
			for (Cliente cliente : listaDeClientes) {
				listarNaTabela.addRow(new Object[] { cliente.getIdCliente(), cliente.getNomeCliente(),
						cliente.getApelidoCliente(), cliente.getCaixa().getNomeCaixa() });
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void filtrar() {
		String texto = textpesquisa.getText();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 2, 3, 4));
		}
	}
}
