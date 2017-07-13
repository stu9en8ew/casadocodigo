package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager mananger;
	
	public void gravar(Produto produto){
		mananger.persist(produto);
	}

	public List<Produto> listar() {
		return mananger.createQuery("select distinct (p) from Produto p join fetch p.precos", Produto.class)
				.getResultList();		
	}

	// Método para buscar um produto por ID
	public Produto find(Integer id) {
		// Montagem de uma query planejada considerando o relacionamento entre Produto e Preco com fetch join
		Produto produto = mananger.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos preco where p.id=:id", Produto.class).setParameter("id", id).getSingleResult();
		// Retorno do produto
		return produto;
	}
	
	// Método para efetuar o somatório de produtos por tipo
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco){
		TypedQuery<BigDecimal> query = mananger.createQuery("select sum(preco.valor) from Produto p join p.precos preco"
				+ " where preco.tipo = :tipoPreco", BigDecimal.class)
				.setParameter("tipoPreco", tipoPreco);
		
		return query.getSingleResult();
	}
}
