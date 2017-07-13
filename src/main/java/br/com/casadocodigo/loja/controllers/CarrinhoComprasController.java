package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST) // Definido os escopos no Spring MVC
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	@Autowired
	private CarrinhoCompras carrinho;
	
	
	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco){
		
		// Monta a relação de modelo e visão no escopo do carrinho de compras
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		
		// Criando o item
		CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco);
		
		// Adicionando o item ao carrinho
		carrinho.add(carrinhoItem);
		
		return modelAndView;
		
	}
	
	
	// Método para retornar um objeto do tipo CarrinhoItem
	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco){
		Produto produto = produtoDao.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		return carrinhoItem;
	}
	
	
	// Método para direcionar o usuário para a página de itens no carrinho de compras
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens(){
		ModelAndView modelAndView = new ModelAndView("/carrinho/itens");
		return modelAndView;
	}
	
	// Chamada de um método para remover um item do carrinho
	@RequestMapping("/remover")
	public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco){
		
		carrinho.remover(produtoId, tipoPreco);
		
		return new ModelAndView("redirect:/carrinho");
	}
	
	
}
