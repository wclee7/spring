<%-- 
    Document   : groupEdit
    Created on : 2015. 7. 12, 오전 3:44:11
    Author     : HK
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span>
        <c:if test="${editMode == 'insert'}"> 권한그룹 등록</c:if>
        <c:if test="${editMode == 'update'}"> 권한그룹 상세 (수정)</c:if>
        </h4>
    </div>

    <form method="post" id="editForm" action="${pageContext.request.contextPath}/bnd/master/adminGroup/${editMode}">

    <div class="panel panel-default">
        <div class="panel-heading panel-heading-custom1">운영자 정보</div>
        <div class="panel-body edit-fields">
            <fieldset>
                <div class="column-wrapper">
                    <div class="col-xs-6">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>권한그룹 ID</label>
                            </div>
                            <div class="col-xs-8">
                                <input type="text" id="id" name="id" class="form-control" value="${adminGroup.id}" readonly placeholder="ID는 자동 생성됩니다."/>
                            </div>
                        </div>

                    </div>
                    <div class="col-xs-6">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>권한그룹 명</label>
                            </div>
                            <div class="col-xs-8">
                                <input type="text" id="name" name="name" class="form-control" value="${adminGroup.name}" maxlength="31"  />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12">
                        <div class="row input-field">
                            <div class="col-xs-2 label-wrapper">
                                <label>권한그룹 설명</label>
                            </div>
                            <div class="col-xs-10">
                                <input type="text" id="description" name="description" class="form-control" value="${adminGroup.description}" maxlength="255" />
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>
    <input type="hidden" id="ownMenuIds" name="ownMenuIds"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="panel panel-default edit-background">
    <form method="post" id="searchForm" onsubmit="return false;">
        <input type="hidden" id="adminGroupId" name="adminGroupId" value="${adminGroup.id}" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>    
    </form>
    <div class="panel-heading">권한 (메뉴) 정보</div>
    <div class="panel-body">
        <div>
            <table id="adminMenuGrid" class="table-jqgrid"></table>
            <div id="adminMenuGridPager"></div>
        </div>
    </div>
</div>
<c:if test="${editMode eq 'update'}">
    <div class="panel panel-default">
        <div class="panel-heading panel-heading-custom1">등록(수정)자 정보</div>
        <div class="panel-body edit-fields">
            <fieldset>
                <div class="column-wrapper">
                    <div class="col-xs-6 column-side-wrapper">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>등록(수정)자</label>
                            </div>
                            <div class="col-xs-8">
                                <input type="text" class="form-control" value="${adminGroup.lastUpdate.admin.name}" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 column-side-wrapper">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>등록(수정) 일시</label>
                            </div>
                            <div class="col-xs-8">
                                <fmt:parseDate var="updateTIme" value="${fn:replace(adminGroup.lastUpdate.datetime, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" type="both" />
                                <input type="text" maxlength="63" value="<fmt:formatDate value="${updateTIme}" pattern="yyyy.MM.dd HH:mm" />"disabled class="form-control" />
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>
</c:if>

<div class="action-buttons">
    <button type="submit" id="submitBtn" form="editForm" class="btn btn-primary btn-sm">
        <c:if test="${editMode == 'insert'}">등록</c:if>
        <c:if test="${editMode == 'update'}">수정</c:if></button>
    <button type="button" id="backstep" class="btn btn-danger btn-sm">이전</button>
</div>


