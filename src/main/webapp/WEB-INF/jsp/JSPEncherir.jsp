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
						<h1>Enchérir une Vente</h1>
					</div>
		
					<div class="titreProduit">
						<p>Titre du produit : ${details.getNomArticle()}</p>
					</div>
	
		
					<div class="descriptionProduit">
						<p>Description : ${details.getDescription()}</p>
					</div>
		
					<div class="categorieProduit">
						<p>Categorie :${details.getCategorie()}</p>
					</div>
		
		
					<div class="miseAPrixProduit">
						<p>Mise à prix : ${details.getPrixVente()}</p>
					</div>
		
					<div class="meilleurOffreProduit">
						<p>Meilleur offre produit :${details.getPrixVente()}</p>
					</div>
		
					<div class="finEnchereProduit">
						<p>Fin enchere produit :${details.getDateFinEncheres()}</p>
		
					</div>
		
					<div class="retraitProduit">
						<p>Retrait Produit :${details.getRetrait()}<p>
					</div>
		
					<div class="vendeurProduit">
						<p>Vendeur :${details.getVendeur()}</p>
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