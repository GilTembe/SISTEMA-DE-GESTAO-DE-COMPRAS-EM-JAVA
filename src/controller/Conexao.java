package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexao {
	public static Connection conectar() {
		Connection conexao = null;
		try {
			if (conexao == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/produtos", "root", "petit.petit");
				//JOptionPane.showMessageDialog(null, "CONECTADO COM SUCESSO");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return conexao;
	}

	public static Connection connector() {
		// TODO Auto-generated method stub
		return null;
	}

// public static void main(String[] args) {
// conectar();
//}
}
