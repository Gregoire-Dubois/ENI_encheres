<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enchérir</title>
</head>
<body>
	
	<%@ include file="JSPHeader.jsp"%>


		<div class="mainconteneurDetail">
				<div class="photoDetails" class="fit-picture" src="#" alt="photo">
					<img class="photo" alt="une photo"
						src="https://www.leparisien.fr/resizer/PQ6kjwN7VTSmYq9mzk3zHvEu0LY=/932x582/cloudfront-eu-central-1.images.arcpublishing.com/lpguideshopping/A45LFCGVEVBPBNIGSRUKV3AWFM.jpg">
		
				</div>
		
				<div class="conteneurdetails">
					<div class="titreDetails">
						<h1>Enchérir une Vente (A dynamiser) </h1>
					</div>
		
					<div class="titreProduit">
						<p>Titre du produit :</p>
					</div>
		
		
					<div class="descriptionProduit">
						<p>Description :</p>
					</div>
		
					<div class="categorieProduit">
						<p>Categorie :</p>
					</div>
		
		
					<div class="miseAPrixProduit">
						<p>Mise à prix :</p>
					</div>
		
					<div class="meilleurOffreProduit">
						<p>Meilleur offre produit :</p>
					</div>
		
					<div class="finEnchereProduit">
						<p>Fin enchere produit :</p>
		
					</div>
		
					<div class="retraitProduit">
						<p>Retrait Produit :
						<p>
					</div>
		
					<div class="vendeurProduit">
						<p>Vendeur :</p>
					</div>
		
					<div class="propositionProduit">
		
						<form action="" method="post">
		
							<legend>Ma proposition</legend>
		
							<select name="price" id="price-select">
								<option value="">--Votre offre--</option>
							</select>
		
						</form>
		
						<div class="button">
							<button type="submit">Enchérir</button>
						</div>
		
					</div>
				</div>
			</div>
	
		<%@ include file="JSPFooter.jsp"%>

</body>
</html>