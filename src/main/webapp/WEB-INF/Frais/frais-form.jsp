<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
				<c:if test="${frais != null}">
					<form action="updateF" method="post" >
				</c:if>
				<c:if test="${frais == null}">
					<form action="insertF" method="post" >
				</c:if>

				<caption>
					<h2 class="text-center ">
						<c:if test="${frais != null}">
            			Modification de Frais
            		</c:if>
						<c:if test="${frais == null}">
            			Ajouter un nouveau Frais
            		</c:if>
					</h2>
				</caption><br>
				
				
				
				<fieldset class="form-group" >
					<label >Identifient frais : </label> 
					<input type="text"
						value="<c:out value='${frais.idfrais}' />" 
						class="form-control"
						name="idfrais" 
						required="required"
					/>
				</fieldset>

				<fieldset class="form-group">
					<label>Montant1</label> 
					<input type="number" 
						value="<c:out value='${frais.montant1}' />" 
						class="form-control"
						name="montant1" 
						required="required"
					/>
				</fieldset>

				<fieldset class="form-group">
					<label>Montant2</label> 
					<input type="number"
						value="<c:out value='${frais.montant2}' />" 
						class="form-control"
						name="montant2"
						required="required"
					/>
				</fieldset>
				
				<fieldset class="form-group">
					<label>Frais</label> 
					<input type="number"
						value="<c:out value='${frais.frais}' />" 
						class="form-control"
						name="frais"
						required="required"
					/>
				</fieldset>
				<br>

				<button type="submit" class="btn btn-success">Enregistrer</button>
				<button type="reset" class="btn btn-danger">Annuler</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>