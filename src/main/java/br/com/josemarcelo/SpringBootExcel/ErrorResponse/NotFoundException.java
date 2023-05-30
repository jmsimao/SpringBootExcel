package br.com.josemarcelo.SpringBootExcel.ErrorResponse;

public class NotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String erroInfo;
	private String nomeClasse;
	
	public NotFoundException(String mensagem, String erroInfo, String nomeClasse) {
		super(mensagem);
		this.erroInfo = erroInfo;
		this.nomeClasse = nomeClasse;
	}

	public String getErroInfo() {
		return this.erroInfo;
	}
	
	public String getNomeClasse() {
		return this.nomeClasse;
	}

}
