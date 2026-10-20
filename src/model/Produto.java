package model;

import java.time.LocalDate;

public class Produto {

	private int idproduto;
	private String produto;
	private double precoUnitario;
	private LocalDate dataValidade;
	private LocalDate dataConfecao;

	public Produto() {
	}

	public Produto(int idproduto, String produto, double precoUnitario, LocalDate dataValidade,
			LocalDate dataConfecao) {
		this.idproduto = idproduto;
		this.produto = produto;
		this.precoUnitario = precoUnitario;
		this.dataValidade = dataValidade;
		this.dataConfecao = dataConfecao;
	}

	public int getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public LocalDate getDataConfecao() {
		return dataConfecao;
	}

	public void setDataConfecao(LocalDate dataConfecao) {
		this.dataConfecao = dataConfecao;
	}

	@Override
	public String toString() {
		return produto;
	}
}