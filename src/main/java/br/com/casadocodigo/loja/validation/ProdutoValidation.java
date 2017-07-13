package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;

// Classe de validação deve implementar a interface Validator do Spring sobrescrevendo os métodos supports e validate
public class ProdutoValidation implements Validator {

	// Método que verifica se o objeto recebido para a validação tem a assinatura da classe Produto
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	// Método que efetua a validação dos campos
	// O parâmetro target representa o alvo da validação
	// O parâmetro errors efetua o tratamento das mensagens de erro
	@Override
	public void validate(Object target, Errors errors) {
		
		// Uso da classe ValidationUtils do Spring para validação de dados
		// Uso do método rejectIfEmpty para rejeitar se um campo estiver vazio com três paramêtros
		// onde: errors - contém os erros de validação; field - indica o nome do campo que vamos validar e errorCode - usado
		// para informar a obrigatoriedade de preenchimento do campo
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		//ValidationUtils.rejectIfEmpty(errors, "dataLancamento", "field.required");
		
		Produto produto = (Produto) target;
		
		if (produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "field.required");
		}
		
	}

}
