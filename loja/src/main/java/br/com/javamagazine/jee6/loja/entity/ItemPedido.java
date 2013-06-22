package br.com.javamagazine.jee6.loja.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ItemPedido implements Serializable, Comparable<ItemPedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7098009672155454810L;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	@Column(name = "preco_unitario")
	@NotNull
	private Double precoUnitario;
	
	@NotNull
	private Integer quantidade;
	
	@Column(name = "preco_total")
	@NotNull
	private Double precoTotal;
	
	public ItemPedido() {}
	
	public ItemPedido(Produto produto) {
		this.produto = produto;
	}
	
	public ItemPedido(Produto produto, Integer quantidade) {
		this.produto = produto;
		this.precoUnitario = produto.getPreco();
		this.quantidade = quantidade;
		calcularTotal();
	}

	public void calcularTotal() {
		precoTotal = precoUnitario * quantidade;
	}
	
	public void atualizarQuantidade(Integer novaQuantidade) {
		quantidade = novaQuantidade;
		calcularTotal();
	}

	@Override
	public int compareTo(ItemPedido o) {
		return produto.getTitulo().compareTo(o.getProduto().getTitulo());
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((precoTotal == null) ? 0 : precoTotal.hashCode());
		result = prime * result
				+ ((precoUnitario == null) ? 0 : precoUnitario.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
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
		ItemPedido other = (ItemPedido) obj;
		if (precoTotal == null) {
			if (other.precoTotal != null)
				return false;
		} else if (!precoTotal.equals(other.precoTotal))
			return false;
		if (precoUnitario == null) {
			if (other.precoUnitario != null)
				return false;
		} else if (!precoUnitario.equals(other.precoUnitario))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ItemPedido [produto=" + produto + ", precoUnitario="
		        + precoUnitario + ", quantidade=" + quantidade + ", precoTotal="
		        + precoTotal + "]";
	}
	
}
