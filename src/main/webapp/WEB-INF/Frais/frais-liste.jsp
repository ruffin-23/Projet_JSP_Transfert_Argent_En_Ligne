<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Liste Frais</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/bootstrap.css">
</head>
<body>

	<jsp:include page="../../index.jsp" />

	<br>

	<div class="row">
		<div class="container">
			<div class="container">
				<h3 class="text-center">Liste des Frais</h3>
				<hr>
				<div class="container text-left">
	
					<a href="<%= request.getContextPath() %>/frais/newF" 
						class="btn btn-success"
						>
						Ajuter un frais
					</a>
				</div>
				<br>
				<table class="table table-bordered">
					<thead>
						<tr class="text-center">
							<th>Identifient frais</th>
							<th>Montant1</th>
							<th>Montant2</th>
							<th>Frais</th>
							<th class="text-center">Actions</th>
						</tr>
					</thead>
						<tbody>
							
							<c:forEach var="frai" items="${listeF}">
		
								<tr class="align-middle text-center">
									<td><c:out value="${frai.idfrais}" /></td>
									<td><c:out value="${frai.montant1}" /> Euro</td>
									<td><c:out value="${frai.montant2}" /> Euro</td>
									<td><c:out value="${frai.frais}" /> Euro</td>
									<td class="pr-auto">
										<a href="readByIdF?idfrais=<c:out value='${frai.idfrais}' />" 
											class="btn btn-warning btn-sm"
											>
											Voire
										</a>
										<a href="editF?idfrais=<c:out value='${frai.idfrais}' />" 
											class="btn btn-primary btn-sm"
											>
											Modifier
										</a>
											&nbsp;&nbsp;&nbsp;&nbsp; 
										<a href="deleteF?idfrais=<c:out value='${frai.idfrais}' />" 
											class="btn btn-danger btn-sm"
											>
											Supprimer
										</a>
										
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
	
				</table>
			</div>
		</div>
	</div>
</body>
</html>
