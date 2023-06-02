<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />

<title>Mon profile</title>
</head>
<body>

	<%@ include file="JSPHeader.jsp" %>



	<table>
			<tr>
				<td>Pseudo : </td>
				<td>${user.pseudo}</td>
			</tr>
			<tr>
				<td>Nom : </td>
				<td>${user.nom}</td>
			</tr>
			<tr>	
				<td>Prénom : </td>
				<td>${user.prenom}</td>
			</tr>
			<tr>
				<td>Email : </td>
				<td>${user.email}</td>
			</tr>
				<td>Téléphone : </td>
				<td>${user.telephone}</td>
			<tr>
				<td>Rue : </td>
				<td>${user.rue}</td>
			</tr>
				<td>Code postal : </td>
				<td>${user.codePostal}</td>
			<tr>
				<td>Ville : </td>
				<td>${user.ville}</td>
			</tr>
	
	</table>
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>