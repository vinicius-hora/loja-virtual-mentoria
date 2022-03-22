package jdev.lojavirtual.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "seq_item_venda_loja", sequenceName = "seq_item_venda_loja",
initialValue = 1, allocationSize = 1)
@Table(name = "item_venda_loja")
public class ItemVendaLoja implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_venda_loja")
	private Long id;
	
	@Column(nullable = false)
	private Double quantidade;
	
	@ManyToOne
	@JoinColumn(name = "item_venda_loja0produto_id", nullable = false, foreignKey = 
	@ForeignKey(value = ConstraintMode.CONSTRAINT, name = "item_venda_loja0produto_fk"))
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "item_venda_loja0venda_compra_loja_virt_id", nullable = false, foreignKey = 
	@ForeignKey(value = ConstraintMode.CONSTRAINT, name = "item_venda_loja0venda_compra_loja_virt_fk"))
	private VendaCompraLojaVirtual vendaCompraLojaVirtual;

	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "itemVendaLoja0empresa_id", nullable = false, foreignKey = 
	@ForeignKey(value = ConstraintMode.CONSTRAINT, name = "itemVendaLoja0empresa_fk"))
	private Pessoa empresa;

	public Pessoa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Pessoa empresa) {
		this.empresa = empresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public VendaCompraLojaVirtual getVendaCompraLojaVirtual() {
		return vendaCompraLojaVirtual;
	}

	public void setVendaCompraLojaVirtual(VendaCompraLojaVirtual vendaCompraLojaVirtual) {
		this.vendaCompraLojaVirtual = vendaCompraLojaVirtual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVendaLoja other = (ItemVendaLoja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	

}
