<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vente non commencée</title>
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
						<h1>Détail de ma vente non commencée</h1>
					</div>
		
					<div class="titreProduit">
						<p>Titre du produit : ${details.getNomArticle()}</p>
					</div>
	
		
					<div class="descriptionProduit">
						<p>Description : ${details.getDescription()}</p>
					</div>
		
					<div class="categorieProduit">
						<p>Categorie :${details.getCategorie().getLibelle()}</p>
					</div>
		
					<div class="miseAPrixProduit">
						<p>Mise à prix : ${details.getPrixInitial()}</p>
					</div>				
		
		
		
					<div class="debutEncheres">
						<p>Début encheres produit :${details.getDateDebutEncheres()}</p>
		
					</div>
		
		
		
					<div class="finEnchereProduit">
						<p>Fin enchere produit :${details.getDateFinEncheres()}</p>
		
					</div>
		
					<div class="retraitProduit">
						<p>Retrait Produit :${details.getRetrait().getRue()}<p>
						<p>${details.getRetrait().getCodePostal()}<p>
						<p>${details.getRetrait().getVille()}<p>
						
					</div>
			
	
				</div>
			</div>
	
	
	<%@ include file="JSPFooter.jsp"%>

</body>
</html>