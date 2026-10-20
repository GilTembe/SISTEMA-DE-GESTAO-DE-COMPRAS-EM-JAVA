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
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComboBox;

import model.Cadastro;
import model.Caixa;
import model.Fornecedor;
import controller.Caixa_controller;
import controller.Cliente_controller;
import controller.Fornecedor_controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;

public class Caixa_view {

	private JFrame frame;
	private JTextField textcaixaNome;
	private Cadastro usuarilogado;
	private Cadastro usuarioLogado;
	private JScrollPane scrollPane;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JLabel lblusuario;
	private JButton tglbtnremover;
	private JButton tglbtnlistar;
	private JButton tglbtneditar;
	private JButton tglbtnenviar;
	private JTextField textpesquisa;
	private JLabel lblNewLabel;
	private JLabel lblGestaoDeCaixa;

//

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Caixa_view(Cadastro usuarioLogado) {
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
		panel.setBounds(66, 120, 420, 385);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textcaixaNome = new JTextField();
		textcaixaNome.setColumns(10);
		textcaixaNome.setBounds(61, 144, 268, 37);
		panel.add(textcaixaNome);

		JLabel lblPrecoUnitario = new JLabel("NOME DO CAIXA");
		lblPrecoUnitario.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		lblPrecoUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoUnitario.setBounds(61, 105, 201, 28);
		panel.add(lblPrecoUnitario);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(24, 222, 372, 113);
		panel.add(panel_1);
		panel_1.setLayout(null);

		tglbtnenviar = new JButton("ENVIAR");
		tglbtnenviar.setBackground(new Color(64, 128, 128));
		tglbtnenviar.setBounds(10, 11, 173, 35);
		panel_1.add(tglbtnenviar);
		tglbtnenviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!validarCampos()) {
						return;
					}
					String nomeCaixa = textcaixaNome.getText();
					Caixa_controller controller = new Caixa_controller();

					controller.adicionarCaixa(nomeCaixa, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "CAIXA ADICIONADO COM SUCESSO");
					listar();
					limpar();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO ADICIONAR" + ex.getMessage());
				}

			}
		});

		tglbtneditar = new JButton("EDITAR");
		tglbtneditar.setBackground(new Color(64, 128, 128));
		tglbtneditar.setBounds(193, 11, 169, 35);
		panel_1.add(tglbtneditar);
		tglbtneditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM CAIXA NA TABELA");
					return;
				}

				String nomeCaixa = textcaixaNome.getText();
				Caixa_controller controller = new Caixa_controller();

				try {
					int idCaixa = (int) table.getValueAt(linhaSeleccionada, 0);
					controller.actualizarCaixa(idCaixa, nomeCaixa, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "CAIXA ADICIOANDO COM SUCESSO");
					listar();
					limpar();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERRO AO ACTUALIZAR" + ex.getMessage());
				}
			}

		});

		tglbtnlistar = new JButton("LISTAR");
		tglbtnlistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
				limpar();
			}
		});
		tglbtnlistar.setBackground(new Color(64, 128, 128));
		tglbtnlistar.setBounds(193, 56, 169, 35);
		panel_1.add(tglbtnlistar);

		tglbtnremover = new JButton("REMOVER");
		tglbtnremover.setBackground(new Color(64, 128, 128));
		tglbtnremover.setBounds(10, 56, 173, 35);
		panel_1.add(tglbtnremover);
		tglbtnremover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSeleccionada = table.getSelectedRow();
				if (linhaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONA UM CAIXA NA TABELA");
					return;
				}
				try {
					int idCaixa = (int) table.getValueAt(linhaSeleccionada, 0);
					Caixa_controller controller = new Caixa_controller();
					controller.remover(idCaixa, usuarioLogado.getNome(), usuarioLogado.getperfil());
					JOptionPane.showMessageDialog(null, "CAIXA REMOVIDO COM SUCESSO");
					listar();
					limpar();

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

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "NOME DO CAIXA" }));
		scrollPane.setViewportView(table);
		// filtro da pesquisa
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = table.getSelectedRow();
				textcaixaNome.setText(table.getValueAt(linha, 1).toString());
				tglbtnenviar.setEnabled(false);
			}
		});

		JButton btnmenuprincipal = new JButton("TELA PRINCIPAL");
		btnmenuprincipal.setBackground(new Color(64, 128, 128));
		btnmenuprincipal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Programa programa = new Programa(usuarioLogado);
				programa.setVisible(true);
				frame.dispose();
			}

		});

		btnmenuprincipal.setForeground(new Color(6, 0, 6));
		btnmenuprincipal.setBounds(97, 551, 138, 42);
		frame.getContentPane().add(btnmenuprincipal);

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(66, 41, 420, 23);
		frame.getContentPane().add(lblusuario);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(781, 77, 258, 32);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);

		lblNewLabel = new JLabel("PESQUISA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(1049, 86, 112, 14);
		frame.getContentPane().add(lblNewLabel);

		lblGestaoDeCaixa = new JLabel("GESTAO DE CAIXA");
		lblGestaoDeCaixa.setBounds(530, 36, 149, 28);
		frame.getContentPane().add(lblGestaoDeCaixa);
		lblGestaoDeCaixa.setForeground(new Color(120, 188, 188));
		lblGestaoDeCaixa.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeCaixa.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));

		JButton btnNewButton = new JButton("LIMPAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.setBounds(298, 551, 156, 42);
		frame.getContentPane().add(btnNewButton);
		// FILTRO DE PESQUISA
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

// metodo lista 
	public void listar() {
		DefaultTableModel listarNaTabela = (DefaultTableModel) table.getModel();
		listarNaTabela.setRowCount(0);
		Caixa_controller controller = new Caixa_controller();
		try {
			ArrayList<Caixa> listaDeCaixas = controller.listaDeCaixas();
			for (Caixa caixa : listaDeCaixas) {
				int idCaixa = caixa.getIdCaixa();
				String nomeCaixa = caixa.getNomeCaixa();
				listarNaTabela.addRow(new Object[] { idCaixa, nomeCaixa });
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void verificarPermissoes() {

		if (usuarioLogado == null)
			return;
		String perfil = usuarioLogado.getperfil();

		switch (perfil) {
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

	public void limpar() {
		textcaixaNome.setText("");
		tglbtnenviar.setEnabled(true);

	}

	// validacao dos campos
	private boolean validarCampos() {
		String nomeCaixa = textcaixaNome.getText();
		if (nomeCaixa.isEmpty() || nomeCaixa.isBlank()) {
			JOptionPane.showMessageDialog(null, "CAMPO NAO FOI PREENCHIDO");
			return false;
		}
		return true;
	}

	private void filtrar() {
		String texto = textpesquisa.getText();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			// busca nas colunas ID (0) e NOME DO CAIXA (1)
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1));
		}
	}
}
