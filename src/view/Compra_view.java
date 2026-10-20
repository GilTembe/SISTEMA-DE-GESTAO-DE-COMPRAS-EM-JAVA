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

import controller.Caixa_controller;
import controller.Cliente_controller;
import controller.Compra_controller;
import controller.Fornecedor_controller;
import controller.Produto_controller;
import model.Cadastro;
import model.Caixa;
import model.Cliente;
import model.Compra;
import model.Produto;

import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import de.wannawork.jcalendar.JCalendarComboBox;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;
import java.awt.Font;

public class Compra_view {

	private JFrame frame;
	private JTextField textquantidade;
	private Cadastro usuarioLogado;
	private JScrollPane scrollPane;
	private JLabel lblusuario;
	private JButton tglbtnenviar;
	private JButton tglbtnlistar;
	private JButton tglbtneditar;
	private JButton tglbtnremover;
	private JComboBox<Cliente> comboClinete;
	private JComboBox<Produto> comboProduto;
	private JCalendarComboBox calendardataCompra;
	private int idCompraSelecionada = -1;
	private JTextField textpesquisa;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JLabel lblNewLabel_2;
	private JLabel lblGestaoDeCompras;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Compra_view(Cadastro usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		initialize();
		carregarCliente();
		carregarProduto();
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
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1278, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(49, 124, 414, 495);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textquantidade = new JTextField();
		textquantidade.setColumns(10);
		textquantidade.setBounds(40, 146, 279, 20);
		panel.add(textquantidade);

		JLabel lblNewLabel = new JLabel("DATA DA COMPRA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(40, 59, 127, 14);
		panel.add(lblNewLabel);

		JLabel lblPrecoUnitario = new JLabel("QUANTIDADE");
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoUnitario.setBounds(50, 121, 94, 14);
		panel.add(lblPrecoUnitario);

		calendardataCompra = new JCalendarComboBox();
		calendardataCompra.setBounds(40, 84, 179, 20);
		panel.add(calendardataCompra);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(28, 259, 291, 127);
		panel.add(panel_1);
		panel_1.setLayout(null);

		tglbtnenviar = new JButton("ENVIAR");
		tglbtnenviar.setBackground(new Color(94, 174, 174));
		tglbtnenviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantidade;
				try {
					quantidade = Integer.parseInt(textquantidade.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "DIGITA APENAS NUMEROS");
					return;
				}
				LocalDate dataCompra = calendardataCompra.getCalendar().toInstant()
						.atZone(java.time.ZoneId.systemDefault()).toLocalDate();

				Cliente clienteSelecionado = (Cliente) comboClinete.getSelectedItem();
				Produto produtoSelecionado = (Produto) comboProduto.getSelectedItem();
				String nomeUsuario = usuarioLogado.getNome();
				String perfil = usuarioLogado.getperfil();

				int anoCompra = dataCompra.getYear();
				if ((anoCompra > LocalDate.now().getYear())) {
					JOptionPane.showMessageDialog(null, " O ANO DA COMPRA NAO PODE SER MAIOR QUE 2026");
					return;
				}

				try {
					Compra_controller controller = new Compra_controller();
					controller.adicionarCompra(quantidade, dataCompra, produtoSelecionado.getIdproduto(),
							clienteSelecionado.getIdCliente(), nomeUsuario, perfil);
					JOptionPane.showMessageDialog(null, "COMPRA ADICIONADA COM SUCESSO");

					listar();
					limpar();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		tglbtnenviar.setBounds(10, 11, 122, 38);
		panel_1.add(tglbtnenviar);

		tglbtneditar = new JButton("EDITAR");
		tglbtneditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "SELECIONA UMA COMPRA NA TABELA");
					return;
				}
				int idCompra = (int) table.getValueAt(linhaSeleccionada, 0);
				int quantidade;
				try {
					quantidade = Integer.parseInt(textquantidade.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "DIGITA APENAS NUMEROS");
					return;
				}

				LocalDate dataCompra = calendardataCompra.getCalendar().toInstant()
						.atZone(java.time.ZoneId.systemDefault()).toLocalDate();

				Cliente clienteSelecionado = (Cliente) comboClinete.getSelectedItem();
				Produto produtoSelecionado = (Produto) comboProduto.getSelectedItem();

				try {
					Compra_controller controller = new Compra_controller();
					controller.actualizarCompra(idCompra, quantidade, dataCompra, produtoSelecionado,
							clienteSelecionado, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "COMPRA EDITADA COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO EDITAR: " + ex.getMessage());
				}
			}
		});
		tglbtneditar.setBackground(new Color(94, 174, 174));
		tglbtneditar.setBounds(142, 11, 122, 38);
		panel_1.add(tglbtneditar);

		tglbtnlistar = new JButton("LISTAR");
		tglbtnlistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		tglbtnlistar.setBackground(new Color(94, 174, 174));
		tglbtnlistar.setBounds(142, 60, 122, 44);
		panel_1.add(tglbtnlistar);

		tglbtnremover = new JButton("REMOVER");
		tglbtnremover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UMA COMPRA NA TABELA");
					return;
				}
				try {
					int idCompra = (int) table.getValueAt(linhaSeleccionada, 0);
					Compra_controller controller = new Compra_controller();
					controller.remover(idCompra, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "COMPRA REMOVIDA COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO REMOVER" + ex.getMessage());
				}
			}
		});
		tglbtnremover.setBackground(new Color(94, 174, 174));
		tglbtnremover.setBounds(10, 60, 122, 44);
		panel_1.add(tglbtnremover);

		JButton btntela = new JButton("TELA PRINCIPAL");
		btntela.setBackground(new Color(94, 174, 174));
		btntela.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Programa programa = new Programa(usuarioLogado);
				programa.setVisible(true);
				frame.dispose();
			}

		});
		btntela.setBounds(40, 397, 115, 34);
		panel.add(btntela);

		comboClinete = new JComboBox();
		comboClinete.setBackground(new Color(64, 128, 128));
		comboClinete.setBounds(40, 193, 152, 22);
		panel.add(comboClinete);

		comboProduto = new JComboBox();
		comboProduto.setBackground(new Color(64, 128, 128));
		comboProduto.setBounds(40, 226, 152, 22);
		panel.add(comboProduto);

		JLabel lblNewLabel_1 = new JLabel("CLIENTE");
		lblNewLabel_1.setBounds(217, 197, 102, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("PRODUTO");
		lblNewLabel_1_1.setBounds(217, 230, 102, 14);
		panel.add(lblNewLabel_1_1);

		btnNewButton = new JButton("LIMPAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.setBounds(177, 397, 115, 34);
		panel.add(btnNewButton);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(510, 195, 661, 307);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "DATA COMPRA", "QUANTIDADE", "CLIENTE", "PRODUTO", "PERCO TOTAL" }));
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = table.getSelectedRow();
				textquantidade.setText(table.getValueAt(linha, 2).toString());
				tglbtnenviar.setEnabled(false);
			}
		});

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(49, 49, 427, 25);
		frame.getContentPane().add(lblusuario);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(695, 124, 269, 32);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);

		lblNewLabel_2 = new JLabel("PESQUISA");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(978, 133, 112, 14);
		frame.getContentPane().add(lblNewLabel_2);

		lblGestaoDeCompras = new JLabel("GESTAO DE COMPRAS");
		lblGestaoDeCompras.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeCompras.setForeground(new Color(120, 188, 188));
		lblGestaoDeCompras.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		lblGestaoDeCompras.setBounds(469, 45, 201, 28);
		frame.getContentPane().add(lblGestaoDeCompras);
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

	private void carregarCliente() {
		try {
			comboClinete.removeAllItems();
			ArrayList<Cliente> lista = Cliente_controller.listaDeClientes();
			for (Cliente cliente : lista) {
				comboClinete.addItem(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void carregarProduto() {
		try {
			comboProduto.removeAllItems();
			ArrayList<Produto> lista = Produto_controller.listaDeProdutos();
			for (Produto produto : lista) {
				comboProduto.addItem(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void filtrar() {
		String texto = textpesquisa.getText();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 2, 3, 4, 5, 6));
		}

	}

	public void limpar() {
		textquantidade.setText("");
		tglbtnenviar.setEnabled(true);

	}

	public void listar() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		try {
			ArrayList<Compra> listaDeCompras = Compra_controller.listaDeCompras();
			for (Compra compra : listaDeCompras) {

				modelo.addRow(new Object[] { compra.getIdcompra(), compra.getDataCompra(), compra.getQuantidade(),
						compra.getCliente().getNomeCliente(), compra.getProduto().getProduto(), compra.precoTotal() });
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
