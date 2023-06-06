<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Accueil</title>

<<<<<<< HEAD
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
=======

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
>>>>>>> branch 'master' of git@bitbucket.org:ccrepin/eni_encheres.git

<<<<<<< HEAD
<link rel="stylesheet" href="../css/style.css">


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
=======
>>>>>>> branch 'master' of git@bitbucket.org:ccrepin/eni_encheres.git

</head>
<body>

	<%@ include file="JSPHeader.jsp"%>

	<%@ include file="JSPHeaderConnexion.jsp"%>


	<section>


<<<<<<< HEAD
		<h1>Liste des enchères</h1>
=======



>>>>>>> branch 'master' of git@bitbucket.org:ccrepin/eni_encheres.git
			<h1>Liste des enchères</h1>
			<%@ include file="tests/FiltreArticles.jsp"%>
<<<<<<< HEAD


		<!-- </div> -->

		<!-- <div class="filtresNonConnecte"> -->
		<div>
			<h3>Filtre</h3>

			<form>

				<input type="text" name="search"
					placeholder="Le nom de l'article contient"> <br> <label
					for="categorie">Catégorie:</label> <select name="categorie"
					id="categorie">
					<!-- <option value="">Choix</option>-->
					<c:forEach items="${listeCategorie}" var="categorie">
						<option value="${categorie.libelle}"
							${categorie.libelle == article.categorie.libelle ? 'selected' : ''}>${categorie.libelle}</option>
					</c:forEach>
				</select>
				<!-- <select name="categorie" id="categorie">
					<option value="categorie1" >Catégorie 1</option>
					<option value="categorie2" >Catégorie 2</option>
					<option value="categorie3" >Catégorie 3</option>
				</select>-->
				<br> <input type="submit" value="Rechercher">
			</form>
=======
>>>>>>> branch 'master' of git@bitbucket.org:ccrepin/eni_encheres.git



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

		<c:choose>

<<<<<<< HEAD
				<a href="ServletTestEncherirAcquisitionDetailMAventeFinEnchere">
					<div class="resultatsNonConnecte">
						<div class="card">
							<img
								src="https://static.fnac-static.com/multimedia/Images/FR/MDM/24/35/1f/18822436/1540-1/tsp20230519135127/The-Legend-of-Zelda-Tears-Of-The-Kingdom-Nintendo-Switch.jpg"
								alt="Image de la card">
=======
			<c:when test="${empty listeErreurs}">
				<div class="resultatsNonConnecte">

					
						<c:forEach var="v" items="${articles}">
					<div class="card">
							<img src="#" alt="Image de la card">
>>>>>>> branch 'master' of git@bitbucket.org:ccrepin/eni_encheres.git
							<div class="card-content">
								<h2>${v.nomArticle}</h2>
								<p>Prix : ${v.prixVente}</p>
								<p>Fin de l'enchère : ${v.dateFinEncheres}</p>
								<p>Vendeur : ${v.vendeur.pseudo}</p>

							</div>
					</div>
						</c:forEach>
					
				</div>

			</c:when>

			<c:otherwise>

				<c:forEach var="e" items="${listeErreurs}">
					<p>${LecteurMessage.getMessageErreur(e)}</p>
				</c:forEach>
			
			</c:otherwise>

		</c:choose>
		
		</c:otherwise>
		
		</c:choose>



	</section>

	<%@ include file="JSPFooter.jsp"%>


</body>
</html>