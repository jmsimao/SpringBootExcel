package br.com.josemarcelo.SpringBootExcel.ErrorResponse;

public class ErrorResponse {
		
	private final int erroHttpNo;
	private final String erroHttpInfo;
	private String mensagem;
	private String erroInfo;
	
	public ErrorResponse(int erroHttpNo, String erroHttpInfo, String mensagem, String erroInfo) {
		super();
		this.erroHttpNo = erroHttpNo;
		this.erroHttpInfo = erroHttpInfo;
		this.mensagem = mensagem;
		this.erroInfo = erroInfo;
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
}
