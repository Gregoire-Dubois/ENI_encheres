<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acquisitions</title>
</head>
<body>
	
	<%@ include file="JSPHeader.jsp"%>

		<div class="mainconteneurDetail2">
				<div class="photoDetails2" class="fit-picture" src="#" alt="photo">
					<img class="photo2" alt="une photo"
						src="https://www.leparisien.fr/resizer/PQ6kjwN7VTSmYq9mzk3zHvEu0LY=/932x582/cloudfront-eu-central-1.images.arcpublishing.com/lpguideshopping/A45LFCGVEVBPBNIGSRUKV3AWFM.jpg">
		
				</div>
		
				<div class="conteneurdetails2">
					<div class="titreDetails2">
						<h1>Vous avez remporté la vente (A dyamiser)</h1>
					</div>
		
					<div class="titreProduit2">
						<p>Titre du produit :</p>
					</div>
		
					<div class="descriptionProduit2">
						<p>Description :</p>
					</div>
		
					<div class="miseAPrixProduit2">
						<p>Mise à prix :</p>
					</div>
		
					<div class="meilleurOffreProduit2">
						<p>Meilleur offre produit :</p>
					</div>
		
		
					<div class="retraitProduit2">
						<p>Retrait Produit :</p>
					</div>
		
					<div class="vendeurProduit2">
						<p>Vendeur :</p>
					</div>
		
					<div class="telVendeurProduit2">
						<p>Téléphone :</p>
					</div>
		
		
				</div>
			</div>

		<%@ include file="JSPFooter.jsp"%>


</body>
</html>
