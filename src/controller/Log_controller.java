package controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Log;

public class Log_controller {

	public void registarLog(String nome, String perfil, String accao) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con
				.prepareStatement("INSERT INTO log (nome, perfil, accao, dataAccao) VALUES (?, ?, ?, NOW())");

		stmt.setString(1, nome);
		stmt.setString(2, perfil);
		stmt.setString(3, accao);
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

	public ArrayList<Log> listaDeLogs() throws SQLException {
		ArrayList<Log> logs = new ArrayList<Log>();
		PreparedStatement stmt = null;
		Connection con = Conexao.conectar();
		stmt = con.prepareStatement("SELECT * FROM log ORDER BY dataAccao DESC");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int idLog = rs.getInt(1);
			String nome = rs.getString(2);
			String perfil = rs.getString(3);
			String accao = rs.getString(4);
			String dataAccao = rs.getString(5);

			logs.add(new Log(idLog, nome, perfil, accao, dataAccao));
		}
		con.close();
		return logs;
	}

}
