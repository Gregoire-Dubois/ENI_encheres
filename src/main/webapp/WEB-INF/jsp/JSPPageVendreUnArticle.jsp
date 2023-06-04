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
	
	<c:if test="${!empty listeErreursAjoutArticle }">
		<c:forEach var="code" items="${listeErreursAjoutArticle}">
			<li>Erreur: ${LecteurMessage.getMessageErreur(code)}</li>
		</c:forEach>
	</c:if>

	<form action="${pageContext.request.contextPath}/vendre" method="post">
		<label for="article">Article:</label> 
		<input type="text" id="article" name="article" required><br> <br> 
		
		<label for="description">Description:</label><br>
		<textarea id="description" name="description" rows="4" cols="50" maxlength="300"></textarea>
		<p>
		<label for="categorie">Catégorie:</label>

		<select name="categorie" id="categorie">
			<!-- <option value="">Choix</option>-->
			<c:forEach items="${listeCategorie}" var="cat">
				<option value="${cat.id}"
					${cat.libelle == article.cat.libelle ? 'selected' : ''}> ${cat.libelle} 
				</option>
			</c:forEach>
		</select>
		<p> 
		<label for="photo">Photo de l'article:</label> 
		<input type="file" id="photo" name="photo"><br> <br> 
			
		<label for="prixDepart">Mise à prix :</label> 
		<input type="number" id="prixDepart" name="prixDepart">
		
		<br> 
		<label for="debutEnchere">Début de l'enchère:</label> 
		<input type="date" id="debutEnchere" name="debutEnchere"><br>
		<br> 
		<label for="finEnchere">Fin de l'enchère:</label> 
		<input type="date" id="finEnchere" name="finEnchere"><br> <br>

		<fieldset>

			<legend>Adresse de retrait</legend>

			<label for="rue">Rue :</label> <input type="text" id="rue" name="rue" value="${userConnected.rue}"> <br>

			<label for="codePostal">Code postal :</label> <input type="text" id="codePostal" name="codePostal" pattern="[0-9]{5}" value="${userConnected.codePostal}">
			<br> <label for="ville">Ville :</label> <input type="text" id="ville" name="ville" value="${userConnected.ville}"> <br>

		</fieldset>


		<input type="submit" value="Enregistrer"> <input type="reset"
			value="Annuler">
	</form>

	<%@ include file="JSPFooter.jsp"%>

</body>
</html>