<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- Import da taglib de templates de JSP -->
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<!-- Uso da taglib de templates de JSP -->
<tags:pageTemplate titulo="Livros de Java, Android, iOS, Mobile e muito mais">
	
	<!-- Uso do recurso de fragmento do JSP -->
	<jsp:attribute name="extraScripts">
		<script>console.log("Finalização de compra de ${carrinhoCompras.quantidade} itens");</script>
	</jsp:attribute>

	<!-- Uso da tag jsp:body para qualificar o uso de fragments -->
	<jsp:body>
		<section class="container middle">
			  <h2 id="cart-title">Seu carrinho de compras</h2>
	 
			    <table id="cart-table">
			      <colgroup>
			        <col class="item-col"/>
			        <col class="item-price-col"/>
			        <col class="item-quantity-col"/>
			        <col class="line-price-col"/>
			        <col class="delete-col"/>
			      </colgroup>
			      <thead>
			        <tr>
			          <th class="cart-img-col"></th>
			          <th width="65%">Item</th>
			          <th width="10%">Preço</th>
			          <th width="10%">Quantidade</th>
			          <th width="10%">Total</th>
			          <th width="5%"></th>
			        </tr>
			      </thead>
			      <tbody>
			      	<!-- Iteração em cima da lista de itens tratada como Collection na classe @Componente do Spring MVC -->
			      	<c:forEach items="${carrinhoCompras.itens}" var="item">
				      <tr>
				          <td class="cart-img-col"><img src="http:////cdn.shopify.com/s/files/1/0155/7645/products/css-eficiente-featured_large.png?v=1435245145" width="71px" height="100px"/></td>
				          
				          <!-- Retorna o título do produto -->
				          <td class="item-title">${item.produto.titulo}</td>
				          
				          <!-- Retorna o preço do produto -->
				          <td class="numeric-cell">${item.preco}</td>
				          
				          <!-- Retorna a quantidade do item -->
				          <td class="quantity-input-cell"><input type="number" min="0" readonly="readonly" id="quantidade" 
				          name="quantidade" value="${carrinhoCompras.getQuantidade(item)}"/></td>
				          
				          <!--Retorna o total da soma dos preços daquele produto específico  -->
				          <td class="numeric-cell">${carrinhoCompras.getTotal(item)}</td>
				          
				          <!-- Uso de um input image para exclusão de itens do carrinho de compras -->
				          <td class="remove-item">
					          <form:form action="${s:mvcUrl('CCC#remover').arg(0,item.produto.id).arg(1,item.tipoPreco).build()}" method="post">
						          <input type="image" src="${contextPath }resources/imagens/excluir.png" alt="Excluir" title="Excluir" />
					          </form:form>
				          </td>
				          
				      </tr>
			      	</c:forEach>
			      </tbody>
				      <tfoot>
				        <tr>
				          <td colspan="3">
					          <form:form action="${s:mvcUrl('PC#finalizar').build()}" method="post">
					          	<input type="submit" class="checkout" name="checkout" value="Finalizar compra" />
					          </form:form>
				          </td>
				          <td class="numeric-cell">${carrinhoCompras.total()}</td><td></td>
				        </tr>
				      </tfoot>
			    </table>
			  
			  <h2>Você já conhece os outros livros da Casa do Código?</h2>
			  <ul id="collection" class="related-books">          
			      <li class="col-left">
			        <a href="/products/livro-plsql" class="block clearfix book-suggest" data-book="PL/SQL: Domine a linguagem do banco de dados Oracle">
			          <img width="113px" height="160px" src="http:////cdn.shopify.com/s/files/1/0155/7645/products/plsql-featured_compact.png?v=1434740236" alt="PL/SQL: Domine a linguagem do banco de dados Oracle"/>
			        </a>
			      </li>          
			  </ul>
			  
			  <h2><a href="http://www.casadocodigo.com.br">Veja todos os livros que publicamos!</a></h2>
			</section>
		</jsp:body> 

</tags:pageTemplate>
