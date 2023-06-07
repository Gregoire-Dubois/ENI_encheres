<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
    
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

		<div class="conteneurdetails3">
			<div class="titreDetails3">
				<h1>détail de ma vente à jojo (à dynamiser)</h1>
			</div>

			<div class="titreProduit3">
				<p>Titre du produit : ${details.getNomArticle()}</p>
			</div>



			<div class="descriptionProduit3">
				<p>Description :</p>
			</div>

			<div class="miseAPrixProduit3">
				<p>Mise à prix :</p>
			</div>

			<div class="meilleurOffreProduit3">
				<p>Meilleur offre produit :</p>
			</div>

			<div class="finEnchereProduit3">
				<p>Fin enchere produit :</p>

			</div>

			<div class="retraitProduit3">
				<p>Retrait Produit :</p>
			</div>

			<div class="vendeurProduit3">
				<p>Vendeur :</p>
			</div>

		</div>
	</div>
	
	<%@ include file="JSPFooter.jsp"%>
	

</body>
</html>