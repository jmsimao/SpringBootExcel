package br.com.josemarcelo.SpringBootExcel.ErrorResponse;

public class ErrorResponse {
		
	private final int erroHttpNo;
	private final String erroHttpInfo;
	private String mensagem;
	private String erroInfo;
	private String nomeClasse;
	
	public ErrorResponse(int erroHttpNo, String erroHttpInfo, String mensagem, String erroInfo, String nomeClasse) {
		super();
		this.erroHttpNo = erroHttpNo;
		this.erroHttpInfo = erroHttpInfo;
		this.mensagem = mensagem;
		this.erroInfo = erroInfo;
		this.nomeClasse = nomeClasse;
	}

	public int getErroHttpNo() {
		return this.erroHttpNo;
	}

	public String getErroHttpInfo() {
		return this.erroHttpInfo;
	}

	public String getMensagem() {
		return this.mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getErroInfo() {
		return this.erroInfo;
	}

	public void setErroInfo(String erroInfo) {
		this.erroInfo = erroInfo;
	}
	
	public String getNomeClasse() {
		return this.nomeClasse;
	}
	
	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}
	
}
