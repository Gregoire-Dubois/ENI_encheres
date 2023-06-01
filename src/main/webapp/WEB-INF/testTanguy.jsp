<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>modification d'un utilisateur</h1>
<form method="post" action="modifier">
<input type="hidden" name="id" value="${Utilisateur.no_utilisateur}">
pseudo <input type="text" name="nom" value="${Utilisateur.pseudo}">
<br/>
nom <input type="text" name="nom" value="${Utilisateur.nom}">
<br/>
prenom <input type="text" name="prenom" value="${Utilisateur.prenom}">
<br/>
email <input type="text" name="email" value="${Utilisateur.email}">
<br/>
telephone <input type="text" name="telephone" value="${Utilisateur.telephone}">
<br/>
rue <input type="text" name="rue" value="${Utilisateur.email}">
<br/>
code postal <input type="text" name="code_postal" value="${Utilisateur.code_postal}">
<br/>
ville <input type="text" name="ville" value="${Utilisateur.ville}">
<br/>
mot de passe <input type="text" name="mot_de_passe" value="${Utilisateur.mot_de_passe}">
<br/>
<input type="submit" name="valider" value="valider">

</form>


</body>
</html>