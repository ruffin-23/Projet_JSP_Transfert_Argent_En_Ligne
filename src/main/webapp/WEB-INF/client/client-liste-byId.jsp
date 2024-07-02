<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails du Client</title>
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
                Détails du Client
            </div>
            <div class="card-body">
                <h5 class="card-title">Informations Client</h5>
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Numéro de téléphone :</strong> ${client.numtel}</p>
                        <p><strong>Nom :</strong> ${client.nom}</p>
                        <p><strong>Sexe :</strong> ${client.sexe}</p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Pays :</strong> ${client.pays}</p>
                        <p><strong>Solde :</strong> ${client.solde}</p>
                        <p><strong>Email :</strong> ${client.mail}</p>
                    </div>
                </div>
                <a href="edit?numtel=${client.numtel}" class="btn btn-primary">Modifier</a>
                <a href="delete?numtel=${client.numtel}" class="btn btn-danger">Supprimer</a>
                
                <a href="<%= request.getContextPath() %>/envoye/newE?numtel=${client.numtel}" class="btn btn-success">Envoyer un argent</a>

            </div>
        </div>
    </div>
    <!-- Scripts Bootstrap -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
