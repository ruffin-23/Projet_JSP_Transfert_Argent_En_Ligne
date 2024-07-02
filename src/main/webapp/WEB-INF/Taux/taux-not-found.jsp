<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Taux non trouvé</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="alert alert-danger" role="alert">
            <h4 class="alert-heading">Taux non trouvé!</h4>
            <p>Le taux avec l'ID spécifié n'a pas été trouvé.</p>
            <hr>
            <p class="mb-0">Retournez à la <a href="<%= request.getContextPath() %>/taux-list">liste des taux</a>.</p>
        </div>
    </div>
    <!-- Scripts Bootstrap -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
