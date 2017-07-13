package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {
	
	// Variável para extrair o contexto atual e descobrir o caminho absoluto do diretório no computador
	@Autowired
	private HttpServletRequest request;
	
	

	// Método para realizar a transferência do arquivo e retornar o caminho onde foi armazenado no computador.
	public String writer(String baseFolder, MultipartFile file){
		try {
			
			// Pegando o caminho completo no servidor
			//String realPath = request.getServletContext().getRealPath("/")+baseFolder;
			String realPath = "C:\\estudos\\alura\\spring\\workspace\\casadocodigo\\src\\main\\webapp\\arquivos-sumario";
			
			// Setando o path com o caminho completo e o nome do arquivo
			String path = realPath + "\\" + file.getName();
			
			// Efetuando a transferência baseado no contexto do realPath
			file.transferTo(new File(path));
			
			// Retorna a pasta base com o nome do arquivo
			return baseFolder + "/" + file.getOriginalFilename();
			
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		} 
	}
}
