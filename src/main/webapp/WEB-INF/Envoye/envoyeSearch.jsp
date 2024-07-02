<%@page import="java.util.List"%>
<%@page import="com.transferArgent.model.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

        <c:forEach var="list" items="${ listeEnv }">
             <tr class="align-middle text-center">
                 <td><c:out value="${list.idEnv}" /></td>
                 <td><c:out value="${list.numEnvoyeur}" /> </td>
                 <td><c:out value="${list.numRecepteur}" /> </td>
                 <td><c:out value="${list.montant}" /></td>
                 <td>
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
                        
                        
                        
                        