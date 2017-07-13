package br.com.casadocodigo.loja.conf;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JSonViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		
		// Uso do Jackson para lidar com o formato JSON
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		
		// Definição de formatação amigável com PrettyPrint
		jsonView.setPrettyPrint(true);
		
		return jsonView;
		
	}

}
