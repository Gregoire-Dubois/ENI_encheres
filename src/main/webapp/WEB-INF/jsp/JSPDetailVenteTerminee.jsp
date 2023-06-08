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
		
		<div class="titreDetails3">
		    <c:choose>
		        <c:when test="${details.acquereur.selectionnerPseudo() eq userConnected.getPseudo()}">
		            <h1>Vous avez remporté la vente</h1>
		        </c:when>
		        <c:otherwise>
		            <h1>${details.acquereur.selectionnerPseudo()} a remporté la vente</h1>
		        </c:otherwise>
		    </c:choose>
		</div>

		<div class="conteneurdetails3">
			<div class="titreDetails3">
				<h1>détail de ma vente à jojo (à dynamiser)</h1>
			</div>

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
				<p>Retrait : ${details.getRetrait().getRue()}<p>
				<p>          ${details.getRetrait().getCodePostal()}<p>
				<p>          ${details.getRetrait().getVille()}<p>

			<div class="vendeurProduit">
				<p>Vendeur : ${details.getVendeur().getPseudo()}</p>
			</div>

		</div>
	</div>
	
	<%@ include file="JSPFooter.jsp"%>
	

</body>
</html>