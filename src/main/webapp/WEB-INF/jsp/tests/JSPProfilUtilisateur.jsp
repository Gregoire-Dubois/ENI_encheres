<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="fr.eni.enienchere.bo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profil Utilisateur</title>
</head>
<body>
	
	<% 
	Utilisateur u;
	u = (Utilisateur)request.getAttribute("user");
	%>
	

<header>
<h1>ENI-Enchères</h1>
</header>

<form method="get" action="VueProfil">
<label for="ID">"ID :"</label>
<input type="text" name="id" value=<%u.getNoUtilisateur(); %>>
<br>
<label for="pseudo">"Pseudo :"</label>
<input type="text" id="pseudo" name="pseudo" value=<%u.getPseudo()(); %>>
<br>
<label for="nom">"Nom :"</label>
<input type="text" id="nom" name="nom" value=<%u.getNom()(); %>>
<br>
<label for="prenom">"Prénom :"</label>
<input type="text" id="prenom" name="prenom" value=<%u.getPrenom()(); %>>
<br>
<label for="email">"Email :"</label>
<input type="text" id="email" name="email" value=<%u.getEmail()(); %>>
<br>
<label for="telephone">"Téléphone :"</label>
<input type="text" id="telephone" name="telephone" value=<%u.getTelephone()(); %>>
<br>
<label for="rue">"Rue :"</label>
<input type="text" id="rue" name="rue" value=<%u.getRue()(); %>>
<br>
<label for="code_postal">"Code Postal :"</label>
<input type="text" id="code_postal" name="code_postal" value=<%u.getCodePostal()(); %>>
<br>
<label for="ville">"Ville :"</label>
<input type="text" id="ville" name="ville" value=<%u.getVille()(); %>>
</form>


</body>
</html>