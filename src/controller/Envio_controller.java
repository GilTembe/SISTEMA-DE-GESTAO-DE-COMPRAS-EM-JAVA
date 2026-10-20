package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Envio;
import model.Fornecedor;
import model.Produto;

public class Envio_controller {

	private Log_controller log = new Log_controller();

	public void adicionarEnvio(LocalDate dataEnvio, int idFornecedor, int idProduto, String nome, String perfil)
			throws SQLException {
		
		Connection con = Conexao.conectar();
		PreparedStatement stmt = null;
		
		stmt = con.prepareStatement("INSERT INTO envia (dataEnvio, idFornecedor, idProduto) VALUES (?, ?, ?)");
		stmt.setDate(1, Date.valueOf(dataEnvio));
		stmt.setInt(2, idFornecedor);
		stmt.setInt(3, idProduto);

		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adicionou um envio ");
	}

	public static ArrayList<Envio> listaDeEnvios() throws SQLException {
		ArrayList<Envio> envios = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("SELECT e.idEnvia, e.dataEnvio, " + "f.nomeFornecedor, p.produto "
				+ "FROM envia e " + "JOIN fornecedor f ON e.idFornecedor = f.idFornecedor "
				+ "JOIN produto p ON e.idProduto = p.idProduto");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int idEnvio = rs.getInt("idEnvia");
			LocalDate dataEnvio = rs.getDate("dataEnvio").toLocalDate();

			String nomeFornecedor = rs.getString("nomeFornecedor");
			String Produto = rs.getString("produto");

			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setNomeFornecedor(nomeFornecedor);

			Produto produto = new Produto();
			produto.setProduto(Produto);

			Envio envio = new Envio(idEnvio, dataEnvio, fornecedor, produto);

			envios.add(envio);
		}
		return envios;
	}

	public void actualizarEnvio(int idEnvio, LocalDate dataEnvio, int idFornecedor, int idProduto, String nome,
			String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con
				.prepareStatement("UPDATE envia SET dataEnvio=?, idFornecedor=?, idProduto=? WHERE idEnvia=?");

		stmt.setDate(1, Date.valueOf(dataEnvio));
		stmt.setInt(2, idFornecedor);
		stmt.setInt(3, idProduto);
		stmt.setInt(4, idEnvio);

		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Actualizou um envio");
	}

	public void remover(int idEnvio, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM envia WHERE idEnvia=?");

		stmt.setInt(1, idEnvio);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Removeu um envio");
	}

}
