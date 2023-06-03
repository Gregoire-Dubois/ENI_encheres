<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css" href="css/style.css" />

<title>Mon profil</title>
</head>
<body>

	<%@ include file="JSPHeader.jsp" %>
	
	<section>
		<c:choose>
			
			<c:when test="${utilisateur !=null || vendeur!=null}">
				<p>
					Pseudo: ${utilisateur.getPseudo() } 
					</br> 
					Nom: ${utilisateur.getNom() } 
					</br> 
					Prénom:${utilisateur.getPrenom() } 
					</br> 
					Email: ${utilisateur.getEmail() } 
					</br> 
					Téléphone:${utilisateur.getTelephone() } 
					</br> 
					Rue: ${utilisateur.getRue() } 
					</br> 
					Code Postal:${utilisateur.getCodePostal() } 
					</br> 
					Ville: ${utilisateur.getVille() }
					</br> 
					Crédit: ${utilisateur.getCredit() }
				</p>
			
			</c:when>
			
			<c:otherwise>
				<c:forEach var="code" items="${listeErreursProfil}">
					<li>${LecteurMessage.getMessageErreur(code)}</li>

				</c:forEach>

			</c:otherwise>


		</c:choose>
		
		<!-- Si l'attribut "utilisateur" de la session = userConnected, alors il a accès au bouton modifier -->
		<c:if test="${utilisateur == userConnected}">
			<a href="${pageContext.request.contextPath}/modification"><button type="button" id="modifier" name="modifier" value="modifier">Modifier</button></a>

			
		</c:if>
		
	</section>

	<%-- <table>
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
	
	<button type="submit">Modifier</button> --%>
	
	<%@ include file="JSPFooter.jsp" %>

</body>
</html>