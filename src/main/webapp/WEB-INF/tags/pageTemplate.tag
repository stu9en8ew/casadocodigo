<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="titulo" required="true"%>
<!-- Acr�scimo de atributos ao template-->
<%@ attribute name="bodyClass" required="false"%>
<!-- Uso do recurso de fragmentos -->
<%@ attribute name="extraScripts" fragment="true" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
	<link rel="icon"
		href="//cdn.shopify.com/s/files/1/0155/7645/t/177/assets/favicon.ico?11981592617154272979"
		type="image/ico" />
	<link href="https://plus.googlecom/108540024862647200608"
		rel="publisher"/>
	<title>${titulo} - Casa do C�digo</title>
	<link href="${contextPath}resources/css/cssbase-min.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700'
		rel='stylesheet'/>
	<link href="${contextPath}resources/css/fonts.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello-ie7.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello-embedded.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/style.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/layout-colors.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/responsive-style.css"
		rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/guia-do-programador-style.css" 
	rel="stylesheet" type="text/css"  media="all"  />
	  <link href="${contextPath}resources/css/produtos.css" 
	   	rel="stylesheet" type="text/css"  media="all"  />
	<link rel="canonical" href="http://www.casadocodigo.com.br/" />	
</head>
<body class="bodyClass">


<%@include file="/WEB-INF/views/cabecalho.jsp"%>

<!-- conte�do da p�gina -->
<!-- Uso da tag jsp:body para indicar o local em que o conte�do ser� inserido com o uso dos templates da JSP -->
<jsp:doBody/>

<%@include file="/WEB-INF/views/rodape.jsp" %>

<!-- Utiliza��o do recurso de fragmentos da especifica��o de JSP -->
<jsp:invoke fragment="extraScripts"></jsp:invoke>


</body>
</html>
