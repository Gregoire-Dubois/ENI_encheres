<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.enienchere.bo.Utilisateur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
</head>
<body>

	<div>
      <% Utilisateur connectedUser = (Utilisateur) session.getAttribute("ConnectedUser"); %>
          <ul>
              <li><a href="<%=request.getContextPath()%>/accueilConnected">Enchères</a></li>
              <li><a href="<%=request.getContextPath()%>/vendre">Vendre</a></li>
              <li><a href="<%=request.getContextPath()%>/profil?pseudo=<%=connectedUser.getPseudo()%>">Mon Profil</a></li>
              <li><a href="<%=request.getContextPath()%>/AccueilDeconnected">Deconnexion</a></li>
          </ul>
      </div>

</body>
</html>