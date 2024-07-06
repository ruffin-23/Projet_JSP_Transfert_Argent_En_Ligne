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
        <div class="card-body">
            <c:if test="${client != null}">
                <form action="update" method="post">
            </c:if>
            <c:if test="${client == null}">
                <form action="insert" method="post">
            </c:if>

            <caption>
                <h2 class="text-center">
                    <c:if test="${client != null}">
                        Modification de client
                    </c:if>
                    <c:if test="${client == null}">
                        Ajouter un nouveau client
                    </c:if>
                </h2>
            </caption><br>

            <fieldset class="form-group">
                <label>Numero téléphone</label>
                <input type="tel"
                       value="<c:out value='${client.numtel}' />"
                       class="form-control"
                       name="numtel"
                       required="required">
            </fieldset>

            <fieldset class="form-group">
                <label>Nom</label>
                <input type="text"
                       value="<c:out value='${client.nom}' />"
                       class="form-control"
                       name="nom"
                       required="required">
            </fieldset>

            <fieldset class="form-group">
                <label>Sexe</label>
                <select class="form-control" name="sexe">
                    <c:choose>
                        <c:when test="${client != null && client.sexe == 'Homme'}">
                            <option value="Homme" selected>Homme</option>
                        </c:when>
                        <c:when test="${client != null && client.sexe == 'Femme'}">
                            <option value="Femme" selected>Femme</option>
                        </c:when>
                        <c:when test="${client != null && client.sexe == 'Autre'}">
                            <option value="Autre" selected>Autre</option>
                        </c:when>
                        <c:otherwise>
                            <option value="" selected disabled>Sélectionnez le sexe</option>
                        </c:otherwise>
                    </c:choose>
                    <option value="Homme">Homme</option>
	                <option value="Femme">Femme</option>
	                <option value="Autre">Autre</option>
                </select>
            </fieldset>

            <fieldset class="form-group">
                <label>Pays</label>
                <select class="form-control" name="pays">
                    <c:choose>
                        <c:when test="${client != null && client.pays == 'Madagascar'}">
                            <option value="Madagascar" selected>Madagascar</option>
                        </c:when>
                        <c:when test="${client != null && client.pays == 'France'}">
                            <option value="France" selected>France</option>
                        </c:when>
                        <c:when test="${client != null && client.pays == 'Angleta'}">
                            <option value="Allemagne" selected>Allemagne</option>
                        </c:when>
                        <c:otherwise>
                            <option value="" selected disabled>Sélectionnez un pays</option>
                        </c:otherwise>
                    </c:choose>
                    <option value="Madagascar">Madagascar</option>
			        <option value="France">France</option>
			        <option value="Allemagne">Allemagne</option>
                </select>
            </fieldset>

            <fieldset class="form-group">
                <label>Solde</label>
                <input type="number"
                       value="<c:out  value='${client.solde}' />"
                       class="form-control"
                       name="solde">
            </fieldset>

            <fieldset class="form-group">
                <label>Email</label>
                <input type="text"
                       value="<c:out value='${client.mail}' />"
                       class="form-control"
                       name="mail">
            </fieldset><br>

            <button type="submit" class="btn btn-success">Enregistrer</button>
            <button type="reset" class="btn btn-danger">Annuler</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
