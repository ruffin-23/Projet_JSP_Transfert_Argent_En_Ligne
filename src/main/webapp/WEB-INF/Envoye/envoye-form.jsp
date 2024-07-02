<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Transfert d'argent en ligne</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/bootstrap.css">
</head>
<body>

	<jsp:include page="../../index.jsp" />
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body ">
				<c:if test="${envoye != null}">
					<form action="updateE" method="post" >
				</c:if>
				<c:if test="${envoye == null}">
					<form action="insertE" method="post" >
				</c:if>

				<caption>
					<h2 class="text-center ">
						<c:if test="${envoye != null}">
            			Modification de Envoye
            		</c:if>
						<c:if test="${envoye == null}">
            			Envoyer de l'argent
            		</c:if>
					</h2>
				</caption><br>
				
				
				
				<fieldset class="form-group" >
					<label >Identifient taux : </label> 
					<input type="text"
						value="<c:out value='${envoye.idEnv}' />" 
						class="form-control"
						name="idEnv" 
						required="required"
					/>
				</fieldset>

				<fieldset class="form-group">
					<label>Numero envoyeur</label> 
					<input type="text" value="${numtel}" required="required" readonly
						
						class="form-control"
						name="numEnv" 
						
					/>
				</fieldset>
				
				<fieldset class="form-group">
                    <label>Numero recepteur</label> 
                    <select name="numRec" class="form-control"  required="required">
      					
					   <c:forEach var="client" items="${listNumTel}">
					   
					        <c:if test="${client.numtel != numtel}">
	                            <option value="${client.numtel}">${client.numtel}</option>
	                        </c:if>
					        
					    </c:forEach>
					    
					</select>
                </fieldset>	
				
				<fieldset class="form-group">
					<label>Montant</label> 
					<input type="number" 
						value="<c:out value='${envoye.montant}' />" 
						class="form-control"
						name="montant" 
						required="required"
					/>
				</fieldset>
				
				<fieldset class="form-group">
					<label>Date</label> 
					<input type="date" 
						value="<c:out value='${envoye.date}' />" 
						class="form-control"
						name="date" 
						required="required"
					/>
				</fieldset>

				<fieldset class="form-group">
					<label>Raison</label> 
					<input type="text"
						value="<c:out value='${envoye.raison}' />" 
						class="form-control"
						name="raison"
					/>
				</fieldset>
				<br>

				<button type="submit" class="btn btn-success">Envoyer</button>
				<button type="reset" class="btn btn-danger">Annuler</button>
				<a href="javascript:history.back()" class="btn btn-primary">Retour au detail</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

