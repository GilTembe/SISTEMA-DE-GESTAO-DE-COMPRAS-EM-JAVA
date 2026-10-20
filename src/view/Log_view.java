package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.Conexao;
import controller.Fornecedor_controller;
import controller.Log_controller;
import model.Cadastro;
import model.Log;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Log_view {

	private JFrame frame;
	private Cadastro usuarioLogado;
	private ArrayList<Log> listaDeLogs;
	private JLabel lblusuario;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTextField textpesquisa;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Log_view(Cadastro usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		initialize();
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
		frame.setBounds(100, 100, 1106, 860);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(64, 128, 128));
		panel.setBounds(0, 0, 246, 758);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("LISTAR");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		btnNewButton.setBounds(43, 274, 143, 42);
		panel.add(btnNewButton);

		JButton btntelaprincipal = new JButton("TELA PRINCIPAL");
		btntelaprincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Programa programa = new Programa(usuarioLogado);
				programa.setVisible(true);
				frame.dispose();
			}
		});
		btntelaprincipal.setBackground(new Color(255, 255, 255));
		btntelaprincipal.setBounds(43, 337, 143, 42);
		panel.add(btntelaprincipal);

		JLabel lblGestaoDeLog = new JLabel("GESTAO DE LOG");
		lblGestaoDeLog.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestaoDeLog.setForeground(new Color(255, 255, 255));
		lblGestaoDeLog.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		lblGestaoDeLog.setBounds(24, 86, 201, 28);
		panel.add(lblGestaoDeLog);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(333, 93, 671, 596);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "NOME", "PERFIL", "ACCAO", "DATA" }));
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(256, 37, 378, 25);
		frame.getContentPane().add(lblusuario);

		JLabel lblNewLabel = new JLabel("Pesquisar");
		lblNewLabel.setBounds(605, 44, 71, 25);
		frame.getContentPane().add(lblNewLabel);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(659, 42, 313, 25);
		frame.getContentPane().add(textpesquisa);
		textpesquisa.setColumns(10);
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
		Log_controller controller = new Log_controller();
		try {
			ArrayList<Log> listaDeLogs = controller.listaDeLogs();
			for (Log log : listaDeLogs) {
				modelo.addRow(new Object[] { log.getIdlog(), log.getNome(), log.getPerfil(), log.getAccao(),
						log.getDataAccao() });
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
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 2, 3, 4));
		}
	}

}