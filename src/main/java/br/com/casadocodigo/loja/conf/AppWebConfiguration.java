package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

@EnableWebMvc

@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class})

// Habilitando o uso de cache no Spring
@EnableCaching 

public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		// Expondo atributos para o jsp
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		return resolver;
	}
	

	// Método para carregar o arquivo de mensagens
	@Bean
	public MessageSource messageSource(){
		
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		// Definindo o nome base dos resources
		messageSource.setBasename("/WEB-INF/messages");
		// Definindo o enconding
		messageSource.setDefaultEncoding("UTF-8");
		// Definindo para que o Spring recarregue o arquivo
		messageSource.setCacheSeconds(1);
		
		return messageSource;
		
	}
	
	// Método para configurar o uso de formato de datas
	@Bean
	public FormattingConversionService mvcConversionService(){
		
		// Definindo um objeto responsável pelo serviço de conversão de formato
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// Objeto para registar o formato usado para a conversão
		DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
		// Definição do formato com o uso do DateFormatter
		formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		// Registrando o padrão no serviço de conversão
		formatterRegistrar.registerFormatters(conversionService);
		
		return conversionService;
		
	}
	
	
	// Método para configurar um MultiPartResolver(resolvedor de dados multimídia) para o envio de arquivo
	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}
	
	// Método para configurar a criação de um objeto RestTemplate pelo Spring
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	// Método para prover recursos de cache pelo Spring
	// Substituição do ConcurrentMapCacheManager pelo Guava
	@Bean
	public CacheManager cacheManager(){
		//return new ConcurrentMapCacheManager();
		
		CacheBuilder<Object,Object> cacheBuilder = CacheBuilder
				.newBuilder()
				.maximumSize(100) // Tamanho máximo do cache
				.expireAfterAccess(5, TimeUnit.MINUTES); // Expirar após um determinando tempo
		
		GuavaCacheManager manager = new GuavaCacheManager();
		
		manager.setCacheBuilder(cacheBuilder);
		
		return manager;
	}
	
	
	// Método para configurar o recurso de Content Negotiation
	@Bean
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager){
		
		// Definindo uma lista para adicionar os possíveis formatos de conteúdo do retorno
		List<ViewResolver> viewResolvers = new ArrayList<>();
		
		// Adicionando o formato de conteúdo: HTML
		viewResolvers.add(internalResourceViewResolver());
		
		// Adicionando o formato de conteúdo: JSON
		viewResolvers.add(new JSonViewResolver());
		
		// Criando a instância do resolver
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		
		// Indicando ao resolver os formatos de viewResolver
		resolver.setViewResolvers(viewResolvers);
		
		// Setando o manager que gerencia o conteúdo de retorno
		resolver.setContentNegotiationManager(manager);
		
		// Retorno do resolver
		return resolver;
		
	}
	
	// Método para definir um servlet padrão que atenda as requisições de css/js
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}
	
	// Método para criar um interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	// Método para resolver o locale com cookie
	@Bean
	public LocaleResolver localeResolver(){
		return new CookieLocaleResolver();
	}
	
	
	// Método para configuração de envio de email
	@Bean
	public MailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("alura.springmvc@gmail.com");
		mailSender.setPassword("alura2015");
		mailSender.setPort(587);
		
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}
	
	
}
