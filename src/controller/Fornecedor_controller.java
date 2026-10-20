package controller;

import model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;

public class Fornecedor_controller {

	private Log_controller log = new Log_controller();

	// -- INSERT --
	public void adicionarFornecedor(String nomeFornecedor, String telemovel, String email, String endereco, String nome,
			String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"INSERT INTO FORNECEDOR (nomeFornecedor, telefone, email, endereco) VALUES (?, ?, ?, ?)");
		stmt.setString(1, nomeFornecedor);
		stmt.setString(2, telemovel);
		stmt.setString(3, email);
		stmt.setString(4, endereco);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adicionou um fornecedor: " + nome);
	}

	public static ArrayList<Fornecedor> listaDeFornecedores() throws SQLException {
		ArrayList<Fornecedor> lista = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT idFornecedor, nomeFornecedor, telefone, email, endereco FROM FORNECEDOR ORDER BY idFornecedor");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int idFornecedor = rs.getInt("idFornecedor");
			String nomeFornecedor = rs.getString("nomeFornecedor");
			String telemovel = rs.getString("telefone");
			String email = rs.getString("email");
			String endereco = rs.getString("endereco");
			lista.add(new Fornecedor(idFornecedor, nomeFornecedor, telemovel, email, endereco));
		}
		rs.close();
		stmt.close();
		con.close();
		return lista;
	}

	public void actualizarFornecedor(int idFornecedor, String nomeFornecedor, String telemovel, String email,
			String endereco, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"UPDATE FORNECEDOR SET nomeFornecedor=?, telefone=?, email=?, endereco=? WHERE idFornecedor=?");
		stmt.setString(1, nomeFornecedor);
		stmt.setString(2, telemovel);
		stmt.setString(3, email);
		stmt.setString(4, endereco);
		stmt.setInt(5, idFornecedor);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Atualizou um fornecedor: " + nome);

	}

	// -- DELETE --
	public void remover(int idFornecedor, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM FORNECEDOR WHERE idFornecedor=?");
		stmt.setInt(1, idFornecedor);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Removeu um fornecedor: " + nome);
	}
}