package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Produto;

public class Produto_controller {

	private Log_controller log = new Log_controller();

	public void adicionarProduto(String produto, double precoUnitario, LocalDate dataValidade, LocalDate dataConfecao,
			String nome, String perfil) throws SQLException {
		// Busca uma conexo com o banco de dados
		Connection con = Conexao.conectar();
		PreparedStatement stmt = null;
		// Class.forName("oracle.jdbc.driver.OracleDriver");

		stmt = con.prepareStatement(
				"INSERT INTO PRODUTO(produto,precoUnitario, dataValidade, dataConfecao) VALUES (?, ?, ?, ?)");

		stmt.setString(1, produto);
		stmt.setDouble(2, precoUnitario);
		stmt.setDate(3, Date.valueOf(dataValidade));
		stmt.setDate(4, Date.valueOf(dataConfecao));

		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adicionou um produto " + nome);
	}

	public static ArrayList<Produto> listaDeProdutos() throws SQLException {
		ArrayList<Produto> produtos = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM PRODUTO");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			int idproduto = rs.getInt("idproduto");
			String nomeProduto = rs.getString("produto");
			double precoUnitario = rs.getDouble("precoUnitario");
			LocalDate dataValidade = rs.getDate("dataValidade").toLocalDate();
			LocalDate dataConfecao = rs.getDate("dataConfecao").toLocalDate();

			Produto produto = new Produto(idproduto, nomeProduto, precoUnitario, dataValidade, dataConfecao);
			produtos.add(produto);
		}

		rs.close();
		stmt.close();
		con.close();
		return produtos;
	}

	public void actualizarProduto(int idproduto, String produto, double precoUnitario, LocalDate dataValidade,
			LocalDate dataConfecao, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();

		PreparedStatement stmt = con.prepareStatement(
				"UPDATE PRODUTO SET produto=?, precoUnitario=?, dataValidade=?, dataConfecao=? WHERE idproduto=?");
		stmt.setString(1, produto);
		stmt.setDouble(2, precoUnitario);
		stmt.setDate(3, Date.valueOf(dataValidade));
		stmt.setDate(4, Date.valueOf(dataConfecao));

		stmt.setInt(5, idproduto);
		stmt.executeUpdate();

		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Actualizou um produto " + nome);
	}

	public void remover(int idproduto, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM PRODUTO WHERE idproduto=?");
		stmt.setInt(1, idproduto);
		stmt.executeUpdate();
		stmt.close();
		con.close();
		log.registarLog(nome, perfil, "Removeu um produto " + nome);
	}
}