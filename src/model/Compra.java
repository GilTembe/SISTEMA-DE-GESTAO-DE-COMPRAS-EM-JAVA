package model;

import java.time.LocalDate;

public class Compra {

	private int idcompra;
	private int quantidade;
	private LocalDate dataCompra;
	private Produto produto;
	private Cliente cliente;

	public Compra() {
	}

	public Compra(int idcompra, int quantidade, LocalDate dataCompra, Produto produto, Cliente cliente) {
		this.idcompra = idcompra;
		this.quantidade = quantidade;
		this.dataCompra = dataCompra;
		this.produto = produto;
		this.cliente = cliente;
	}

	public int getIdcompra() {
		return idcompra;
	}

	public void setIdcompra(int idcompra) {
		this.idcompra = idcompra;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double precoTotal() {
		return quantidade * produto.getPrecoUnitario();
	}

}
