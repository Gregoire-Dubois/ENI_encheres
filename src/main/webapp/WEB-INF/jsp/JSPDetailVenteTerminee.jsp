<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
<%@ page import="fr.eni.enienchere.bo.Utilisateur" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%@ include file="JSPHeader.jsp"%>


	<div class="mainconteneurDetail">
		<div class="photoDetails" class="fit-picture" src="#" alt="photo">
			<img class="photo" alt="une photo"
				src="https://www.leparisien.fr/resizer/PQ6kjwN7VTSmYq9mzk3zHvEu0LY=/932x582/cloudfront-eu-central-1.images.arcpublishing.com/lpguideshopping/A45LFCGVEVBPBNIGSRUKV3AWFM.jpg">

		</div>
		
		<% Utilisateur encherisseur = (Utilisateur) session.getAttribute("encherisseur"); %>
		
		<% if (encherisseur.getPseudo() != null) { %>
    		<h1><%= encherisseur.getPseudo() %> a remporté la vente</h1>
		<% } %>


		<div class="conteneurdetails3">


			<div class="titreProduit3">
				<p>${details.getNomArticle()}</p>
			</div>

			<div class="descriptionProduit3">
				<p>Description : ${details.getDescription()}</p>
			</div>

			<div class="meilleurOffreProduit3">
				<p>Meilleur offre : ${details.getPrixVente()} points</p>
			</div>
			
			<div class="miseAPrixProduit3">
				<p>Mise à prix : ${details.getPrixInitial()} points</p>
			</div>

			<div class="retraitProduit3">
				<p>Retrait : ${details.getRetrait().getRue()}</p>
				<p>          ${details.getRetrait().getCodePostal()}</p>
				<p>          ${details.getRetrait().getVille()}</p>
			</div>
			<div class="vendeurProduit">
				<p>Vendeur : ${details.getVendeur().getPseudo()}</p>
			</div>

		</div>
	</div>
	
	<%@ include file="JSPFooter.jsp"%>
	

</body>
</html>