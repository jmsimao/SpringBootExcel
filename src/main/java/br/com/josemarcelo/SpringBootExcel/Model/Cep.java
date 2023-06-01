package br.com.josemarcelo.SpringBootExcel.Model;

public class Cep {
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private double cidade;
	private String uf;
	
	public Cep(String cep, String logradouro, String complemento, String bairro, double cidade, String uf) {
		super();
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public double getCidade() {
		return cidade;
	}

	public String getUf() {
		return uf;
	}
	
}
