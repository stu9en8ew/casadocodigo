package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;
import br.com.casadocodigo.loja.models.Usuario;

// Controller para finalizar a compra do carrinho
@RequestMapping("/pagamento")
@Controller
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate; // Uso da classe RestTemplate para executar requisições POST com JSON
	
	// Uso do mailsernder do Spring para envio de emails
	@Autowired
	private MailSender sender;
	
	/*
	// Método para finalizar a compra usando um serviço de pagamento simulado com RestTemplate
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public ModelAndView finalizar(RedirectAttributes model){
		
		try {
			
			// Url para executar na requisiçào POST
			String url = "http://book-payment.herokuapp.com/payment";
			
			//Execução da requisição POST
			String response = restTemplate.postForObject(url, new DadosPagamento(carrinho.getTotal()), String.class);
			
			// Disponibilizando mensagem na view com o escopo flash
			model.addFlashAttribute("sucesso", response);
			
			// Printa no console o resultado
			System.out.println(response);
			
			// Definição do redirecionamento da página
			return new ModelAndView("redirect:/produtos");
			
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			
			// Disponibilizando mensagem na view com o escopo flash
			model.addFlashAttribute("falha", "Valor maior que o permitido");
			
			// Definição do redirecionamento da página
			return new ModelAndView("redirect:/produtos");
		}
		
	}
	
	*/
	
	
		// Transformando o método para trabalhar com requisições assíncronas
		// Método para finalizar a compra usando um serviço de pagamento simulado com RestTemplate
		@RequestMapping(value="/finalizar", method=RequestMethod.POST)
		public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario,RedirectAttributes model){
			
			return()->{
				
				try {
					
					// Url para executar na requisiçào POST
					String url = "http://book-payment.herokuapp.com/payment";
					
					//Execução da requisição POST
					String response = restTemplate.postForObject(url, new DadosPagamento(carrinho.total()), String.class);
					
					// Disponibilizando mensagem na view com o escopo flash
					model.addFlashAttribute("sucesso", response);
					
					// Printa no console o resultado
					System.out.println(response);
					
					// Envia email para o usuário
					enviaEmailCompraProduto(usuario);
					
					// Definição do redirecionamento da página
					return new ModelAndView("redirect:/produtos");
					
				} catch (HttpClientErrorException e) {
					e.printStackTrace();
					
					// Disponibilizando mensagem na view com o escopo flash
					model.addFlashAttribute("falha", "Valor maior que o permitido");
					
					// Definição do redirecionamento da página
					return new ModelAndView("redirect:/produtos");
				}
		
			};
			
		}

		private void enviaEmailCompraProduto(Usuario usuario) {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setSubject("Compra finalizada com sucesso");
			email.setTo(usuario.getEmail());
			email.setText("Compra aprovada com sucesso");
			email.setFrom("compras@casadocodigo.com.br");
			
			sender.send(email);
		}
	
	
}