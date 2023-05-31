package br.com.josemarcelo.SpringBootExcel.ErrorResponse;

public class ErrorResponse {
	
	private final int erroHttpNo;
	private final String erroHttpInfo;
	private String mensagem;
	private String erroInfo;
	private final String nomeClasse;
	
	public ErrorResponse(int erroHttpNo, String erroHttpInfo, String mensagem, String erroInfo, String nomeClasse) {
		super();
		this.erroHttpNo = erroHttpNo;
		this.erroHttpInfo = erroHttpInfo;
		this.mensagem = mensagem;
		this.erroInfo = erroInfo;
		this.nomeClasse = nomeClasse;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getErroInfo() {
		return erroInfo;
	}

	public void setErroInfo(String erroInfo) {
		this.erroInfo = erroInfo;
	}

	public int getErroHttpNo() {
		return erroHttpNo;
	}

	public String getErroHttpInfo() {
		return erroHttpInfo;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

}
