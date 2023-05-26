package br.com.josemarcelo.SpringBootExcel.Model;

public class Cidade {

	private final double Id;
	private final String cidade;
	private final String uf;
	
	public Cidade(double id, String cidade, String uf) {
		super();
		this.Id = id;
		this.cidade = cidade;
		this.uf = uf;
	}

	public double getId() {
		return Id;
	}

	public String getCidade() {
		return cidade;
	}

	public String getUf() {
		return uf;
	}
		
}
