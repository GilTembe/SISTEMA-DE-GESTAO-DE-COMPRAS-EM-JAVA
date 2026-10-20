package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import model.Cadastro;
import model.Cliente;
import model.Fornecedor;
import controller.Cadastro_controller;
import controller.Fornecedor_controller;

import javax.swing.DropMode;
import javax.swing.JFormattedTextField;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;

public class Cadastro_view implements ActionListener {

	private JFrame frame;
	private JTextField textnome;
	private JTextField textapelido;
	private JTable table;
	private JTextField textusername;
	private JPasswordField passwordField;
	private JLabel lblusuario;
	private JComboBox comboBox;
	private Cadastro usuarioLogado;
	private JTextField textpesquisa;
	private TableRowSorter<DefaultTableModel> sorter;
	private JButton btngerarsenhas;
	private JButton btncadastrar;
	private JButton btnResetarSenha;
	private JButton btnge_username;
	private JButton btnlimpar;
	private JButton btntelaprincipal;
	private JButton btnlistar;
	private JButton btnRemover;
	private JButton btnAtualizar;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */

	public Cadastro_view(Cadastro usuarioLogado) {
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
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1229, 683);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(96, 75, 361, 540);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("CADASTRAR NOVO USUARIO");
		lblNewLabel.setForeground(new Color(183, 219, 219));
		lblNewLabel.setBackground(new Color(164, 202, 166));
		lblNewLabel.setFont(new Font("THE BOLD FONT (FREE VERSION)", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 55, 347, 71);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(10, 115, 341, 299);
		panel.add(panel_1);
		panel_1.setLayout(null);

		textnome = new JTextField();
		textnome.setBounds(35, 66, 277, 34);
		panel_1.add(textnome);
		textnome.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("NOME");
		lblNewLabel_1.setFont(new Font("Segoe UI Semilight", Font.BOLD, 13));
		lblNewLabel_1.setBounds(35, 41, 72, 14);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("APELIDO");
		lblNewLabel_2.setFont(new Font("Segoe UI Semilight", Font.BOLD, 13));
		lblNewLabel_2.setBounds(35, 117, 106, 14);
		panel_1.add(lblNewLabel_2);

		textapelido = new JTextField();
		textapelido.setColumns(10);
		textapelido.setBounds(35, 137, 277, 34);
		panel_1.add(textapelido);

		JLabel lblNewLabel_3 = new JLabel("SENHA");
		lblNewLabel_3.setFont(new Font("Segoe UI Semibold", Font.BOLD, 11));
		lblNewLabel_3.setBounds(35, 238, 48, 14);
		panel_1.add(lblNewLabel_3);

		comboBox = new JComboBox();
		comboBox.setForeground(new Color(0, 0, 0));
		comboBox.setBackground(new Color(226, 241, 241));
		comboBox.addItem("OPERADOR");
		comboBox.addItem("SUPER OPERADOR");
		comboBox.addItem("AUDITOR");
		comboBox.addItem("ADMINISTRADOR");

		comboBox.setBounds(136, 11, 171, 22);
		panel_1.add(comboBox);

		btngerarsenhas = new JButton("GERAR SENHA");
		btngerarsenhas.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btngerarsenhas.setBackground(new Color(183, 219, 219));
		btngerarsenhas.setBounds(190, 263, 122, 22);
		panel_1.add(btngerarsenhas);

		textusername = new JTextField();
		textusername.setBounds(35, 207, 134, 20);
		panel_1.add(textusername);
		textusername.setColumns(10);

		btnge_username = new JButton("GERAR USERNAME");
		btnge_username.setBackground(new Color(158, 207, 207));
		btnge_username.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnge_username.setBounds(190, 206, 122, 22);
		panel_1.add(btnge_username);

		passwordField = new JPasswordField();
		passwordField.setEditable(false);
		passwordField.setBounds(35, 263, 134, 20);
		panel_1.add(passwordField);

		JLabel lblNewLabel_3_1 = new JLabel("USERNAME");
		lblNewLabel_3_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 11));
		lblNewLabel_3_1.setBounds(35, 182, 88, 14);
		panel_1.add(lblNewLabel_3_1);

		btncadastrar = new JButton("CADASTRAR");
		btncadastrar.setBackground(new Color(183, 219, 219));
		btncadastrar.setBounds(49, 436, 258, 22);
		panel.add(btncadastrar);

		btnResetarSenha = new JButton("RESETAR SENHA");
		btnResetarSenha.setBackground(new Color(183, 219, 219));
		btnResetarSenha.setBounds(49, 469, 258, 22);
		panel.add(btnResetarSenha);

		btnlimpar = new JButton("LIMPAR ");
		btnlimpar.setBackground(new Color(120, 188, 188));
		btnlimpar.setBounds(49, 502, 258, 22);
		panel.add(btnlimpar);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(541, 176, 616, 373);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "NOME", "APELIDO", "PERFIL", "USERNAME" }));
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
		table.setRowSorter(sorter);
		// aqui
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				selecionar();
			}
		});

		btntelaprincipal = new JButton("TELA PRINCIPAL");
		btntelaprincipal.setBackground(new Color(183, 219, 219));
		btntelaprincipal.setBounds(987, 75, 170, 22);
		frame.getContentPane().add(btntelaprincipal);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(528, 560, 616, 48);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		btnlistar = new JButton("LISTAR");
		btnlistar.setBackground(new Color(183, 219, 219));
		btnlistar.setBounds(60, 11, 119, 23);
		panel_3.add(btnlistar);

		btnRemover = new JButton("REMOVER");
		btnRemover.setBackground(new Color(183, 219, 219));
		btnRemover.setBounds(269, 11, 119, 23);
		panel_3.add(btnRemover);

		btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.setBackground(new Color(183, 219, 219));
		btnAtualizar.setBounds(453, 11, 119, 23);
		panel_3.add(btnAtualizar);

		lblusuario = new JLabel("New label");
		lblusuario.setBounds(96, 33, 381, 31);
		frame.getContentPane().add(lblusuario);

		JLabel lblNewLabel_1_1 = new JLabel("PESQUISA");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(1029, 134, 112, 14);
		frame.getContentPane().add(lblNewLabel_1_1);

		textpesquisa = new JTextField();
		textpesquisa.setBounds(807, 130, 203, 22);
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

		// registo do listener unico para todos os botoes
		btngerarsenhas.addActionListener(this);
		btnge_username.addActionListener(this);
		btncadastrar.addActionListener(this);
		btnResetarSenha.addActionListener(this);
		btnlimpar.addActionListener(this);
		btntelaprincipal.addActionListener(this);
		btnlistar.addActionListener(this);
		btnRemover.addActionListener(this);
		btnAtualizar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btngerarsenhas) {
			String nome = textnome.getText();
			String apelido = textapelido.getText();
			char primeiraLetra = nome.charAt(0);
			String senhaGerada = primeiraLetra + apelido + "321";

			passwordField.setText(senhaGerada);
		}

		if (e.getSource() == btnge_username) {
			String nome = textnome.getText();
			String apelido = textapelido.getText();
			char primeiraLetra = nome.charAt(0);
			String username = primeiraLetra + apelido;
			textusername.setText(username);
		}

		if (e.getSource() == btncadastrar) {
			String nome = textnome.getText();
			String apelido = textapelido.getText();
			String senha = passwordField.getText();
			String perfil = comboBox.getSelectedItem().toString();
			String username = textusername.getText();

			if (nome.isEmpty() || apelido.isEmpty() || senha.isEmpty()) {
				JOptionPane.showMessageDialog(null, "PREENCHA TODOS OS CAMPOS");
				return;
			}
			try {
				Cadastro_controller controller = new Cadastro_controller();
				controller.adicionarLogin(nome, apelido, senha, perfil, username);
				JOptionPane.showMessageDialog(null, "USUARIO CADASTRADO COM SUCESSO");
				criarCredenciais();
				listar();
				limpar();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "ERRO AO ADICIONAR" + ex.getMessage());
			}
		}

		if (e.getSource() == btnResetarSenha) {
			ResetarSenha resetar = new ResetarSenha();
			resetar.setVisible(true);
		}

		if (e.getSource() == btnlimpar) {
			limpar();
		}

		if (e.getSource() == btntelaprincipal) {
			Programa programa = new Programa(usuarioLogado);
			programa.setVisible(true);
			frame.dispose();
		}

		if (e.getSource() == btnlistar) {
			listar();
		}

		if (e.getSource() == btnRemover) {
			int linhaSeleccionada = table.getSelectedRow();
			if (linhaSeleccionada == -1) {
				JOptionPane.showMessageDialog(null, "POR FAVOR SELECIONA UM CADASTRO NA TABELA");
				return;
			}
			int idusuarios = (int) table.getValueAt(linhaSeleccionada, 0);
			if (usuarioLogado != null && idusuarios == usuarioLogado.getIdusuarios()) {
				JOptionPane.showMessageDialog(null, "NAO E POSSIVER REMOVER SUA PROPRIA CONTA");
				return;
			}
			try {
				Cadastro_controller controller = new Cadastro_controller();
				controller.remover(idusuarios, null, null);
				JOptionPane.showMessageDialog(null, "CADASTRO REMOVIDO COM SUCESSO");
				listar();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "ERRO AO REMOVER" + ex.getMessage());
			}
		}

		if (e.getSource() == btnAtualizar) {
			int linhaSeleccionada = table.getSelectedRow();
			if (linhaSeleccionada == -1) {
				JOptionPane.showMessageDialog(null, "POR FAVOR SELECIONA UM CADASTRO NA TABELA");
				return;
			}
			int idusuarios = (int) table.getValueAt(linhaSeleccionada, 0);
			String perfilcombo = comboBox.getSelectedItem().toString();

			try {
				Cadastro_controller controller = new Cadastro_controller();
				controller.actualizarPerfil(idusuarios, perfilcombo, usuarioLogado.getperfil(),
						usuarioLogado.getNome());
				JOptionPane.showMessageDialog(null, "PERFIL ATUALIZADO COM SUCESSO");
				listar();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR: " + ex.getMessage());
			}
		}
	}

	public void limpar() {
		textnome.setText("");
		textnome.setEnabled(true);
		textapelido.setText("");
		textapelido.setEnabled(true);
		passwordField.setText("");
		// passwordField.setEnabled(true);
		textusername.setText("");
		textusername.setEnabled(true);
		btngerarsenhas.setSelected(true);
		btngerarsenhas.setEnabled(true);
		btnResetarSenha.setEnabled(true);
		btncadastrar.setEnabled(true);
		btnge_username.setEnabled(true);
	}

	private void criarCredenciais() {
		String nome = textnome.getText();
		String apelido = textapelido.getText();
		String username = textusername.getText();
		String perfil = (String) comboBox.getSelectedItem();
		String senha = String.valueOf(passwordField.getPassword());

		try {
			FileWriter writer = new FileWriter(nome + " utilizador cadastrado" + ".txt");

			writer.write("Nome:" + nome + " " + apelido + "\n");
			writer.write("Perfil:" + perfil + "\n");
			writer.write("USERNAME:" + username + "\n");
			writer.write("Senha:" + senha + "\n");

			writer.close();

			JOptionPane.showMessageDialog(null, "SENHA GERADA COM SUCESSO!!");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "ERRO AO GERAR SENHA");
		}
	}

	public void verificarPermissoes() {

		if (usuarioLogado == null)
			return;

		switch (usuarioLogado.getperfil()) {

		case "OPERADOR":
			// tglbtnremover.setEnabled(false);
			// tglbtneditar.setEnabled(false);
			break;

		case "SUPER OPERADOR":
			break;

		case "AUDITOR":
			break;

		case "ADMINISTRADOR":
			break;
		}
	}

	private boolean validarCampos() {
		String apelido = textapelido.getText();
		String nome = textnome.getText();
		String username = textusername.getText();

		if (apelido.isEmpty() || nome.isEmpty() || username.isEmpty()) {
			JOptionPane.showMessageDialog(null, "UM DOS CAMPOS NAO FOI PREENCHIDOS");
			return false;
		}
		return true;
	}

	public void listar() {
		DefaultTableModel listarNaTabela = (DefaultTableModel) table.getModel();
		listarNaTabela.setRowCount(0);
		Cadastro_controller controller = new Cadastro_controller();
		try {
			ArrayList<Cadastro> listaDeLogin = controller.listaDeLogin();
			for (Cadastro cadastro : listaDeLogin) {
				int Idusuarios = cadastro.getIdusuarios();
				String nome = cadastro.getNome();
				String apelido = cadastro.getApelido();
				String perfil = cadastro.getperfil();
				String username = cadastro.getUsername();
				listarNaTabela.addRow(new Object[] { Idusuarios, nome, apelido, perfil, username });
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
			// busca nas colunas ID (0) e NOME DO CAIXA (1)
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0, 1, 3, 4));
		}
	}

	public void selecionar() {
		int linha = table.getSelectedRow();
		textnome.setText(table.getValueAt(linha, 1).toString());
		textnome.setEnabled(false);
		textapelido.setText(table.getValueAt(linha, 2).toString());
		textapelido.setEnabled(false);
		textusername.setText(table.getValueAt(linha, 4).toString());
		textusername.setEnabled(false);
		comboBox.setSelectedItem(table.getValueAt(linha, 3).toString());
		btngerarsenhas.setEnabled(false);
		btnResetarSenha.setEnabled(false);
		btncadastrar.setEnabled(false);
		btnge_username.setEnabled(false);

	}

}