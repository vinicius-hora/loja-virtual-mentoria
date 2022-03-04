package jdev.lojavirtual.enuns;

public enum StatusContaPagar {
	
	COBRANCA("PAgar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Quitada"),
	NEGOCIADA("Renegociada");
	
	

	private String descricao;
	
	StatusContaPagar(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public String toString() {
		return this.descricao;
	}
}
