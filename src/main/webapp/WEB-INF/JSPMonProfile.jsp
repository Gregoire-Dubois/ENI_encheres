<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
				<td>${userConnected.pseudo}</td>
			</tr>
			<tr>
				<td>Nom : </td>
				<td>${userConnected.nom}</td>
			</tr>
			<tr>	
				<td>Prénom : </td>
				<td>${userConnected.prenom}</td>
			</tr>
			<tr>
				<td>Email : </td>
				<td>${userConnected.email}</td>
			</tr>
				<td>Téléphone : </td>
				<td>${userConnected.telephone}</td>
			<tr>
				<td>Rue : </td>
				<td>${userConnected.rue}</td>
			</tr>
				<td>Code postal : </td>
				<td>${userConnected.codePostal}</td>
			<tr>
				<td>Ville : </td>
				<td>${userConnected.ville}</td>
			</tr>
	
	</table>
	
	<button type="submit">Modifier</button>
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>