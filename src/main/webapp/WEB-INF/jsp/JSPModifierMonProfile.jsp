<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />


<title>Modifier mon profil</title>
</head>
<body>
	
	<%@ include file="JSPHeader.jsp" %>
	
	<section>
	
	<div>
		<h1>Mon Profil</h1>
	</div>
	
		
		<p> 
			<c:if test="${!empty listeErreursInscription }">
				<c:forEach var="code" items="${listeErreursInscription}">
					<li>Erreur: ${LecteurMessage.getMessageErreur(code)}</li>
				</c:forEach>
			</c:if>

		</p>
	
	<form method="post" action="${pageContext.request.contextPath}/modification">
		<input type="hidden" name="id" value="${userConnected.noUtilisateur}">
		<fieldset>
		<legend>Mon profil</legend>
			
			<label for="pseudo">Pseudo :</label>  
			<input type="text" name="pseudo" value="${userConnected.pseudo}" id="pseudo" minlength="2" maxlength="30" required>
			<br>
			<label for="nom">Nom :</label> 
			<input type="text" name="nom" value="${userConnected.getNom()}" id="nom" minlength="2" maxlength="30" required>
			</br>
			<label for="prenom">Prénom :</label> 
			<input type="text" name="prenom" value="${userConnected.getPrenom()}" id="prenom" minlength="2" maxlength="30" required>
			<br>
			<label for="email">Email :</label> 
			<input type="text" name="email" value="${userConnected.getEmail()}" id="email" minlength="2" maxlength="30" required>
			<br>
			<label for="telephone">Téléphone :</label>
			<input type="tel" name="telephone" value="${userConnected.getTelephone()}" id="telephone">
			<br>
			<label for="rue">Rue:</label>
			<input type="text" name="rue" value="${userConnected.getRue()}" id="rue" minlength="2" maxlength="30" required>
			<br>
			<label for="codePostal">Code Postal :</label> 
  			<input type="text" id="codePostal" name="codePostal" pattern="[0-9]{5}" value="${userConnected.getCodePostal()}" title="Code postal">
			<br>			
			<label for="ville">Ville :</label>  
			<input type="text" name="ville" value="${userConnected.getVille()}" id="ville" minlength="2" maxlength="30" required>
			<br>
			<label for="mdp">Mot de passe :</label> 
			<input type="password" name="mdp" id="mdp" minlength="2" maxlength="30" required>
			<br>
			<label for="nouveauMdp">Nouveau mot de passe :</label> 
			<input type="password" name="nouveauMdp"  id="nouveauMdp" minlength="2" maxlength="30">
			<br>
			<label for="confirmationMdp">Confirmation:</label> 
			<input type="password" name="confirmationMdp"  id="confirmationMdp" minlength="2" maxlength="30">
			<br>
			</br>
			
					Crédit: ${userConnected.getCredit() }
			</br> 
			<input type="submit" value="Enregistrer">
		</fieldset>
		
		
	</form>
	

	
		<form method="post" action="<%=request.getContextPath()%>/delete">
              <input type="hidden" name="id" value="${utilisateur.noUtilisateur}">
              <input type="submit" value="SUPPRIMER">
        </form>
        
		
		
	<a href="${pageContext.request.contextPath}/accueil"><input	type="submit" value="Annuler"></a>
	
	
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>