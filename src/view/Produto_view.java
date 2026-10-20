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

import controller.Fornecedor_controller;
import controller.Produto_controller;
import model.Cadastro;
import model.Fornecedor;
import model.Produto;

import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import de.wannawork.jcalendar.JCalendarCellEditor;
import de.wannawork.jcalendar.JCalendarComboBox;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;

public class Produto_view {

	private JFrame frame;
	private JTextField textnomeProduto;
	private JTextField textprecoUnitario;
	private JLabel lblusuario;
	private Cadastro usuario;
	private Cadastro usuariLogado;
	private Cadastro usuarioLogado;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JButton tglbtnremover;
	private JButton tglbtnlistar;
	private JButton tglbtneditar;
	private JButton tglbtnenviar;
	private JTextField textpesquisa;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Produto_view(Cadastro usuarioLogado) {
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
		panel.setBounds(36, 121, 391, 403);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textnomeProduto = new JTextField();
		textnomeProduto.setBounds(40, 68, 279, 20);
		panel.add(textnomeProduto);
		textnomeProduto.setColumns(10);

		textprecoUnitario = new JTextField();
		textprecoUnitario.setColumns(10);
		textprecoUnitario.setBounds(40, 131, 279, 20);
		panel.add(textprecoUnitario);

		JLabel lblNewLabel = new JLabel("NOME DO PRODUTO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(38, 43, 127, 14);
		panel.add(lblNewLabel);

		JLabel lblPrecoUnitario = new JLabel("PRECO UNITARIO");
		lblPrecoUnitario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoUnitario.setBounds(40, 106, 115, 14);
		panel.add(lblPrecoUnitario);

		JLabel lblDataConfecao = new JLabel("DATA CONFECAO");
		lblDataConfecao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataConfecao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataConfecao.setBounds(40, 220, 115, 14);
		panel.add(lblDataConfecao);

		JLabel lblDataValidade = new JLabel("DATA VALIDADE");
		lblDataValidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataValidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataValidade.setBounds(40, 162, 104, 14);
		panel.add(lblDataValidade);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(27, 269, 279, 123);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JCalendarComboBox calendarComboBoxconfecao = new JCalendarComboBox();
		calendarComboBoxconfecao.setBounds(40, 187, 245, 20);
		panel.add(calendarComboBoxconfecao);

		JCalendarComboBox calendarComboBoxvalidade = new JCalendarComboBox();
		calendarComboBoxvalidade.setBounds(40, 245, 245, 20);
		panel.add(calendarComboBoxvalidade);

		tglbtnenviar = new JButton("ENVIAR");
		tglbtnenviar.setBackground(new Color(64, 128, 128));
		tglbtnenviar.setBounds(10, 11, 122, 38);
		panel_1.add(tglbtnenviar);
		tglbtnenviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nomeproduto = textnomeProduto.getText();

				double precoUnitario;
				try {
					precoUnitario = Double.parseDouble(textprecoUnitario.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "PARA O PRECO UNITARIO DIGITA APENAS NUMEROS");
					return;
				}

				LocalDate dataValidade = calendarComboBoxvalidade.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				LocalDate dataConfeccao = calendarComboBoxconfecao.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();

				// validacao do ano
				int anoConfeccao = dataConfeccao.getYear();
				int anoValidade = dataValidade.getYear();
				int anoAtual = LocalDate.now().getYear();
				if (anoValidade > anoAtual || anoConfeccao < anoValidade) {
					JOptionPane.showMessageDialog(null, "DATAS INVALIDAS");
					return;
				}

				try {
					Produto_controller controller = new Produto_controller();

					controller.adicionarProduto(nomeproduto, precoUnitario, dataValidade, dataConfeccao,
							usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "PRODUTO ADICIONADO COM SUCESSO");
					listar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO ADICIONAR" + ex.getMessage());

				}

			}
		});

		tglbtneditar = new JButton("EDITAR");
		tglbtneditar.setBackground(new Color(64, 128, 128));
		tglbtneditar.setBounds(142, 11, 122, 38);
		panel_1.add(tglbtneditar);
		tglbtneditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "SELECIONA UM PRODUTO NA TABELA");
					return;
				}
				int idProduto = (int) table.getValueAt(linhaSeleccionada, 0);
				String nomeproduto = textnomeProduto.getText();

				double precoUnitario;
				try {
					precoUnitario = Double.parseDouble(textprecoUnitario.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "PARA O PRECO UNITARIO DIGITA APENAS NUMEROS");
					return;
				}

				LocalDate dataValidade = calendarComboBoxvalidade.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				LocalDate dataConfeccao = calendarComboBoxconfecao.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				try {
					Produto_controller controller = new Produto_controller();
					controller.actualizarProduto(idProduto, nomeproduto, precoUnitario, dataValidade, dataConfeccao,
							usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "PRODUTO EDITADO COM SUCESSO");
					listar();
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
		tglbtnlistar.setBounds(142, 60, 122, 40);
		panel_1.add(tglbtnlistar);

		tglbtnremover = new JButton("REMOVER");
		tglbtnremover.setBackground(new Color(64, 128, 128));
		tglbtnremover.setBounds(10, 60, 122, 40);
		panel_1.add(tglbtnremover);
		tglbtnremover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM PRODUTO NA TABELA");
					return;
				}
				try {
					int idFornecedor = (int) table.getValueAt(linhaSeleccionada, 0);
					Produto_controller controller = new Produto_controller();
					int idProduto = (int) table.getValueAt(linhaSeleccionada, 0);
					controller.remover(idProduto, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "PRODUTO REMOVIDO COM SUCESSO");
					listar();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO REMOVER" + ex.getMessage());
				}
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(575, 144, 679, 388);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},

				new String[] { "ID", "NOME DO PRODUTO", "PRECO UNITARIO", "DATA VALIDADE", "DATA CONFECAO" }));
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = table.getSelectedRow();
				textnomeProduto.setText(table.getValueAt(linha, 1).toString());
				textprecoUnitario.setText(table.getValueAt(linha, 2).toString());
				tglbtnenviar.setEnabled(false);
			}
		});

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
		btntela.setBounds(69, 549, 128, 39);
		frame.getContentPane().add(btntela);

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(36, 48, 428, 25);
		frame.getContentPane().add(lblusuario);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(778, 86, 271, 31);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("PESQUISA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(1067, 94, 112, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblGestaoDeProdutos = new JLabel("GESTAO DE PRODUTOS");
		lblGestaoDeProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeProdutos.setForeground(new Color(120, 188, 188));
		lblGestaoDeProdutos.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		lblGestaoDeProdutos.setBounds(514, 44, 180, 28);
		frame.getContentPane().add(lblGestaoDeProdutos);

		JButton btnNewButton = new JButton("LIMPAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.setBounds(207, 549, 112, 39);
		frame.getContentPane().add(btnNewButton);
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

	public void limpar() {
		textnomeProduto.setText("");
		textprecoUnitario.setText("");
		tglbtnenviar.setEnabled(true);
	}

	// metodo para listar
	public void listar() {
		DefaultTableModel listarNaTabela = (DefaultTableModel) table.getModel();
		listarNaTabela.setRowCount(0);
		Produto_controller controller = new Produto_controller();
		try {
			ArrayList<Produto> listaDeProdutos = controller.listaDeProdutos();
			for (Produto produto : listaDeProdutos) {
				int idProduto = produto.getIdproduto();
				String nomeProduto = produto.getProduto();
				double precoUnitario = produto.getPrecoUnitario();
				LocalDate dataValidade = produto.getDataValidade();
				LocalDate dataConfecao = produto.getDataConfecao();

				listarNaTabela
						.addRow(new Object[] { idProduto, nomeProduto, precoUnitario, dataConfecao, dataValidade });
				limpar();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
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

	private void filtrar() {
		String texto = textpesquisa.getText();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			// busca nas colunas
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 2, 3, 4));
		}
	}
}
