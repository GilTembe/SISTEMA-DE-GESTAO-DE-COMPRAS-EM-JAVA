package controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
//import net.proteanit.sql.DbUtils;

import model.Caixa;
import model.Cliente;
import model.Compra;
import model.Produto;

public class Cliente_controller {

	private Log_controller log = new Log_controller();

	public void adicionarCliente(String nomeCliente, String apelidoCliente, int idCaixa, String nome, String perfil)
			throws SQLException {
		
		Connection con = Conexao.conectar();
		PreparedStatement stmt = null;
		
		stmt = con.prepareStatement("INSERT INTO CLIENTE(nomeCliente, apelidoCliente,idCaixa) VALUES (?,?,?)");
		stmt.setString(1, nomeCliente);
		stmt.setString(2, apelidoCliente);
		stmt.setInt(3, idCaixa);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adiciono um cliente: " + nomeCliente);
	}

	public static ArrayList<Cliente> listaDeClientes() throws SQLException {
		ArrayList<Cliente> clientes = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con
				.prepareStatement("SELECT c.idCliente, c.nomeCliente, c.apelidoCliente, p.idCaixa, p.nomeCaixa "
						+ "FROM cliente c " + "JOIN caixa p ON c.idCaixa = p.idCaixa");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			// classe caixa
			Caixa caixa = new Caixa();
			caixa.setIdCaixa(rs.getInt("idCaixa"));
			caixa.setNomeCaixa(rs.getString("nomeCaixa"));

			// classe cliente
			Cliente cliente = new Cliente();
			cliente.setIdCliente(rs.getInt("idCliente"));
			cliente.setNomeCliente(rs.getString("nomeCliente"));
			cliente.setApelidoCliente(rs.getString("apelidoCliente"));
			cliente.setCaixa(caixa);

			clientes.add(cliente);
		}

		rs.close();
		stmt.close();
		con.close();
		return clientes;
	}

	public void actualizarCliente(int idcliente, String nomeCliente, String apelidoCliente, int idCaixa, String nome,
			String perfil) throws SQLException {

		Connection con = Conexao.conectar();

		PreparedStatement stmt = con
				.prepareStatement("UPDATE cliente SET nomeCliente=?, apelidoCliente=?, idCaixa=? WHERE idCliente=?");

		stmt.setString(1, nomeCliente);
		stmt.setString(2, apelidoCliente);
		stmt.setInt(3, idCaixa);
		stmt.setInt(4, idcliente);
		stmt.executeUpdate();

		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Actualizou um cliente" + nomeCliente);
	}

	public void remover(int idcliente, String nome, String perfil) throws SQLException {
		// Conectar c= new Conectar();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = null;
		stmt = con.prepareStatement("DELETE FROM CLIENTE WHERE idcliente=?");

		stmt.setInt(1, idcliente);
		stmt.executeUpdate();

		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Removeu um cliente: " + idcliente);
	}
}