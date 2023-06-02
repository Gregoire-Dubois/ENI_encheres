<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- A SUPPRIMER -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />

<title>Connexion</title>
</head>
<body>

	<%@ include file="JSPHeader.jsp" %>
	
	<body>
	    <h1>Formulaire de Connexion</h1>
	
	    <form action="NomDelaServlet" method="post">
	        <label for="identifiant">Identifiant :</label>
	        <input type="text" id="identifiant" name="identifiant" required><br>
	
	        <label for="mot-de-passe">Mot de passe :</label>
	        <input type="password" id="mot-de-passe" name="mot-de-passe" required><br>
	
	        <input type="checkbox" id="se-souvenir-de-moi" name="se-souvenir-de-moi">
	        <label for="se-souvenir-de-moi">Se souvenir de moi</label><br>
	
	        <input type="submit" value="Connexion">
	    </form>
	
	    <a href="mot-de-passe-oublie.html">Mot de passe oublié</a><br>
	    <button onclick="location.href='creer-compte.html'">Créer un compte</button>
	</body>
	</html>

	<%@ include file="JSPFooter.jsp" %>
	

</body>
</html>