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
			<th>Pseudo</th>
			<th>Nom</th>
			<th>Prénom</th>
			<th>Email</th>
			<th>Téléphone</th>
			<th>Rue</th>
			<th>Code postal</th>
			<th>Ville</th>
		</tr>
		<c:forEach var="v" items="${utilisateur}">
			<tr>
				<th>${v.pseudo}</th>
				<th>${v.nom}</th>
				<th>${v.prenom}</th>
				<th>${v.email}</th>
				<th>${v.telephone}</th>
				<th>${v.rue}</th>
				<th>${v.codePostal}</th>
				<th>${v.ville}</th>
			</tr>
		</c:forEach>		
		
	
	</table>
	
	<%@ include file="JSPFooter.jsp" %>

	

</body>
</html>