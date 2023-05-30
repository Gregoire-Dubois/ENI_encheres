<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion - Inscription</title>
</head>
<body>
	<h1>PAGE DE CONNEXION</h1>
	<form method="post" action="${pageContext.request.contextPath}/connexion">
		<p>
			<label for="identifiant">Identifiant :</label> <input type="text" name="identifiant" id="identifiant"> </br> </br>
			<label for="mdp">Mot de passe:</label> <input type="password" name="mdp" id="mdp"> 
			
			<input type="submit" value="Connexion">

		</p>


	</form>
</body>
</html>