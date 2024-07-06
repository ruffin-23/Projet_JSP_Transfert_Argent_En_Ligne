<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Liste Envoye</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bootstrap.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#search").keyup(function(){
                var query = $(this).val();
                $.ajax({
                    url: "<%= request.getContextPath() %>/envoye/search",
                    type: "GET",
                    data: {searchQuery: query},
                    success: function(data){
                        $("#envoyeTable").html(data);
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
                <h3 class="text-center">Liste des Envoyes</h3>
                <hr>
                <div class="col-3 ms-auto">
                    <input class="form-control" type="date" id="search" placeholder="Search"/>
                </div>
                <div class="col-3 ">
				    <p>Somme des frais d'envoye : <%= request.getAttribute("sommefrais") %></p>
				</div>
                <br>
                <table class="table table-bordered">
                    <thead>
                        <tr class="text-center">
                            <th>Id envoye</th>
                            <th>Numero envoyeur</th>
                            <th>Numero recepteur</th>
                            <th>Montant</th>
                            <th>Date</th>
                            <th>Raison</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="envoyeTable">
                        <c:forEach var="list" items="${ listeEnv }">
                            <tr class="align-middle text-center">
                                <td><c:out value="${list.idEnv}" /></td>
                                <td><c:out value="${list.numEnvoyeur}" /> </td>
                                <td><c:out value="${list.numRecepteur}" /> </td>
                                <td><c:out value="${list.montant}" /></td>
                                <td>
                                    <%-- Convertir la chaîne de caractères en java.util.Date --%>
                                    <%-- Assurez-vous que list.date est une chaîne au format 'yyyy-MM-dd HH:mm:ss' --%>
                                    <fmt:parseDate value="${list.date}" var="parsedDate" pattern="yyyy-MM-dd HH:mm:ss" />
                                    <fmt:formatDate value="${parsedDate}" pattern="dd-MM-yyyy" />
                                </td>
                                <td><c:out value="${list.raison}" /> </td>
                                <td>
                                    <a href="<%= request.getContextPath() %>/generatePDF?numEnv=<c:out value='${list.numEnvoyeur}' />"
                                        target="_blank"
                                        class="btn btn-primary btn-sm"
                                        >
                                        Télécharger PDF
                                    </a>
                                    &nbsp;&nbsp;&nbsp;&nbsp; 
                                    <a href="deleteE?idEnv=<c:out value='${list.idEnv}' />" 
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






<!--  	&nbsp;&nbsp;&nbsp;&nbsp; 
										<a href="deleteE?idEnv=<c:out value='${list.idEnv}' />" 
											class="btn btn-danger btn-sm"
											>
											Supprimer
										</a>
										<a href="readByIdE?idEnv=<c:out value='${list.idEnv}' />" 
											class="btn btn-warning btn-sm"
											>
											Voire
										</a>-->

<!--<div class="container text-left">
	
					<a href="<%= request.getContextPath() %>/envoye/newE" 
						class="btn btn-success"
						>
						Ajuter un Envoye
					</a>
				</div>-->