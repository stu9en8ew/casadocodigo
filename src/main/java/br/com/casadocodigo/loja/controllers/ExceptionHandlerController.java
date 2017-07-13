package br.com.casadocodigo.loja.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// Criação de controller para tratamento genérico de exceções no escopo da aplicação


@ControllerAdvice
public class ExceptionHandlerController {
   
	@ExceptionHandler(Exception.class)
    public ModelAndView trataExceptionGenerica(Exception exception){
    
		System.out.println("Erro genérico acontecendo");
        exception.printStackTrace();

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
	
}
