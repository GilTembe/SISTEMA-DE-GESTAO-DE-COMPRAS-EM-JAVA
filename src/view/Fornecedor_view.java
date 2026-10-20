package view;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComboBox;

import model.Cadastro;
import model.Caixa;
import model.Fornecedor;
import controller.Caixa_controller;
import controller.Cliente_controller;
import controller.Fornecedor_controller;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;

public class Fornecedor_view {

	private JFrame frame;
	private JTextField textnomeFornecedor;
	private JTextField textendereco;
	private JTextField texttelefone;
	private JTextField textemail;
	private Cadastro usuarioLogado;
	private JLabel lblusuario;
	private JButton tglbtnremover;
	private JButton tglbtnlistar;
	private JButton tglbtneditar;
	private JButton tglbtnenviar;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField textpesquisa;
	private TableRowSorter<DefaultTableModel> sorter;
	private JLabel lblNewLabel_1;
	private JLabel lblGestaoDeFornecedores;
	private JButton btnNewButton;

	/**
	 * Launch the application. //
	 */

	/**
	 * Create the application.
	 */
	public Fornecedor_view(Cadastro usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		initialize();
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
		panel.setBounds(51, 85, 414, 562);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textnomeFornecedor = new JTextField();
		textnomeFornecedor.setBounds(54, 93, 279, 20);
		panel.add(textnomeFornecedor);
		textnomeFornecedor.setColumns(10);

		textendereco = new JTextField();
		textendereco.setColumns(10);
		textendereco.setBounds(54, 261, 279, 20);
		panel.add(textendereco);

		texttelefone = new JTextField();
		texttelefone.setColumns(10);
		texttelefone.setBounds(54, 149, 279, 20);
		panel.add(texttelefone);

		textemail = new JTextField();
		textemail.setColumns(10);
		textemail.setBounds(54, 205, 279, 20);
		panel.add(textemail);

		JLabel lblNewLabel = new JLabel("NOME DO FORNECEDOR");
		lblNewLabel.setFont(new Font("Segoe UI Historic", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(54, 68, 169, 14);
		panel.add(lblNewLabel);

		JLabel lblPrecoUnitario = new JLabel("TELEFONE");
		lblPrecoUnitario.setFont(new Font("Segoe UI Historic", Font.BOLD, 11));
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoUnitario.setBounds(54, 124, 84, 14);
		panel.add(lblPrecoUnitario);

		JLabel lblDataConfecao = new JLabel("EMAIL");
		lblDataConfecao.setFont(new Font("Segoe UI Historic", Font.BOLD, 11));
		lblDataConfecao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataConfecao.setBounds(54, 180, 72, 14);
		panel.add(lblDataConfecao);

		JLabel lblDataValidade = new JLabel("ENDERECO");
		lblDataValidade.setFont(new Font("Segoe UI Historic", Font.BOLD, 11));
		lblDataValidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataValidade.setBounds(54, 236, 89, 14);
		panel.add(lblDataValidade);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(43, 311, 310, 123);
		panel.add(panel_1);
		panel_1.setLayout(null);

		tglbtnenviar = new JButton("ENVIAR");
		tglbtnenviar.setBackground(new Color(64, 128, 128));
		tglbtnenviar.setBounds(10, 11, 122, 38);
		panel_1.add(tglbtnenviar);
		tglbtnenviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!validarCampos()) {
						return;
					}
					String email = textemail.getText();
					String endereco = textendereco.getText();
					String nomeFornecedor = textnomeFornecedor.getText();
					String telefone = texttelefone.getText();
					Fornecedor_controller controller = new Fornecedor_controller();

					controller.adicionarFornecedor(nomeFornecedor, telefone, email, endereco, usuarioLogado.getNome(),
							usuarioLogado.getperfil());

					JOptionPane.showMessageDialog(null, "FORNECEDOR ADICIONADO COM SUCESSO");
					listar();
					limpar();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO ADICIONAR\n" + ex.getMessage());
				}

			}
		});

		tglbtneditar = new JButton("EDITAR");
		tglbtneditar.setBackground(new Color(64, 128, 128));
		tglbtneditar.setBounds(152, 11, 132, 38);
		panel_1.add(tglbtneditar);
		tglbtneditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "SELECIONA UM FORNECEDOR");
					return;
				}
				if (!validarCampos()) {
					return;
				}
				try {
					int idFornecedor = (int) table.getValueAt(linhaSeleccionada, 0);
					String nomeFornecedor = textnomeFornecedor.getText();
					String telemovel = texttelefone.getText();
					String email = textemail.getText();
					String endereco = textendereco.getText();

					Fornecedor_controller controller = new Fornecedor_controller();
					controller.actualizarFornecedor(idFornecedor, nomeFornecedor, telemovel, email, endereco,
							usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "FORNECEDOR ACTUALIZADO COM SUCESSO");
					listar();
					limpar();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO ACTUALIZAR: " + ex.getMessage());
				}
			}

		});

		tglbtnlistar = new JButton("LISTAR");
		tglbtnlistar.setBackground(new Color(64, 128, 128));
		tglbtnlistar.setBounds(152, 60, 132, 40);
		panel_1.add(tglbtnlistar);
		tglbtnlistar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		tglbtnremover = new JButton("REMOVER");
		tglbtnremover.setBackground(new Color(64, 128, 128));
		tglbtnremover.setBounds(10, 60, 122, 40);
		panel_1.add(tglbtnremover);

		JButton btntela = new JButton("TELA PRINCIPAL");
		btntela.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Programa programa = new Programa(usuarioLogado);
				programa.setVisible(true);
				frame.dispose();
			}

		});
		btntela.setBackground(new Color(64, 128, 128));
		btntela.setBounds(54, 455, 121, 37);
		panel.add(btntela);

		btnNewButton = new JButton("LIMPAR");
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton.setBounds(201, 455, 121, 37);
		panel.add(btnNewButton);
		tglbtnremover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM FORNECEDOR NA TABELA");
					return;
				}
				try {
					int idFornecedor = (int) table.getValueAt(linhaSeleccionada, 0);
					Fornecedor_controller controller = new Fornecedor_controller();
					controller.remover(idFornecedor, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "FORNECEDOR REMOVIDO COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO REMOVER" + ex.getMessage());
				}
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(501, 225, 753, 307);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		// tabela
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "NOME DO FORNECEDORE", "TELEFONE", "EMAIL", "ENDERECO" }));
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);

		lblusuario = new JLabel("New label");
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = table.getSelectedRow();
				textnomeFornecedor.setText(table.getValueAt(linha, 1).toString());
				texttelefone.setText(table.getValueAt(linha, 2).toString());
				textemail.setText(table.getValueAt(linha, 3).toString());
				textendereco.setText(table.getValueAt(linha, 4).toString());
				tglbtnenviar.setEnabled(false);
			}
		});
		lblusuario.setBounds(45, 39, 400, 24);
		frame.getContentPane().add(lblusuario);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(782, 138, 252, 31);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);

		lblNewLabel_1 = new JLabel("PESQUISA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(1055, 146, 112, 14);
		frame.getContentPane().add(lblNewLabel_1);

		lblGestaoDeFornecedores = new JLabel("GESTAO DE FORNECEDORES");
		lblGestaoDeFornecedores.setBounds(514, 57, 201, 28);
		frame.getContentPane().add(lblGestaoDeFornecedores);
		lblGestaoDeFornecedores.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeFornecedores.setForeground(new Color(120, 188, 188));
		lblGestaoDeFornecedores.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
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
	}

	public void listar() {

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		Fornecedor_controller controller = new Fornecedor_controller();
		try {
			ArrayList<Fornecedor> lista = controller.listaDeFornecedores();
			for (Fornecedor fornecedor : lista) {
				modelo.addRow(new Object[] { fornecedor.getIdFornecedor(), fornecedor.getNomeFornecedor(),
						fornecedor.getTelefone(), fornecedor.getEmail(), fornecedor.getEndereco() });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERRO AO LISTAR: " + e.getMessage());
		}
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

	// Fornecedor validar
	private boolean validarCampos() {
		String email = textemail.getText();
		String endereco = textendereco.getText();
		String nomeFornecedor = textnomeFornecedor.getText();
		String telefone = texttelefone.getText();

		if (nomeFornecedor.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || email.isEmpty()
				|| nomeFornecedor.isBlank() || telefone.isBlank() || endereco.isBlank() || email.isBlank()) {
			JOptionPane.showMessageDialog(null, "UM DOS CAMPOS NAO FOI PREENCHIDOS");
			return false;
		}
		return true;
	}

	public void limpar() {
		textnomeFornecedor.setText("");
		textemail.setText("");
		texttelefone.setText("");
		textendereco.setText("");
		tglbtnenviar.setEnabled(true);

	}

	private void filtrar() {
		String texto = textpesquisa.getText();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			// busca nas colunas ID (0) e NOME DO CAIXA (1)
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 3, 4));
		}
	}

}
