<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />


<title>Modifier mon profile</title>
</head>
<body>
	
	<%@ include file="JSPHeader.jsp" %>
	
	<form method="post" action ="ServletAffichageModifierProfile">
		<input type="hidden" name="id" value="${userConnected.noUtilisateur}">
		<fieldset>
		<legend>Mon profile</legend>
			
			<label>Pseudo</label> <input type="text" name="pseudo" value="${userConnected.pseudo}" id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<label>Prénom</label> <input type="text" name="prenom" value="${userConnected.prenom}" id="prenom" minlength="2" maxlenght="30" required>
			<br>
			<label>Téléphone</label> <input type="tel" name="téléphone" value="${userConnected.telephone}" id="telephone" required>
			<br>
			<label for="codepostal">Code postal :</label>
  			<input type="text" id="codepostal" name="codepostal" pattern="[0-9]{5}" value="${userConnected.codePostal}" title="Code postal">
		
			<br>
			<label>Mot de passe actuel</label> <input type="password" name="password" id="password" minlength="2" maxlenght="30" required>
			<br>
			<label>Nouveau Mot de passe</label> <input type="password" name="newPassword"  id="newPassword" minlength="2" maxlenght="30" required>
			<br>
			<label>confirmation</label> <input type="password" name="confirmNewPassword"  id="confirmNewPassword" minlength="2" maxlenght="30" required>
			<br>
			<label>Nom</label> <input type="text" name="nom" value="${userConnected.nom}" id="nom" minlength="2" maxlenght="30" required>
			<br>
			<label>Email</lablel> <input type="text" name="email" value="${userConnected.email}" id="email" minlength="2" maxlenght="30" required>
			<br>
			<label>Rue</lablel> <input type="text" name="rue" value="${userConnected.rue}" id="rue" minlength="2" maxlenght="30" required>
			<br>			
			<label>Ville</label> <input type="text" name="ville" value="${userConnected.ville}" id="ville" minlength="2" maxlenght="30" required>
			
		</fieldset>
		<p>	
		<button type="submit">Enregistrer</button>
	</form>
	<p>

	<!-- <form method="post" action="/supprimermoncompte"> -->
	<!-- Modification de la redirection du bouton supprimer vers une servlet de test -->
		<!-- <a href="${pageContext.request.contextPath}/supprimermoncompte"><input type="button" value="Supprimer mon compte"></a>-->
		<form method="post" action="<%=request.getContextPath()%>/delete">
              <input type="hidden" name="id" value="${utilisateur.id}">
              <input type="submit" value="SUPPRIMER">
        </form>
	
	<!-- </form> -->
	
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>