<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize access="isAnonymous()">

    <div class="wrapper login-wrapper">
        <div class="col-xs-6 col-xs-offset-3 login-header">
            <img src ="${pageContext.request.contextPath}/resources/images/login-title.png">
            <!--    <h1>로그인 <small>Back Office Login</small></h1>-->
        </div>
        <!-- col-xs-6 col-xs-offset-3 -->
        <div class="col-xs-6 col-xs-offset-3 login-input-pannel">
            <div class="col-xs-10 col-xs-offset-1 login-form">
                <form id="loginForm" action="<c:url value='/login' />" method="post" >
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon glyphicon glyphicon-user"> </span>
                        <input type="text" name="account" class="form-control" placeholder="아이디">
                    </div>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon glyphicon glyphicon-lock"> </span>
                        <input type="password" name="password" class="form-control" placeholder="비밀번호">
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div>
                        <button type="submit" form="loginForm" class="btn btn-login btn-lg btn-block">Login</button>
                    </div>
                </form>
                <c:if test="${not empty error}">
                    <div class="login-msg error alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="login-msg msg alert alert-success">${msg}</div>
                </c:if>
            </div>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            이미 로그인 되어 있습니다.
        </sec:authorize>
    </div>
</div>