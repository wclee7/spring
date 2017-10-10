<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-xs-12 single-layout-alarm">
    <h1>${code}</h1>
    <c:forEach var="message" items="${messages}" varStatus="status">
        <c:choose>
            <c:when test="${status.first}">
                <h2>${message}</h2>
            </c:when>
            <c:otherwise>
                <c:if test="${status.index eq 1}"><h3>(</c:if><c:if test="${status.index ne 1}">, </c:if>${message}<c:if test="${status.last}">)</h3></c:if>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>


