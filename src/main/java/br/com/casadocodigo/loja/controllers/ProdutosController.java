package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	@Autowired
	private FileSaver fileSaver;
	
	
	// Criação do vínculo entre as classes controller e validation
	@InitBinder
	public void initBinder(WebDataBinder binder){
		// Setar a classe de validação no binder
		binder.addValidators(new ProdutoValidation());
	}
	

	@RequestMapping("form")
	public ModelAndView form(Produto produto){
		
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	
	// Usaremos um MultipartFile do Spring para gravar um arquivo de sumário
	@RequestMapping(method=RequestMethod.POST)
	// Limpando o cache com o Spring
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes)
	{
		// Verificação se a validação retorna erros
		if (result.hasErrors()) {
			return form(produto);
		}
		
		// Indicação da pasta e o arquivo a serem enviado para o servidor
		String path = fileSaver.writer("arquivos-sumario", sumario);
		
		// Adição ao produto do path do arquivo enviando ao servidor
		produto.setSumarioPath(path);
		
		// Gravação do produto
		produtoDao.gravar(produto);
		
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		
		return new ModelAndView("redirect:produtos");
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
		
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") int id){
		
		// Crio a instância do meu modelAndView setando a página de detalhe
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		
		// Método para recuperar o produto por ID
		Produto produto = produtoDao.find(id);
		
		// Adiciono um produto para ser utilizado na view
		modelAndView.addObject("produto", produto);
		
		// Retorno o meu modelAndView
		return modelAndView;
		
		
	}
	
	
	/*
	@RequestMapping("/{id}")
	@ResponseBody
	public Produto detalheJSON(@PathVariable("id") int id){

		// Retorno o produto por id
		return produtoDao.find(id);
		
	}
	*/
	
	/*
	// Tratamento de exceções genéricas no escopo do controller
	@ExceptionHandler(Exception.class)
	public String trataDetalheNaoEncontrado(){
		return "error";
	}
	*/
	
	
	
	
}
