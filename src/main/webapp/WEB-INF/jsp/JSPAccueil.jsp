<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Accueil</title>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">


</head>
<body>

	<%@ include file="JSPHeader.jsp"%>

	<%@ include file="JSPHeaderConnexion.jsp"%>


	<section>





			<h1>Liste des enchères</h1>
			<%@ include file="tests/FiltreArticles.jsp"%>



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

			<c:when test="${empty listeErreurs}">
				<div class="resultatsNonConnecte">

					
						<c:forEach var="v" items="${articles}">
					<div class="card">
							<img src="#" alt="Image de la card">
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