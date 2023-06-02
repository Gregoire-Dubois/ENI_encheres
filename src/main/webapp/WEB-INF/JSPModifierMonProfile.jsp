<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />


<title>Modifier mon profile</title>
</head>
<body>
	
	<%@ include file="JSPHeader.jsp" %>
	
	<form action="post" action ="ServletAffichageModifierProfile">
	
		<fieldset>
		<legend>Mon profile</legend>
		
			<lablel>Pseudo</lablel> <input type="text" name="pseudo" value="exemple : Bob Léponge" id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Prénom</lablel> <input type="text" name="prenom" value="exemple : Bob" id="prenom" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Téléphone</lablel> <input type="tel" name="téléphone" value="0900000000" id="telephone" required>
			<br>
			<label for="codepostal">Code postal :</label>
  			<input type="text" id="codepostal" name="codepostal" pattern="[0-9]{5}" value="44000" title="Code postal">
		
			<br>
			<lablel>Mot de passe actuel</lablel> <input type="password" name="password" id="password" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Nouveau Mot de passe</lablel> <input type="password" name="newPassword"  id="newPassword" minlength="2" maxlenght="30" required>
			<br>
			<lablel>confirmation</lablel> <input type="password" name="confirmNewPassword"  id="confirmNewPassword" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Nom</lablel> <input type="text" name="nom" value="exemple : Léponge" id="nom" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Email</lablel> <input type="text" name="email" value="bobleponge@gmail.com" id="email" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Rue</lablel> <input type="text" name="rue" value="rue des lillas" id="rue" minlength="2" maxlenght="30" required>
			<br>			
			<lablel>Ville</lablel> <input type="text" name="ville" value="Nantes" id="ville" minlength="2" maxlenght="30" required>
			
		</fieldset>
			
		<button type"submit>Enregistrer</button>
	</form>
	
	<form action="post" action="#nomDelaServletDeSuppressionDuCompte">
	
		<input type="button" value="Supprimer mon compte">
	
	</form>
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>