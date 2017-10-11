<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<div class="navbar navbar-fixed-top navbar-inverse" id="site-nav" role="navigation">
    <div class="navbar-inner" >
        <div class="container fixed">

            <div class="row">

                <div class="col-xs-4" id="site-logo-row">
                    <!-- 250x60 -->                    
                </div>

                <div class="col-xs1 col-xs-offset-10">
                </div>
                <div class="col-xs1 col-xs-offset-11">
                    <sec:authorize access="isAuthenticated()">
                        <div class="navbar-text">
                            <form action="${pageContext.request.contextPath}/logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-primary btn-sm">Logout</button>
                            </form>
                        </div>
                    </sec:authorize>
                </div>
            </div>
            <%--            <div class="row" id="main-menu-nav">
                            <sec:authorize access="isAuthenticated()">
                                <sec:authentication property="principal.menus" var="primeMenus"></sec:authentication>
                                    <ul class="nav nav-pills" id = "main-menu-nav-list">
                                    <c:forEach items="${primeMenus}" var="menu">
                                        <li>
                                            <c:if test="${!empty menu.children}">
                                                <c:forEach items="${menu.children}" var="submenu" varStatus="subStat">
                                                    <c:if test="${subStat.first}">
                                                        <a class="nav nav-top" href="${pageContext.request.contextPath}${submenu.url}">${menu.name}</a>
                                                        <ul class="dropdown-menu nav-top" role="menu">
                                                        </c:if>
                                                        <li >
                                                            <a href="${pageContext.request.contextPath}${submenu.url}">${submenu.name}</a>
                                                        </li>

                                            <c:if test="${subStat.last}">
                                            </ul>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty menu.children}">
                                    <a class="nav nav-top" href="${pageContext.request.contextPath}">${menu.name}</a>
                                </c:if>

                            </li>
                            <li class="divider-vertical"></li>
                            </c:forEach>

                    </ul>
                </sec:authorize>
            </div> --%>
        </div>
    </div>
</div>


