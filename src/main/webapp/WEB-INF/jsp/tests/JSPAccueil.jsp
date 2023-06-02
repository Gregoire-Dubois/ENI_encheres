<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- OK A SUPPRIMER -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Accueil</title>
</head>
<body>

<header>
<h1>ENI-Encheres</h1>



<c:choose>
<c:when test="${userConnected != null}"><!-- Si l'utilisateur est connecte -->
<p>
<a href="">Enchères</a>
<a href="">Vendre un article</a>
<a href="">Mon Profil</a>
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

<section>
<h2>Liste des enchères</h2>

<!-- A terminer avec les éléments articles et catégories -->

</section>



<footer>

</footer>
</body>
</html>