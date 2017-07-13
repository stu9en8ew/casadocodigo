package br.com.casacodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.casadocodigo.loja.confs.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import junit.framework.Assert;


// Indicação do uso do spring-teste em conjunto com o junit
@RunWith(SpringJUnit4ClassRunner.class)
// Classe de configuração para execução do teste
@ContextConfiguration(classes={JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDAOTest {
	
	@Autowired
	private ProdutoDAO produtoDao;

	@Test
	@Transactional
	public void deveSomarTodosOsPrecosPorTipoLivro(){
		
		// Preenchimento da lista de impressos com a classe ProdutoBuilder
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3).buildAll();
		
		// Preenchimento da lista de ebook com a classe ProdutoBuilder
		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3).buildAll();
		
		// Efetuando a iteração para salvar no banco os livros impressos com Java 8
		livrosImpressos.stream().forEach(produtoDao::gravar);
		
		// Efetuando a iteração para salvar no banco os livros ebook com Java 8
		livrosEbook.stream().forEach(produtoDao::gravar);
		
		//Efetuando o teste
		BigDecimal valor = produtoDao.somaPrecosPorTipo(TipoPreco.EBOOK);
		Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
		
		
	}
}
