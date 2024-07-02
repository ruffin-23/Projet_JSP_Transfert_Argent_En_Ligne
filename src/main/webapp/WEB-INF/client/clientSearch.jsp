<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <c:forEach var="client" items="${listeClient}">
            <tr class="align-middle text-center">
                <td><c:out value="${client.numtel}" /></td>
                <td><c:out value="${client.nom}" /></td>
                <td><c:out value="${client.sexe}" /></td>
                <td><c:out value="${client.pays}" /></td>
                <td><c:out value="${client.solde}" /></td>
                <td><c:out value="${client.mail}" /></td>
                <td>
                    <a href="edit?numtel=<c:out value='${client.numtel}' />" class="btn btn-primary btn-sm">Modifier</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?numtel=<c:out value='${client.numtel}' />" class="btn btn-danger btn-sm">Supprimer</a>
                    <a href="readById?numtel=<c:out value='${client.numtel}' />" class="btn btn-warning btn-sm">Voir</a>
                </td>
            </tr>
        </c:forEach>
