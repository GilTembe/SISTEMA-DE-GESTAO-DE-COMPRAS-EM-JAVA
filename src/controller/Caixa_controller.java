package controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Caixa;
import model.Cliente;

public class Caixa_controller {

	private Log_controller log = new Log_controller();

	public void adicionarCaixa(String nomeCaixa, String nome, String perfil) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO CAIXA (nomeCaixa) VALUES (?)");
		stmt.setString(1, nomeCaixa);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adicionou um caixa: " + nomeCaixa);

	}

	public static ArrayList<Caixa> listaDeCaixas() throws SQLException {

		ArrayList<Caixa> lista = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM CAIXA");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Caixa caixa = new Caixa();
			caixa.setIdCaixa(rs.getInt("idCaixa"));
			caixa.setNomeCaixa(rs.getString("nomeCaixa"));
			lista.add(caixa);

		}
		rs.close();
		stmt.close();
		con.close();
		return lista;

	}

	public void actualizarCaixa(int idCaixa, String nomeCaixa, String nome, String perfil) throws SQLException {
		
		Connection con = Conexao.conectar();

		PreparedStatement stmt = null;
		stmt = con.prepareStatement("UPDATE CAIXA SET nomeCaixa=? WHERE idCaixa=?");
		stmt.setString(1, nomeCaixa);
		stmt.setInt(2, idCaixa);
		stmt.executeUpdate();

		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Actualizou um caixa: " + nomeCaixa);
	}

	public void remover(int idcaixa, String nome, String perfil) throws SQLException {
		
		Connection con = Conexao.conectar();
		PreparedStatement stmt = null;
		stmt = con.prepareStatement("DELETE FROM CAIXA WHERE idCaixa=?");

		stmt.setInt(1, idcaixa);
		stmt.executeUpdate();

		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Removeu um caixa ");
	}

}
