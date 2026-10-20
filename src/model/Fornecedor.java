package model;

public class Fornecedor {

	private int idFornecedor;
	private String nomeFornecedor;
	private String telefone;
	private String email;
	private String endereco;

	public Fornecedor() {
	}

	public Fornecedor(int idFornecedor, String nomeFornecedor, String telefone, String email, String endereco) {
		this.idFornecedor = idFornecedor;
		this.nomeFornecedor = nomeFornecedor;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return nomeFornecedor;
	}

}
