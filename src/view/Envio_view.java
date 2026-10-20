package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Compra_controller;
import controller.Envio_controller;
import controller.Fornecedor_controller;
import controller.Produto_controller;

import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import de.wannawork.jcalendar.JCalendarComboBox;
import java.awt.Font;
import de.wannawork.jcalendar.DayLabel;
import de.wannawork.jcalendar.JMonthPanel;
import model.Cadastro;
import model.Cliente;
import model.Compra;
import model.Envio;
import model.Fornecedor;
import model.Produto;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;

public class Envio_view {

	private JFrame frame;
	private Cadastro usuarioLogado;
	private JLabel lblusuario;
	private JButton tglbtnremover;
	private JButton tglbtnlistar;
	private JButton tglbtneditar;
	private JButton tglbtnenvio;
	private JComboBox<Fornecedor> comboBoxFornecedor;
	private JComboBox<Produto> comboBoxProduto;
	private JTextField textpesquisa;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JLabel lblNewLabel_2;
	private JLabel lblGestaoDeEnvio;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Envio_view(Cadastro usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		initialize();
		verificarPermissoes();
		carregarFornecedor();
		carregarProduto();
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
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1278, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(42, 128, 431, 495);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblPrecoUnitario = new JLabel("DATA ENVIO");
		lblPrecoUnitario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoUnitario.setBounds(40, 49, 94, 14);
		panel.add(lblPrecoUnitario);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(40, 202, 300, 117);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JCalendarComboBox calendarComboBox = new JCalendarComboBox();
		calendarComboBox.setBounds(40, 74, 279, 20);
		panel.add(calendarComboBox);

		tglbtnenvio = new JButton("ENVIAR");
		tglbtnenvio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				LocalDate dataEnvio = calendarComboBox.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				Fornecedor fornecedorSelecionado = (Fornecedor) comboBoxFornecedor.getSelectedItem();
				Produto produtoSelecionado = (Produto) comboBoxProduto.getSelectedItem();
				String nomeUsuario = usuarioLogado.getNome();
				String perfil = usuarioLogado.getperfil();
				// validacao do ano
				int anoEnvio = dataEnvio.getYear();
				if ((anoEnvio > LocalDate.now().getYear())) {
					JOptionPane.showMessageDialog(null, " O ANO DE ENVIO NAO PODE SER MAIOR QUE 2026");
					return;
				}

				try {

					Envio_controller controller = new Envio_controller();
					controller.adicionarEnvio(dataEnvio, fornecedorSelecionado.getIdFornecedor(),
							produtoSelecionado.getIdproduto(), nomeUsuario, perfil);
					JOptionPane.showMessageDialog(null, "ENVIO ADICIONADO COM SUCESSO");
					listar();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		tglbtnenvio.setBackground(new Color(64, 128, 128));
		tglbtnenvio.setBounds(10, 19, 134, 38);
		panel_1.add(tglbtnenvio);

		tglbtneditar = new JButton("EDITAR");
		tglbtneditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM ENVIO NA TABELA");
					return;
				}
				int idEnvio = (int) table.getValueAt(linhaSeleccionada, 0);

				LocalDate novaDataEnvio = calendarComboBox.getDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				Fornecedor fornecedorSelecionado = (Fornecedor) comboBoxFornecedor.getSelectedItem();
				Produto produtoSelecionado = (Produto) comboBoxProduto.getSelectedItem();

				try {
					Envio_controller controller = new Envio_controller();
					controller.actualizarEnvio(idEnvio, novaDataEnvio, fornecedorSelecionado.getIdFornecedor(),
							produtoSelecionado.getIdproduto(), usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "ENVIO EDITADO COM SUCESSO");
					listar();
					limpar();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO EDITAR: " + ex.getMessage());
				}
			}
		});
		tglbtneditar.setBackground(new Color(64, 128, 128));
		tglbtneditar.setBounds(154, 19, 136, 38);
		panel_1.add(tglbtneditar);

		// aqui

		tglbtnlistar = new JButton("LISTAR");
		tglbtnlistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		tglbtnlistar.setBackground(new Color(64, 128, 128));
		tglbtnlistar.setBounds(154, 71, 136, 35);
		panel_1.add(tglbtnlistar);

		tglbtnremover = new JButton("REMOVER");
		tglbtnremover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UMA ENVIO NA TABELA");
					return;
				}
				try {
					int idEnvio = (int) table.getValueAt(linhaSeleccionada, 0);
					Envio_controller controller = new Envio_controller();
					controller.remover(idEnvio, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "ENVIO REMOVIDA COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO REMOVER" + ex.getMessage());
				}
			}
		});
		tglbtnremover.setBackground(new Color(64, 128, 128));
		tglbtnremover.setBounds(10, 71, 134, 35);
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
		btntela.setBounds(50, 385, 135, 33);
		panel.add(btntela);

		comboBoxFornecedor = new JComboBox();
		comboBoxFornecedor.setBounds(40, 121, 162, 22);
		panel.add(comboBoxFornecedor);

		comboBoxProduto = new JComboBox();
		comboBoxProduto.setBounds(40, 154, 162, 22);
		panel.add(comboBoxProduto);

		JLabel lblNewLabel = new JLabel("FORNECEDOR");
		lblNewLabel.setBounds(220, 125, 99, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("PRODUTO");
		lblNewLabel_1.setBounds(219, 158, 75, 14);
		panel.add(lblNewLabel_1);

		btnNewButton = new JButton("LIMPAR");
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton.setBounds(195, 385, 135, 33);
		panel.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(531, 128, 679, 427);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "DATA ENVIO", "FORNECEDOR", "PRODUTO" }));
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = table.getSelectedRow();
				// textquantidade.setText(table.getValueAt(linha, 2).toString());
				tglbtnenvio.setEnabled(false);
			}
		});

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(52, 55, 443, 32);
		frame.getContentPane().add(lblusuario);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(734, 85, 277, 32);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);

		lblNewLabel_2 = new JLabel("PESQUISA");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(1027, 94, 112, 14);
		frame.getContentPane().add(lblNewLabel_2);

		lblGestaoDeEnvio = new JLabel("GESTAO DE ENVIO");
		lblGestaoDeEnvio.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeEnvio.setForeground(new Color(120, 188, 188));
		lblGestaoDeEnvio.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		lblGestaoDeEnvio.setBounds(437, 66, 201, 28);
		frame.getContentPane().add(lblGestaoDeEnvio);
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

	private void carregarFornecedor() {
		try {
			comboBoxFornecedor.removeAllItems();
			ArrayList<Fornecedor> lista = Fornecedor_controller.listaDeFornecedores();
			for (Fornecedor fornecedor : lista) {
				comboBoxFornecedor.addItem(fornecedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void carregarProduto() {
		try {
			comboBoxProduto.removeAllItems();
			ArrayList<Produto> lista = Produto_controller.listaDeProdutos();
			for (Produto produto : lista) {
				comboBoxProduto.addItem(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void listar() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		try {
			ArrayList<Envio> listaDeCompras = Envio_controller.listaDeEnvios();
			for (Envio envio : listaDeCompras) {
				modelo.addRow(new Object[] { envio.getIdEnvio(), envio.getDataEnvio(), envio.getForncedor(),
						envio.getProduto() });
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void filtrar() {
		String texto = textpesquisa.getText();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			// busca nas colunas ID (0) e NOME DO CAIXA (1)
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 2, 3));
		}
	}

	public void limpar() {
		tglbtnenvio.setEnabled(true);
	}

}
