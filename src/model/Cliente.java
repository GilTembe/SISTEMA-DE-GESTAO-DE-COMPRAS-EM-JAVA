package model;

public class Cliente {

	private int idCliente;
	private String nomeCliente;
	private String apelidoCliente;
	private Caixa caixa;

	public Cliente() {
	}

	public Cliente(int idCliente, String nomeCliente, String apelidoCliente) {
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		this.apelidoCliente = apelidoCliente;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getApelidoCliente() {
		return apelidoCliente;
	}

	public void setApelidoCliente(String apelidoCliente) {
		this.apelidoCliente = apelidoCliente;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	@Override
	public String toString() {
		return nomeCliente;
	}
}