package model;

public class Log {

	private int idlog;
	private String nome;
	private String perfil;
	private String accao;
	private String dataAccao;

	public Log() {
	}

	public Log(int idlog, String nome, String perfil, String accao, String dataAccao) {
		this.idlog = idlog;
		this.nome = nome;
		this.perfil = perfil;
		this.accao = accao;
		this.dataAccao = dataAccao;
	}

	public int getIdlog() {
		return idlog;
	}

	public void setIdlog(int idlog) {
		this.idlog = idlog;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getAccao() {
		return accao;
	}

	public void setAccao(String accao) {
		this.accao = accao;
	}

	public String getDataAccao() {
		return dataAccao;
	}

	public void setDataAccao(String dataAccao) {
		this.dataAccao = dataAccao;
	}

	@Override
	public String toString() {
		return "Log [idlog=" + idlog + ", nome=" + nome + ", perfil=" + perfil + ", accao=" + accao + ", dataAccao="
				+ dataAccao + "]";
	}

}
