package br.com.josemarcelo.SpringBootExcel.Model;

public class UF {
	
	private final double Id;
	private final String estado;
	private final String sigla;
	
	public UF(double id, String estado, String sigla) {
		super();
		this.Id = id;
		this.estado = estado;
		this.sigla = sigla;
	}

	public double getId() {
		return Id;
	}

	public String getEstado() {
		return estado;
	}

	public String getSigla() {
		return sigla;
	}
	
}
