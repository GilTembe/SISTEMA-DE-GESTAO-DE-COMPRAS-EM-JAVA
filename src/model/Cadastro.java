package model;

public class Cadastro {

	private int idusuarios;
	private String nome;
	private String apelido;
	private String senha;
	private String perfil;
	private String username;

	public Cadastro() {
	}

	public Cadastro(int idusuarios, String nome, String apelido, String senha, String perfil, String username) {
		this.idusuarios = idusuarios;
		this.nome = nome;
		this.apelido = apelido;
		this.senha = senha;
		this.perfil = perfil;
		this.username = username;
	}

	public int getIdusuarios() {
		return idusuarios;
	}

	public void setIdusuarios(int idusuarios) {
		this.idusuarios = idusuarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getperfil() {
		return perfil;
	}

	public void setperfil(String perfil) {
		this.perfil = perfil;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Login [idusuarios=" + idusuarios + ", nome=" + nome + ", apelido=" + apelido + ", senha=" + senha
				+ ", perfil=" + perfil + "]";
	}

}
