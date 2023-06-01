<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INSCRIPTION</title>
</head>
<body>
<header>
<h1>ENI-Encheres</h1>

</header>

<section>
<h2>Mon profil</h2>
<p>
<c:if test="${!empty listeErreursInscription }">
	<c:forEach var="code" items="${listeErreursInscription}">
			<li>Erreur: ${LecteurMessage.getMessageErreur(code)}</li>
	</c:forEach>
</c:if>
</p>
<form method="post" action="${pageContext.request.contextPath}/CCN_ServletInscription">

<label for="pseudo">Pseudo :</label> 
<input type="text" name="pseudo" id="pseudo" value="${not empty param.pseudo ? param.pseudo : ''}" required>
 </br>
<label for="nom">Nom :</label> 
<input type="text" name="nom" id="nom" value="${not empty param.nom ? param.nom : ''}" required>
 </br>
<label for="prenom">Prénom :</label> 
<input type="text" name="prenom" id="prenom" value="${not empty param.prenom ? param.prenom : ''}" required>
 </br>
<label for="email">Email :</label> 
<input type="email" name="email" id="email" value="${not empty param.email ? param.email : ''}" required>
 </br>
<label for="telephone">Téléphone :</label> 
<input type="text" name="telephone" id="telephone" value="${not empty param.telephone ? param.telephone : ''}">
 </br>
<label for="rue">Rue :</label> 
<input type="text" name="rue" id="rue" value="${not empty param.rue ? param.rue : ''}" required>
 </br>
<label for="codePostal">Code Postal :</label> 
<input type="text" name="codePostal" id="codePostal" value="${not empty param.codePostal ? param.codePostal : ''}" required>
 </br>
<label for="ville">Ville :</label> 
<input type="text" name="ville" id="ville" value="${not empty param.ville ? param.ville : ''}" required>
 </br>
<label for="mdp">Mot de passe :</label> 
<input type="password" name="mdp" id="mdp" required>
 </br>
<label for="confirmationMdp">Confirmation :</label> 
<input type="password" name="confirmationMdp" id="confirmationMdp" required>
 </br>
<input type="submit" value="Créer">
 </br>
</form>

<a href="${pageContext.request.contextPath}/accueil"><input type="submit" value="Annuler"></a>
</section>


</body>
</html>