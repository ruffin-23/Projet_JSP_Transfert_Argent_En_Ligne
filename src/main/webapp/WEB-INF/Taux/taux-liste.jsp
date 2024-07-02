<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Liste Taux</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/bootstrap.css">
</head>
<body>

	<jsp:include page="../../index.jsp" />

	<br>

	<div class="row">
		<div class="container">
			<div class="container">
				<h3 class="text-center">Liste des Taux</h3>
				<hr>
				<div class="container text-left">
	
					<a href="<%= request.getContextPath() %>/taux/newT" 
						class="btn btn-success"
						>
						Ajuter un taux
					</a>
				</div>
				<br>
				<table class="table table-bordered mx-auto">
				    <thead>
				        <tr class="text-center">
				            <th>Identifient taux</th>
				            <th>Montant1</th>
				            <th>Montant2</th>
				            <th>Actions</th>
				        </tr>
				    </thead>
				    <tbody>
				        <c:forEach var="taux" items="${listeT}">
				            <tr class="align-middle text-center">
				                <td><c:out value="${taux.idtaux}" /></td>
				                <td><c:out value="${taux.montant1}" /> Euro</td>
				                <td><c:out value="${taux.montant2}" /> Ariary</td>
				                <td >
				                	<a href="readByIdT?idtaux=<c:out value='${taux.idtaux}' />" class="btn btn-warning btn-sm">
				                        Voir
				                    </a>
				                    <a href="editT?idtaux=<c:out value='${taux.idtaux}' />" class="btn btn-primary btn-sm">
				                        Modifier
				                    </a>
				                    &nbsp;&nbsp;&nbsp;&nbsp;
				                    <a href="deleteT?idtaux=<c:out value='${taux.idtaux}' />" class="btn btn-danger btn-sm">
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
