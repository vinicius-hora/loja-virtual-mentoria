package jdev.lojavirtual.enuns;

public enum TipoEndereco {
	
	COBRANCA("cobrança"),
	ENTREGA("entrega");
	

	private String descricao;
	

	TipoEndereco(String descricao) {
		this.descricao = descricao;
		
	}


	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}

}
