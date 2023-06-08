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
						<p>Categorie :${details.getCategorie().getLibelle()}</p>
					</div>
		
					<div class="miseAPrixProduit">
						<p>Mise à prix : ${details.getPrixInitial()}</p>
					</div>
		
					<div class="meilleurOffreProduit">
						<p>Meilleur offre produit :${details.getPrixVente()}</p>
					</div>
		
					<div class="finEnchereProduit">
						<p>Fin enchere produit :${details.getDateFinEncheres()}</p>
		
					</div>
		
					<div class="retraitProduit">
						<p>Retrait Produit :${details.getRetrait().getRue()}<p>
						<p>${details.getRetrait().getCodePostal()}<p>
						<p>${details.getRetrait().getVille()}<p>
						
					</div>
		
					<div class="vendeurProduit">
						<p>Vendeur :${details.getVendeur().getPseudo()}</p>
					</div>
				</div>
			</div>
			

    				<form method ="post">
        				<label for="montantEnchere">Ma Proposition : </label>
        				<input type="number" id="montantEnchere" name="montantEnchere"
               			min =      
        					<c:choose>
    							<c:when test="${enchere == null}">
         						"${article.prixInitial}" value="${article.prixInitial + 1}"
    							</c:when>
    						<c:otherwise>
        						"${enchere.montantEnchere + 1}" value="${enchere.montantEnchere + 1}"
    						</c:otherwise>
							</c:choose>
        				/>
	        			<input type="submit" value="Enchérir" />
    				</form>
    				</div>

	<c:if test="${!empty listeErreursEnchere }">
	<c:forEach var="code" items="${listeErreursEnchere}">
			<li style="color: red">Erreur: ${LecteurMessage.getMessageErreur(code)}</li>
	</c:forEach>
</c:if>
	
		<%@ include file="JSPFooter.jsp"%>

</body>
</html>