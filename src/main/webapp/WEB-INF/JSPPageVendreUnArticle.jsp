<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />

<title>Vendre un article</title>
</head>
<body>



	<%@ include file="JSPHeader.jsp"%>

	<h1>Vendre un article</h1>


	<form action="traitement_formulaire.php" method="post">
		<label for="article">Article:</label> <input type="text" id="article"
			name="article" required><br>
		<br> <label for="description">Description:</label><br>
		<textarea id="description" name="description" rows="4" cols="50"></textarea>
		<br>
		<br> <label for="categorie">Catégorie:</label> <select
			id="categorie" name="categorie">
			<option value="categorie1">Catégorie 1</option>
			<option value="categorie2">Catégorie 2</option>
			<option value="categorie3">Catégorie 3</option>
		</select><br>
		<br> <label for="photo">Photo de l'article:</label> <input
			type="file" id="photo" name="photo"><br>
		<br> <label for="miseaprix">Mise à prix:</label> <select
			id="miseaprix" name="miseaprix">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<!-- Ajouter d'autres options si nécessaire -->
		</select><br>
		<br> <label for="debutenchere">Début de l'enchère:</label> <input
			type="date" id="debutenchere" name="debutenchere"><br>
		<br> <label for="finenchere">Fin de l'enchère:</label> <input
			type="date" id="finenchere" name="finenchere"><br>
		<br> <input type="submit" value="Enregistrer"> <input
			type="reset" value="Annuler">
	</form>



	<%@ include file="JSPFooter.jsp"%>


</body>
</html>