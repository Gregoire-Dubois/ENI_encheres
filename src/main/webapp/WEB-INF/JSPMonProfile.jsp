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
		<c:forEach var="v" items="${utilisateur}">
			<tr>
			
				<td>Pseudo : </td>
				<td>${v.pseudo}</td>
			</tr>
			<tr>
				<td>Nom : </td>
				<td>${v.nom}</td>
			</tr>
			<tr>	
				<td>Prénom : </td>
				<td>${v.prenom}</td>
			</tr>
			<tr>
				<td>Email : </td>
				<td>${v.email}</td>
			</tr>
				<td>Téléphone : </td>
				<td>${v.telephone}</td>
			<tr>
				<td>Rue : </td>
				<td>${v.rue}</td>
			</tr>
				<td>Code postal : </td>
				<td>${v.codePostal}</td>
			<tr>
				<td>Ville : </td>
				<td>${v.ville}</td>
			</tr>
		</c:forEach>
		
	
	</table>
	
	<%@ include file="JSPFooter.jsp" %>

	

</body>
</html>