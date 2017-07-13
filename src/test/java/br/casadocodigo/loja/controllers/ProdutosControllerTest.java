package br.casadocodigo.loja.controllers;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.casadocodigo.loja.confs.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.AppWebConfiguration;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;

// Efetua o carregamento das configurações do MVC do Spring
@WebAppConfiguration

//Indicação do uso do spring-test em conjunto com o junit
@RunWith(SpringJUnit4ClassRunner.class)

//Classe de configuração de classes para a execução do teste
@ContextConfiguration(classes={JPAConfiguration.class, AppWebConfiguration.class, 
		DataSourceConfigurationTest.class, SecurityConfiguration.class})

// Indicação do uso de profiles do spring test
@ActiveProfiles("test")

public class ProdutosControllerTest {

	// Indicação do uso do contexto da aplicação
	@Autowired
	private WebApplicationContext wac;
	
	// Objeto que fará as requisições para os nossos controllers.
	private MockMvc mockMvc;
	
	// Declaração de um filter para uso com o mockMvc
	@Autowired
	private Filter springSecurityFilterChain;
	
		
	// Criação do objeto antes da execução do teste
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
	}
	
	// Método de teste para validar a requisição da home
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception{
		// Simulando uma requisição
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		
		// Verificação da existência de atributos na requisição
		.andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
		
		// Indicação da página acessada pela requisição
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	
	// Método de teste para validar o acesso a tela de cadastro de produtos
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
				.with(SecurityMockMvcRequestPostProcessors
						.user("user@casadocodigo.com.br")
						.password("123456")
						.roles("USUARIO")))
		.andExpect(MockMvcResultMatchers.status().is(403));
	}
	
	
}
