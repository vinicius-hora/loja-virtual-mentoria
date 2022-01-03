package jdev.lojavirtual.enuns;

public enum StatusContaReceber {
	
	COBRANCA("PAgar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Quitada");
	
	

	private String descricao;
	
	StatusContaReceber(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public String toString() {
		return this.descricao;
	}
}
