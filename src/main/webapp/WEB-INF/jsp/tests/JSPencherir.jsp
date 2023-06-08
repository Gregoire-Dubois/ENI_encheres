<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.enienchere.messages.LecteurMessage"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ENI enchères</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>




				<div class="div-encherir">
    <form method ="post">
        <label for="montantEnchere">Ma Proposition : </label>
        <input type="number" id="montantEnchere" name="montantEnchere"
               min =      
        <c:choose>
    <c:when test="${enchere == null}">
         "${article.prixInitial}" value="${article.prixInitial + 1}"
    </c:when>
    <c:otherwise>
        "${enchere.montantEnchere + 1}" value="${enchere.montantEnchere + 1}"
    </c:otherwise>
</c:choose>
        />
        <input type="submit" value="Enchérir" />
    </form>

			</div>



</body>
</html>