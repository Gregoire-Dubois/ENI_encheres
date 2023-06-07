<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<title>Créer un compte</title>
</head>
<body>

	<%@ include file="JSPHeader.jsp" %>
	
	<h1>Mon profil</h1>
	  <form action="#" method="post">
	    <label for="pseudo">Pseudo :</label>
	    <input type="text" id="pseudo" name="pseudo" required minlength="2" maxlenght="30"><br><br>
	    
	    <label for="nom">Nom :</label>
	    <input type="text" id="nom" name="nom" minlength="2" maxlenght="30" required><br><br>
	   
	   
	    <label for="prenom">Prénom :</label>
	    <input type="text" id="prenom" name="prenom" required minlength="2" maxlenght="30"><br><br>
	    
	    <label for="telephone">Téléphone :</label>
	    <input type="tel" id="telephone" name="telephone" required><br><br>
	    
	   	<label for="ville">Ville :</label>
	    <input type="text" id="ville" name="ville" minlength="2" maxlenght="30" required><br><br>
	    
	   	<label for="rue">Rue :</label>
	    <input type="text" id="rue" name="rue" minlength="2" maxlenght="30" required><br><br>
	    
	    
	   	<label for="codepostal">Code postal :</label>
	    <input type="text" id="codepostal" name="codepostal" required><br><br>
	    
	    
	    <label for="password">Mot de passe :</label>
	    <input type="password" id="password" name="password" minlength="2" maxlenght="30" required><br><br>
	    
	    <label for="confirmpassword">Confirmation du mot de passe :</label>
	    <input type="password" id="confirmpassword" name="confirmpassword" minlength="2" maxlenght="30" required><br><br>
	    
	    
	    <label for="email">Email :</label>
	    <input type="email" id="email" name="email" minlength="2" maxlenght="30" required><br><br>
	 
	    
	    <input type="submit" value="Créer">
	    <input type="reset" value="Annuler">
	  
	  </form>

	<%@ include file="JSPFooter.jsp" %>

</body>
</html>