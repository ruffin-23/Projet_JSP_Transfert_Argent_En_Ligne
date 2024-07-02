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
                Détails du Frais
            </div>
            <div class="card-body">
                <h5 class="card-title">Informations Frais</h5>
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Identifient taux :</strong> ${frais.idfrais}</p>
                        <p><strong>Montant minimal :</strong> ${frais.montant1}</p>
                        <p><strong>Montant maximal :</strong> ${frais.montant2}</p>
                        <p><strong>Frais : </strong> ${frais.frais}</p>
                    </div>
                </div>
                <a href="editT?idtaux=${frais.idfrais}" class="btn btn-primary">Modifier</a>
                <a href="deleteT?idtaux=${frais.idfrais}" class="btn btn-danger">Supprimer</a>
                <a href="<%= request.getContextPath() %>/frais/listF" class="btn btn-secondary">Retour à la liste des frais</a>
            </div>
        </div>
    </div>
    <!-- Scripts Bootstrap -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
