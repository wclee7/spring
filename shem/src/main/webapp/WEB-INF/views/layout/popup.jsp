<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
        <div id="main-container" class="container fixed" <c:if test="${editMode eq 'popup'}"> style="padding-top: 0px"</c:if> >
                <tiles:insertAttribute name="contentJsp" />
        </div>
        <div id="modalAlert" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body" ></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>