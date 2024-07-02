<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails du Taux</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/bootstrap.css">
    <style>
        
        body {
            padding: 20px;
        }
    </style>
</head>
<body>
	
	<jsp:include page="../../index.jsp" />
	<br>

    <div class="container">
        <div class="card">
            <div class="card-header bg-primary text-white">
                Détails du Taux
            </div>
            <div class="card-body">
                <h5 class="card-title">Informations Taux</h5>
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Identifient taux :</strong> ${taux.idtaux}</p>
                        <p><strong>Montant en Euro :</strong> ${taux.montant1}</p>
                        <p><strong>Montant en Ariary : </strong> ${taux.montant2}</p>
                    </div>
                </div>
                <a href="editT?idtaux=${taux.idtaux}" class="btn btn-primary">Modifier</a>
                <a href="deleteT?idtaux=${taux.idtaux}" class="btn btn-danger">Supprimer</a>
                <a href="<%= request.getContextPath() %>/taux/listeT" class="btn btn-secondary">Retour à la liste des taux</a>
            </div>
        </div>
    </div>
    <!-- Scripts Bootstrap -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
