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


<c:choose>
  <c:when test="${etatVente == 'EC'}">
  
  		<%@ include file="JSPEncherir.jsp"%>


  </c:when>
  <c:when test="${etatVente == 'VE'}">

		<%@ include file="JSPEncherir.jsp"%>
		

  </c:when>
  <c:when test="${etatVente == 'NC'}">

		<%@ include file="JSPDetailVenteTerminee.jsp"%>

  </c:when>

</c:choose>



</body>
</html>