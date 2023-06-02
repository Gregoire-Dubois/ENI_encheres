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
	
		<fieldset>
		<legend>Mon profile</legend>

			<label>Pseudo</label> <input type="text" name="pseudo" value="${user.pseudo}" id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<label>Prénom</label> <input type="text" name="prenom" value="exemple : Bob" id="prenom" minlength="2" maxlenght="30" required>
			<br>
			<label>Téléphone</label> <input type="tel" name="téléphone" value="0900000000" id="telephone" required>
			<br>
			<label for="codepostal">Code postal :</label>
  			<input type="text" id="codepostal" name="codepostal" pattern="[0-9]{5}" value="44000" title="Code postal">
		
			<br>
			<label>Mot de passe actuel</label> <input type="password" name="password" id="password" minlength="2" maxlenght="30" required>
			<br>
			<label>Nouveau Mot de passe</label> <input type="password" name="newPassword"  id="newPassword" minlength="2" maxlenght="30" required>
			<br>
			<label>confirmation</label> <input type="password" name="confirmNewPassword"  id="confirmNewPassword" minlength="2" maxlenght="30" required>
			<br>
			<label>Nom</label> <input type="text" name="nom" value="exemple : Léponge" id="nom" minlength="2" maxlenght="30" required>
			<br>
			<label>Email</lablel> <input type="text" name="email" value="bobleponge@gmail.com" id="email" minlength="2" maxlenght="30" required>
			<br>
			<label>Rue</lablel> <input type="text" name="rue" value="rue des lillas" id="rue" minlength="2" maxlenght="30" required>
			<br>			
			<label>Ville</label> <input type="text" name="ville" value="Nantes" id="ville" minlength="2" maxlenght="30" required>
			
		</fieldset>
			
		<button type="submit">Enregistrer</button>
	</form>
	

	<!-- <form method="post" action="/supprimermoncompte"> -->
	
		<a href="${pageContext.request.contextPath}/supprimermoncompte"><input type="button" value="Supprimer mon compte"></a>
	
	<!-- </form> -->
	
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>