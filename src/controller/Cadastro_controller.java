package controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cadastro;
import controller.Log_controller;

public class Cadastro_controller {

	private Log_controller log = new Log_controller();

	public void adicionarLogin(String nome, String apelido, String senha, String perfil, String username)
			throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"INSERT INTO utilizadores (nome , apelido , senha, perfil, USERNAME) VALUES (?,?,?,?,?)");
		stmt.setString(1, nome);
		stmt.setString(2, apelido);
		stmt.setString(3, senha);
		stmt.setString(4, perfil);
		stmt.setString(5, username);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Adicionou um utilizador ");
	}

	public ArrayList<Cadastro> listaDeLogin() throws SQLException {
		ArrayList<Cadastro> logins = new ArrayList<>();
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(
				"SELECT idusuarios, nome, apelido, perfil,senha,USERNAME FROM utilizadores ORDER BY idusuarios");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int idusuarios = rs.getInt("idusuarios");
			String nome = rs.getString("nome");
			String apelido = rs.getString("apelido");
			String perfil = rs.getString("perfil");
			String senha = rs.getString("senha");
			String username = rs.getString("USERNAME");
			logins.add(new Cadastro(idusuarios, nome, apelido, senha, perfil, username));
		}
		rs.close();
		stmt.close();
		con.close();
		return logins;

	}

	public void actualizarPerfil(int idusuarios, String perfil, String perfilLogado, String nome) throws SQLException {

		Connection con = Conexao.conectar();

		String sql = "UPDATE utilizadores SET perfil=? WHERE idusuarios=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, perfil);
		ps.setInt(2, idusuarios);
		ps.executeUpdate();

		ps.close();
		con.close();

		log.registarLog(nome, perfil, "Atualizou um utilizador ");

	}

	public void remover(int idusuarios, String perfil, String nome) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = null;
		stmt = con.prepareStatement("DELETE FROM utilizadores WHERE idusuarios=?");
		stmt.setInt(1, idusuarios);
		stmt.executeUpdate();
		stmt.close();
		con.close();

		log.registarLog(nome, perfil, "Removeu um utilizador ");
	}

	public static Cadastro autenticar(String senha, String username) throws SQLException {
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM utilizadores WHERE USERNAME=? AND senha=?");

		stmt.setString(1, username);
		stmt.setString(2, senha);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			int idusuarios = rs.getInt("idusuarios");
			String nome = rs.getString("nome");
			String apelido = rs.getString("apelido");
			String perfil = rs.getString("perfil");

			Cadastro usuario = new Cadastro(idusuarios, nome, apelido, senha, perfil, username);

			stmt.close();
			con.close();
			return usuario;
		}

		stmt.close();
		con.close();
		return null;
	}

	public void atualizarSenha(int idUsuario, String novaSenha) throws SQLException {
		Connection con = Conexao.conectar();
		String sql = "UPDATE utilizadores SET senha = ? WHERE idusuarios = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, novaSenha);
		stmt.setInt(2, idUsuario);

		stmt.executeUpdate();

		stmt.close();
		con.close();

	}

}
