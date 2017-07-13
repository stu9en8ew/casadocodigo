<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código.</title>

<!-- Uso do framework front end bootstrap -->
<c:url value="/resources/css" var="cssPath"/>
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" href="${cssPath}/bootstrap-theme.min.css">

</head>
<body>
	<!-- Uso do framework bootstrap com menu para navegação -->
	<nav class="navbar navbar-inverse">
	  <div class="container">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">Casa do Código</a>
	    </div>
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li><a href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos</a></li>
	        <li><a href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a></li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	      	<li><a href="#"><security:authentication property="principal" var="usuario"/>Usuário: ${usuario.username}</a></li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div>
	</nav>

	<!-- Uso do framework front bootstrap - tag div class container -->
	<div class="container">
		<h2>Listagem de Produtos</h2>
			<p>${sucesso}</p>
			<p>${falha}</p>
			
			<table class="table table-bordered table-striped table-hover">
				<tr>
					<th>Título</th>
					<th>Descrição</th>
					<th>Páginas</th>
					<th>Preço</th>
					<th>Data de Lançamento</th>
				</tr>
				<c:forEach items="${produtos}" var="produto">
					<tr>
						<td><a href="${s:mvcUrl('PC#detalhe').arg(0,produto.id).build()}">${produto.titulo}</a></td>
						<td>${produto.descricao}</td>
						<td>${produto.paginas}</td>
						<td>${produto.precos}</td>
						<td><fmt:formatDate value="${produto.dataLancamento.time}" type="date" pattern="dd/MM/yyyy"/></td>
					</tr>
				</c:forEach>
			</table>
	</div>	

</body>

</html>