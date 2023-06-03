<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Connexion - Inscription</title>
</head>

<body>

<%@ include file="JSPHeader.jsp" %>

<section>
	<c:if test="${!empty listeErreurs}">
		<c:forEach var="code" items="${listeErreurs}">
			<li>Erreur: ${LecteurMessage.getMessageErreur(code)}</li>
		</c:forEach>
	</c:if>
	<form method="post" action="${pageContext.request.contextPath}/connexion">
		<p>
			<label for="identifiant">Identifiant :</label> 
			<input type="text" name="identifiant" id="identifiant" value="${identifiant}" required> 
			</br> </br>
			<label for="mdp">Mot de passe:</label> 
			<input type="password" name="mdp" id="mdp" value="${mdp}" required>
			</br> 
			<input type="checkbox" id="se-souvenir-de-moi" name="se-souvenir-de-moi">
	        <label for="se-souvenir-de-moi">Se souvenir de moi</label>
	        <br>
			
			<input type="submit" value="Connexion">

		</p>


	</form>
	
	<a href="${pageContext.request.contextPath}/inscription"><input type="submit"value="Créer un compte"></a>
	<a href="mot-de-passe-oublie.html">Mot de passe oublié</a><br>
	   <!--  <button onclick="location.href='creer-compte.html'">Créer un compte</button> --> <!-- ???? -->
</section>
<%@ include file="JSPFooter.jsp" %>
</body>
</html>