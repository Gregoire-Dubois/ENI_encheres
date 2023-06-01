<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion - Inscription</title>
</head>
<body>
<header>
<h1>ENI-Encheres</h1>
</header>
<section>
	<c:if test="${!empty listeErreurs}">
		<c:forEach var="code" items="${listeErreurs}">
			<li>Erreur: ${LecteurMessage.getMessageErreur(code)}</li>
		</c:forEach>
	</c:if>
	<form method="post" action="${pageContext.request.contextPath}/connexion">
		<p>
			<label for="identifiant">Identifiant :</label> <input type="text" name="identifiant" id="identifiant" required> </br> </br>
			<label for="mdp">Mot de passe:</label> <input type="password" name="mdp" id="mdp" required> 
			
			<input type="submit" value="Connexion">

		</p>


	</form>
	
	<a href="${pageContext.request.contextPath}/inscription"><input type="submit"value="Créer un compte"></a>
</section>
</body>
</html>