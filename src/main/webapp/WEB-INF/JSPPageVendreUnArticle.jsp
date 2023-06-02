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

	<%@ include file="/WEB-INF/jsp/JSPHeader.jsp" %>

	<h1>Vendre un article</h1>

	<form action="NomDelaServlet" method="post">
		<label for="article">Article:</label>
		<input type="text" id="article" name="article" required><br>
		<br> 
		
		<label for="description">Description:</label><br>
		<textarea id="description" name="description" rows="4" cols="50"></textarea>
		<br> 
		<label for="categorie">Catégorie:</label> 
		
		<select
			id="categorie" name="categorie">
			<option value="categorie1">Catégorie 1</option>
			<option value="categorie2">Catégorie 2</option>
			<option value="categorie3">Catégorie 3</option>
		</select>
		<br> 
		<label for="photo">Photo de l'article:</label> 
		
		<input
			type="file" id="photo" name="photo"><br>
		<br> 
		
		<label for="offre">Mise à prix :</label>
		<input type="text" id="offre">
		<br>			
		</select>
		
	
		
		<br> 
		<label for="debutenchere">Début de l'enchère:</label> 
		<input
			type="date" id="debutenchere" name="debutenchere"><br>
		<br> 
		<label for="finenchere">Fin de l'enchère:</label> 
		<input
			type="date" id="finenchere" name="finenchere"><br>
		<br> 
		
		<fieldset>
		
			<legend>Adresse de retrait</legend>
		
				<label for="rue">Rue :</label>
				<input type="text" id="rue">
				<br>
				
				<label for="CP">Code postal :</label>
				<input type="text" id="CP">
				<br>
				<label for="ville">Ville :</label>
				<input type="text" id="ville">
				<br>
		
		</fieldset>
		
		
		<input type="submit" value="Enregistrer"> 
		<input
			type="reset" value="Annuler">
	</form>

	<%@ include file="/WEB-INF/jsp/JSPFooter.jsp" %>

</body>
</html>