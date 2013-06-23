package br.com.javamagazine.jee6.loja.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.jee6.loja.entity.Cliente;
import br.com.javamagazine.jee6.loja.entity.ItemPedido;
import br.com.javamagazine.jee6.loja.entity.Pedido;
import br.com.javamagazine.jee6.loja.entity.Produto;
import br.com.javamagazine.jee6.loja.exception.ClienteExistenteException;
import br.com.javamagazine.jee6.loja.exception.ClienteNaoEncontradoException;
import br.com.javamagazine.jee6.loja.services.ClienteServices;
import br.com.javamagazine.jee6.loja.services.PedidoServices;

@Named
@SessionScoped
public class CarrinhoDeComprasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7162879065611634918L;
	
	private Pedido pedidoCarrinho;
	private Long idPedidoGerado;
	private Cliente cliente;
	private Produto produtoRemover;
	
	@Inject
	private transient ClienteServices clienteServices;
	
	@Inject
	private transient PedidoServices pedidoServices;
	
	@Inject
	private transient UtilsMB utilsMB;
	
	@PostConstruct
	public void init() {
		pedidoCarrinho = new Pedido();
		cliente = new Cliente();
	}
	
	public String adicionarItem(Produto produto) {
		pedidoCarrinho.adicionarItem(produto, 1);
		return "carrinho?faces-redirect=true";
	}
	
	public void removerItem() {
		pedidoCarrinho.removerItem(produtoRemover);
	}
	
	public void atualizarQuantidadeItem(Produto produto, Integer novaQuantidade) {
		pedidoCarrinho.atualizarQuantidade(produto, novaQuantidade);
	}
	
	public String fecharPedidoUsuarioExistente() {
		return fecharPedido();
	}
	
	public String fecharPedidoNovoUsuario() {
		try {
			cliente = clienteServices.adicionar(cliente);
		} catch (ClienteExistenteException e) {
			adicionarMensagem(FacesMessage.SEVERITY_ERROR, "cliente-existente");
			return null;
		}
		return fecharPedido();
	}

	private String fecharPedido() {
		try {
			pedidoCarrinho = pedidoServices.criarPedido(pedidoCarrinho, cliente.getEmail(), cliente.getSenha());
			idPedidoGerado = pedidoCarrinho.getId();
			init();
			return "pedidoFechado?faces-redirect=true";
		} catch (ClienteNaoEncontradoException e) {
			adicionarMensagem(FacesMessage.SEVERITY_ERROR, "cliente-nao-encontrado");
			return null;
		}
	}

	private void adicionarMensagem(Severity severidade, String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severidade, utilsMB.getMessage(chave), null));
	}
	
	public void recalcularTotal(ItemPedido itemPedido) {
		itemPedido.calcularTotal();
		pedidoCarrinho.calcularTotal();
	}
	
	public boolean temItens() {
		return pedidoCarrinho.getItens().size() > 0;
	}

	public Pedido getPedidoCarrinho() {
		return pedidoCarrinho;
	}

	public void setPedidoCarrinho(Pedido pedidoCarrinho) {
		this.pedidoCarrinho = pedidoCarrinho;
	}

	public Long getIdPedidoGerado() {
		return idPedidoGerado;
	}

	public void setIdPedidoGerado(Long idPedidoGerado) {
		this.idPedidoGerado = idPedidoGerado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProdutoRemover() {
		return produtoRemover;
	}

	public void setProdutoRemover(Produto produtoRemover) {
		this.produtoRemover = produtoRemover;
	}
	
}
