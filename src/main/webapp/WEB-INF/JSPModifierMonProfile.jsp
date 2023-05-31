<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />


<title>Modifier mon profile</title>
</head>
<body>

<header>
	
		<div class="entreprise">
			<h1>ENI-Enchères</h1>
		</div>
		
		<div>
			<a href="#">S'inscrire - Se déconnecter</a>
		</div>
	
	</header>
	
	<form action="post" action ="#NomDeLaServletDeModificationDuCompte">
	
		<fieldset>
		<legend>Mon profile</legend>
			<lablel>Pseudo</lablel> <input type="text" name="pseudo" value="exemple : Bob Léponge" id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Prénom</lablel> <input type="text" name="pseudo" value="exemple : Bob" id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Téléphone</lablel> <input type="tel" name="téléphone" value="09 00 00 00 00" id="telephone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required>
			<br>
			<label for="codepostal">Code postal :</label>
  			<input type="text" id="codepostal" name="codepostal" pattern="[0-9]{5}" value="44000" title="Code postal">
		
			<br>
			<lablel>Mot de passe actuel</lablel> <input type="password" name="password" id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Nouveau Mot de passe</lablel> <input type="password" name="newPassword"  id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<lablel>confirmation</lablel> <input type="password" name="confirmNewPassword"  id="pseudo" minlength="2" maxlenght="30" required>
			<br>
			<lablel>Nom</lablel> <input type="text" name="nom" value="exemple : Léponge" id="pseudo" minlength="2" maxlenght="30" required>
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
	
	<footer>
		<p>Team Les Vioques © 2023</p>
	</footer>

</body>
</html>