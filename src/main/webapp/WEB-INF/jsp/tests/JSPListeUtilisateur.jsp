<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "java.util.List,fr.eni.enienchere.bo.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Liste Utilisateurs</h2>

<table>
	<tr>
		<th>N° Utilisateur</th>
		<th>Pseudo</th>
		<th>Nom</th>
		<th>Prénom</th>
		<th>Email</th>
		<th>Téléphone</th>
		<th>Rue</th>
		<th>Code Postal</th>
		<th>Ville</th>
		<th>Crédit</th>
	</tr>	
	<%
		List<Utilisateur> lst;
		lst=(List<Utilisateur>)request.getAttribute("liste");
		for(Utilisateur u:lst){
	%>
	<tr>
		<td><%=u.getNoUtilisateur() %></td>
		<td><%=u.getPseudo()() %></td>
		<td><%=u.getNom()() %></td>
		<td><%=u.getPrenom()() %></td>
		<td><%=u.getEmail()() %></td>
		<td><%=u.getTelephone()() %></td>
		<td><%=u.getRue()() %></td>
		<td><%=u.getCodePostal()() %></td>
		<td><%=u.getVille()() %></td>
		<td><%=u.getCredit()() %></td>
	</tr>
		
	<%		
		}
	%>
	
</table>


</body>
</html>