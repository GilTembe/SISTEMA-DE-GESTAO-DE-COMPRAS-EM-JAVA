package model;

import java.time.LocalDate;

import model.Fornecedor;
import model.Produto;

public class Envio {

	private int idEnvio;
	private LocalDate dataEnvio;
	private Fornecedor forncedor;
	private Produto produto;

	public Envio() {
	}

	public Envio(int idEnvio, LocalDate dataEnvio, Fornecedor forncedor, Produto produto) {
		this.idEnvio = idEnvio;
		this.dataEnvio = dataEnvio;
		this.forncedor = forncedor;
		this.produto = produto;
	}

	public int getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(int idEnvio) {
		this.idEnvio = idEnvio;
	}

	public LocalDate getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDate dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Fornecedor getForncedor() {
		return forncedor;
	}

	public void setForncedor(Fornecedor forncedor) {
		this.forncedor = forncedor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "Envio [idEnvio=" + idEnvio + ", dataEnvio=" + dataEnvio + ", forncedor=" + forncedor + ", produto="
				+ produto + "]";
	}

}
