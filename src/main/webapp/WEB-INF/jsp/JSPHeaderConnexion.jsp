<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/style.css" />
<title>Insert title here</title>
</head>
<body>
<header>
	<c:choose>
		<c:when test="${userConnected != null}"><!-- Si l'utilisateur est connecte -->
			<p><!-- Mise en place d'une liste possible -->
				<a href="${pageContext.request.contextPath}/accueilConnected">Enchères</a>
				<a href="${pageContext.request.contextPath}/vendre">Vendre un article</a>
				<a href="${pageContext.request.contextPath}/profil?pseudo=${userConnected.getPseudo()}">Mon Profil</a>
				<a href="${pageContext.request.contextPath}/deconnexion">Déconnexion</a>
			</p>
		</c:when>
		<c:otherwise><!-- Si l'utilisateur n'est pas connecte -->
			<p>
				<a href="${pageContext.request.contextPath}/connexion">Se connecter - S'inscrire</a>
			</p>
		</c:otherwise>


	</c:choose>
</header>
</body>
</html>