<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Liste Client</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>

	<jsp:include page="index.jsp" />

	<br>

	<div class="row">

		<div class="container">
			<h3 class="text-center">Liste des clients</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" 
					class="btn btn-success"
					>
					Ajuter un client
				</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Numero telephone</th>
						<th>Nom</th>
						<th>Sexe</th>
						<th>Pays</th>
						<th>Solde</th>
						<th>Email</th>
						<th>Actions</th>
					</tr>
				</thead>
					<tbody>
						
						<c:forEach var="client" items="${listeClient}">
	
							<tr>
								<td><c:out value="${client.numtel}" /></td>
								<td><c:out value="${client.nom}" /></td>
								<td><c:out value="${client.sexe}" /></td>
								<td><c:out value="${client.pays}" /></td>
								<td><c:out value="${client.solde}" /></td>
								<td><c:out value="${client.mail}" /></td>
								<td>
									<a href="edit?numtel=<c:out value='${client.numtel}' />" 
										class="btn btn-primary btn-sm"
										>
										Modifier
									</a>
										&nbsp;&nbsp;&nbsp;&nbsp; 
									<a href="delete?numtel=<c:out value='${client.numtel}' />" 
										class="btn btn-danger btn-sm"
										>
										Supprimer
									</a>
									<a href="readById?numtel=<c:out value='${client.numtel}' />" 
										class="btn btn-warning btn-sm"
										>
										Voire
									</a>
								</td>
							</tr>
						</c:forEach>
							<!-- Si aucun client n'est trouvé -->
				        <!--<c:if test="${empty clients}">
				            <tr>
				                <td colspan="7">Aucun client trouvé.</td>
				            </tr>
				        </c:if> -->
						
					</tbody>

			</table>
		</div>
	</div>
</body>
</html>
