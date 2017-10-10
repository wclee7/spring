<%-- 
    Document   : groupList
    Created on : 2015. 7. 12, 오전 3:43:27
    Author     : HK
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span> 권한 그룹 관리</h4>
</div>

<form method="post" id="searchForm" onsubmit="return false;">
    <div class="panel panel-default">
        <div class="panel-heading panel-heading-custom1">조회</div>
        <div class="panel-body">
            <div class="row">
                <div class="col-xs-6 search-condition" >
                    <div class="row">
                        <div class="col-xs-5" >
                            <li>
                                <label>권한그룹 명</label>
                            </li>
                        </div>
                        <div class="col-xs-7" >
                            <input type="text" id="name" name="name" class="form-control" placeholder="입력하세요">
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="selectedIds" name="selectedIds"/>
        </div>
    </div>
    <div class="search-buttons">
        <button type="button" form="conditionForm" id="searchBtn"class="btn btn-primary btn-sm">조회</button>
    </div>
</form>

<div class="panel panel-default table-grid">
    <div class="panel-heading panel-heading-custom1">권한그룹 목록</div>
    <div class="panel-body edit-fields">
        <table id="adminGroupGrid" class="data-grid enter-reload"></table>
        <div id="adminGroupGridPager"></div>
    </div>
</div>

<div class="action-buttons">
    <a href="${pageContext.request.contextPath}/bnd/master/adminGroup/insert" class="btn btn-primary btn-sm">권한그룹 등록</a>
    <button type="button" id="deleteAdminGroups" disabled class="btn btn-primary btn-sm">권한그룹 삭제</button>
</div>