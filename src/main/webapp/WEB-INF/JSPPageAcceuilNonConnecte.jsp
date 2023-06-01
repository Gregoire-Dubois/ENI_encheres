<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />



<title>Accueil</title>
</head>
<body>

	<-- la première carte est à supprimer elle sert de modèle de présentation -->
	<%@ include file="JSPHeaderNonConnecte.jsp" %>


	<div class="grilleNonConnecte">
		<div class="titreNonConnecte">
			<h1>Liste des enchères</h1>
		</div>

		<div class="filtresNonConnecte">
			<h3>Filtre</h3>

			<form>

				<input type="text" name="search"
					placeholder="Le nom de l'article contient"> <select>
					<option value="option1">Option 1</option>
					<option value="option2">Option 2</option>
					<option value="option3">Option 3</option>
				</select> <input type="submit" value="Rechercher">
			</form>


		</div>

		<div class="resultatsNonConnecte">

			<div class="card">
				<img
					src="https://static.fnac-static.com/multimedia/Images/FR/MDM/24/35/1f/18822436/1540-1/tsp20230519135127/The-Legend-of-Zelda-Tears-Of-The-Kingdom-Nintendo-Switch.jpg"
					alt="Image de la card">
				<div class="card-content">
					<h2>Zelda: Tears Of The Kingdom</h2>
					<p>Prix :</p>
					<p>Fin de l'enchère :</p>
					<p>Vendeur :</p>
				</div>
			</div>

		</div>

	</div>
	
	
	
	
		<div class="resultatsNonConnecte">

			<div class="card">
					<c:forEach var="v" items="${produit}">
			
				<img
					src=""
					alt="Image de la card">
				<div class="card-content">
					<h2>${v.nomArticle}</h2>
					<p>Prix : ${v.montantEnchere}</p>
					<p>Fin de l'enchère : ${v.dateFinEnchere}</p>
					<p>Vendeur : ${v.nom}</p>
				</div>
					</c:forEach>		
				
			</div>

		</div>
	
	<%@ include file="JSPFooter.jsp" %>


</body>
</html>