<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Liste Client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
	    $(document).ready(function(){
	        $("#search").keyup(function(){
	            
	            var query = $(this).val();
	            $.ajax({
	                url: "<%= request.getContextPath() %>/Client/search",
	                type: "GET",
	                data: {searchQuery: query},
	                success: function(data){
	                    $("#clientTable").html(data);
	                }
	            });
	            
	        });
	    });
    </script>
</head>
<body>

    <jsp:include page="../../index.jsp" />

    <br>

    <div class="row">
        <div class="container">
        
            <div class="container">
                <h3 class="text-center">Liste des clients</h3>
                <hr>
                <div class="container text-left">
                    <div class="col-3 ms-auto">
                        <input class="form-control" type="text" id="search" placeholder="Search"/>
                    </div>
                    <a href="<%=request.getContextPath()%>/Client/new" 
                        class="btn btn-success">
                        Ajouter un client
                    </a>
                </div>
                <br>
                <table class="table table-bordered">
                    <thead>
                        <tr class="text-center">
                            <th>Numero téléphone</th>
                            <th>Nom</th>
                            <th>Sexe</th>
                            <th>Pays</th>
                            <th>Solde</th>
                            <th>Email</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="clientTable">
                        <c:forEach var="client" items="${listeClients}">
                            <tr class="align-middle text-center">
                                <td><c:out value="${client.numtel}" /></td>
                                <td><c:out value="${client.nom}" /></td>
                                <td><c:out value="${client.sexe}" /></td>
                                <td><c:out value="${client.pays}" /></td>
                                <td><c:out value="${client.solde}" /> Euro</td>
                                <td><c:out value="${client.mail}" /></td>
                                <td>
                                
                                	<a href="<%=request.getContextPath()%>/Client/readById?numtel=<c:out value='${client.numtel}' />" 
                                        class="btn btn-warning btn-sm">
                                        Voir
                                    </a>
                                	&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="<%=request.getContextPath()%>/Client/edit?numtel=<c:out value='${client.numtel}' />" 
                                        class="btn btn-primary btn-sm">
                                        Modifier
                                    </a>
                                     
                                    <a href="<%=request.getContextPath()%>/Client/delete?numtel=<c:out value='${client.numtel}' />" 
                                        class="btn btn-danger btn-sm">
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
