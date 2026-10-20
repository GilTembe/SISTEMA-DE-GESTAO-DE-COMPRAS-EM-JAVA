package model;

public class Caixa {

	private int idCaixa;
	private String nomeCaixa;

	public Caixa() {
	}

	public Caixa(int idCaixa, String nomeCaixa) {
		this.idCaixa = idCaixa;
		this.nomeCaixa = nomeCaixa;
	}

	public int getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(int idCaixa) {
		this.idCaixa = idCaixa;
	}

	public String getNomeCaixa() {
		return nomeCaixa;
	}

	public void setNomeCaixa(String nomeCaixa) {
		this.nomeCaixa = nomeCaixa;
	}

	@Override
	public String toString() {
		return nomeCaixa;
	}
}
