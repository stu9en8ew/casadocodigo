package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 Classe para encaminhar as requisições para o SpringMVC
*/

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	// Definição do carregamento das configurações de segurança na subida da aplicação
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityConfiguration.class, AppWebConfiguration.class, 
				JPAConfiguration.class, JPAProductionConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String []{"/"};
	}
	
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		
		//Acréscimo do filtro para a manutenção da sessão até os dados serem carregados nas páginas por completo
		return new Filter[] {encodingFilter, new OpenEntityManagerInViewFilter()};
	}
	
	
	// Método para registrar a configuração de um MultiPartConfig para o envio de arquivo
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	
	// Necessário comentar para execução no Heroku
	/*
	// Método para configurar o profile de inicialização
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		// Adicionamos um ouvinte no contexto da requisição
		servletContext.addListener(new RequestContextListener());
		// Definição do profile a ser usado
		servletContext.setInitParameter("spring.profiles.active", "dev");
		
	}
	*/
	
	
	
	

}
