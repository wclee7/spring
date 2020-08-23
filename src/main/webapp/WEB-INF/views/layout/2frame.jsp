<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html>
    <head>
        <meta http-equiv="Cache-Control" content="no-cache">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title><tiles:insertAttribute name="title"/></title>

        <tiles:insertAttribute name="head-tag" />


        <!-- Content JavaScript Module -->
        <script src="${pageContext.request.contextPath}<tiles:insertAttribute name="contentJs"/>"></script>


    </head>
    <body>
        <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />
        <tiles:insertAttribute name="header" />

        <div id="main-container" class="container fixed"  >

            <!--Sidebar-->
<%--            <div id="sidebar" class="col-xs-2">
                <c:set var="currentUri" value="${fn:substringAfter(requestScope['javax.servlet.forward.request_uri'], pageContext.request.contextPath)}" />

                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.menus" var="primeMenus"></sec:authentication>
                    <c:forEach items="${primeMenus}" var="menu">
                        <c:if test="${fn:startsWith(currentUri, menu.url)}">
                            <ul class="nav nav-pills nav-stacked side-menu-list">
                                <c:if test="${!empty menu.children}">
                                    <c:forEach items="${menu.children}" var="submenu" varStatus="subStat">
                                        <li>
                                            <a class="side-menu <c:if test='${fn:contains(currentUri, submenu.url)}'>active</c:if>" href="${pageContext.request.contextPath}${submenu.url}">${submenu.name}</a>
                                            </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </c:if>
                    </c:forEach>
                </sec:authorize>
            </div> --%>

            <div id="content" class="col-xs-10" >
                <tiles:insertAttribute name="contentJsp" />
            </div>

        </div>

        <div class="col-xs-12">
            <tiles:insertAttribute name="footer" />
        </div>


        <div id="modalAlert" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="confirmButton" disabled>진행</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
