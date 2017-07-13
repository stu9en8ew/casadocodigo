package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("serial")
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION, // Definindo os escopos no Spring MVC
proxyMode=ScopedProxyMode.TARGET_CLASS) // Criação de proxy para resolver o componente CarrinhoCompras
public class CarrinhoCompras implements Serializable {
	
	// Criação de uma lista que associe um item a sua quantidade
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	
	// Retornamos uma lista de itens baseados na sua chave
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}
	
	

	// Método para adicionar itens a lista
	public void add(CarrinhoItem item){
		
		itens.put(item, getQuantidade(item) + 1);
	}

	// Métoo que realiza o somatório das qtds de itens no carrinho
	public int getQuantidade(CarrinhoItem item) {
		
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		
		return itens.get(item);
	}
	
	// Método que recupera a quantidade de itens no carrinho de compras
	public int getQuantidade(){
		return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}
	
	
	// Método que retorna a soma total de um produto específico recebendo um CarrinhoItem 
	public BigDecimal getTotal(CarrinhoItem item){
		return item.getTotal(getQuantidade(item));
	}
	
	
	// Método que calcula o somatório de todos os produtos no carrinho de compras
	public BigDecimal total(){
		
		BigDecimal total = BigDecimal.ZERO;
		
		for (CarrinhoItem item : itens.keySet()) {
			total = total.add(getTotal(item));
		}
		
		return total;
	}


	// Método para remover itens do carrinho de compras
	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
	
	
}
