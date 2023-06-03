<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<form action="NomDelaServlet" method="post">
		<label for="article">Article:</label> <input type="text" id="article"
			name="article" required><br> <br> <label
			for="description">Description:</label><br>
		<textarea id="description" name="description" rows="4" cols="50"></textarea>
		<p>
		<label for="categorie">Catégorie:</label>

		<select name="categorie" id="categorie">
			<!-- <option value="">Choix</option>-->
			<c:forEach items="${listeCategorie}" var="categorie">
				<option value="${categorie.libelle}"
					${categorie.libelle == article.categorie.libelle ? 'selected' : ''}>${categorie.libelle}</option>
			</c:forEach>
		</select>
		<p> 
		<label for="photo">Photo de l'article:</label> <input
			type="file" id="photo" name="photo"><br> <br> <label
			for="offre">Mise à prix :</label> <input type="text" id="offre">
		<br> </select> <br> <label for="debutenchere">Début de
			l'enchère:</label> <input type="date" id="debutenchere" name="debutenchere"><br>
		<br> <label for="finenchere">Fin de l'enchère:</label> <input
			type="date" id="finenchere" name="finenchere"><br> <br>

		<fieldset>

			<legend>Adresse de retrait</legend>

			<label for="rue">Rue :</label> <input type="text" id="rue"> <br>

			<label for="CP">Code postal :</label> <input type="text" id="CP">
			<br> <label for="ville">Ville :</label> <input type="text"
				id="ville"> <br>

		</fieldset>


		<input type="submit" value="Enregistrer"> <input type="reset"
			value="Annuler">
	</form>

	<%@ include file="JSPFooter.jsp"%>

</body>
</html>