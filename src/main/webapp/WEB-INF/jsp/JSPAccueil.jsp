<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Accueil</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
</head>
<body>

<%@ include file="JSPHeader.jsp" %>


<%@ include file="JSPHeaderConnexion.jsp" %>

<%@ include file="tests/FiltreArticles.jsp" %>


<section>

<!-- A terminer avec les éléments articles et catégories  selon connecte ou pas-->

<!-- la première carte est à supprimer elle sert de modèle de présentation -->
	<!-- <div class="grilleNonConnecte">
		<div class="titreNonConnecte"> -->
			<h1>Liste des enchères</h1>
		<!-- </div> -->
	
		<!-- <div class="filtresNonConnecte"> -->
		<div>
			<h3>Filtre</h3>
	
			<form>
	
				<input type="text" name="search" placeholder="Le nom de l'article contient"> 
				<br>
				<label for="categorie">Catégorie:</label>
					<select name="categorie" id="categorie">
						<!-- <option value="">Choix</option>-->
				<c:forEach items="${listeCategorie}" var="categorie">
					<option value="${categorie.libelle}"${categorie.libelle == article.categorie.libelle ? 'selected' : ''}>${categorie.libelle}</option>
				</c:forEach>
		</select>
				<!-- <select name="categorie" id="categorie">
					<option value="categorie1" >Catégorie 1</option>
					<option value="categorie2" >Catégorie 2</option>
					<option value="categorie3" >Catégorie 3</option>
				</select>--> <br>
				<input type="submit" value="Rechercher">
			</form>
	
	
		</div> <!-- OK POUR TOUT LE MONDE JUSQU'ICI-->
		
		<!-- Il faut dynamiser -->
		
		<c:choose>
		<c:when test="${userConnected != null}"><!-- Si l'utilisateur est connecte -->
		<div class="resultatsNonConnecte">
	
			<div class="card">
				<img
					src="https://static.fnac-static.com/multimedia/Images/FR/MDM/24/35/1f/18822436/1540-1/tsp20230519135127/The-Legend-of-Zelda-Tears-Of-The-Kingdom-Nintendo-Switch.jpg"
					alt="Image de la card">
				<div class="card-content">
					<h2><a href="">${article.nomArticle}</a></h2> <!--  Exemple pour se repérer les expressions ne correspondent à rien pour le moment -->
					
					<p>Prix :${article.prixVente}</p> <!--  Exemple pour se repérer les expressions ne correspondent à rien pour le moment -->
					<p>Fin de l'enchère :${article.dateFinEncheres}</p> <!--  Exemple pour se repérer les expressions ne correspondent à rien pour le moment -->
					<p><a href="${pageContext.request.contextPath}/profil?idVendeur=${article.utilisateur.getNoUtilisateur}">Vendeur : ${article.utilisateur.getPseudo}</a></p> <!--  Exemple pour se repérer les expressions ne correspondent à rien pour le moment -->
					<!-- <p><a href="${pageContext.request.contextPath}/profil?idVendeur=1">Vendeur : ${article.utilisateur.getPseudo}</a></p> --> <!-- Pour l'exemple -->
				</div>
			</div>
	
		</div>
		</c:when>
		<c:otherwise><!-- Si l'utilisateur n'est pas connecte -->
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
		</c:otherwise>


	</c:choose>
	
	
	
	
	
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
	
</section>
	
<%@ include file="JSPFooter.jsp" %>	



</body>
</html>