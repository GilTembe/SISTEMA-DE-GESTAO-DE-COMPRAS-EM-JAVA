package controller;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Cliente;
import model.Compra;
import model.Produto;

public class Compra_controller {

	private Log_controller log = new Log_controller();

	public void adicionarCompra(int quantidade, LocalDate dataCompra, int idProduto, int idCliente, String nome,
			String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"INSERT INTO COMPRA(quantidade, dataDaCompra, idProduto, idCliente) VALUES (?, ?, ?, ?)");
		stmt.setInt(1, quantidade);
		stmt.setDate(2, Date.valueOf(dataCompra));
		stmt.setInt(3, idProduto);
		stmt.setInt(4, idCliente);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adicionou uma compra ");
	}

	public static ArrayList<Compra> listaDeCompras() throws SQLException {
		ArrayList<Compra> compras = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("SELECT c.idCompra, c.quantidade, c.dataDaCompra,  "
				+ "p.idProduto, p.produto, p.precoUnitario, " + "cl.idCliente, cl.nomeCliente " + "FROM COMPRA c "
				+ "JOIN PRODUTO p ON c.idProduto = p.idProduto " + "JOIN CLIENTE cl ON c.idCliente = cl.idCliente");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {

			int idcompra = rs.getInt("idCompra");
			int quantidade = rs.getInt("quantidade");
			LocalDate dataCompra = rs.getDate("dataDaCompra").toLocalDate();

			Produto produto = new Produto();
			produto.setIdproduto(rs.getInt("idproduto"));
			produto.setProduto(rs.getString("produto"));
			produto.setPrecoUnitario(rs.getDouble("precoUnitario"));

			Cliente cliente = new Cliente();
			cliente.setIdCliente(rs.getInt("idcliente"));
			cliente.setNomeCliente(rs.getString("nomeCliente"));

			Compra compra = new Compra(idcompra, quantidade, dataCompra, produto, cliente);
			compras.add(compra);
		}
		rs.close();
		stmt.close();
		con.close();
		return compras;
	}

	public void actualizarCompra(int idcompra, int quantidade, LocalDate dataCompra, Produto produto, Cliente cliente,
			String nome, String perfil) throws SQLException {

		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"UPDATE COMPRA SET quantidade=?, dataDaCompra=?, idProduto=?, idCliente=? WHERE idCompra=?");

		stmt.setInt(1, quantidade);
		stmt.setDate(2, Date.valueOf(dataCompra));
		stmt.setInt(3, produto.getIdproduto());
		stmt.setInt(4, cliente.getIdCliente());
		stmt.setInt(5, idcompra);
		stmt.executeUpdate();

		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Actualizou uma compra: " + idcompra);
	}

	public void remover(int idcompra, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM COMPRA WHERE idcompra=?");

		stmt.setInt(1, idcompra);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Removeu uma compra: " + idcompra);
	}
}