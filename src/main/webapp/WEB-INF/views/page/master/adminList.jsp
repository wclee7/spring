<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span> 운영자 관리</h4>
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
                                <label>구분</label>
                            </li>
                        </div>
                        <div class="col-xs-7">
                            <div class="row">
                                <div class="col-xs-6">
                                    <select id="keywordType"  class="form-control">
                                        <option id="name" value="name" selected>운영자 이름</option>
                                        <option id="account" value="account">운영자 ID</option>
                                    </select>
                                </div>
                                <div class="col-xs-6">
                                    <input type="text" id="keywordValue" name="name"  class="form-control" placeholder="입력하세요" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6 search-condition" >
                    <div class="row">
                        <div class="col-xs-5" >
                            <li>
                                <label>권한그룹 명</label>
                            </li>
                        </div>
                        <div class="col-xs-7" >
                            <input type="text" id="adminGroupName" name="adminGroupName"  class="form-control" placeholder="입력하세요" />
                        </div>
                    </div>
                </div>
                <div class="col-xs-6 search-condition" >
                    <div class="row">
                        <div class="col-xs-5" >
                            <li>
                                <label>사용 여부</label>
                            </li>
                        </div>
                        <div class="col-xs-7" >
                            <select id="isActive" name="isActive" class="form-control">
                                <option value="" selected>전체</option>
                                <option value="1">Y</option>
                                <option value="0">N</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" id="selectedIds" name="selectedIds" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
    </div>
    <div class="search-buttons">
        <button type="button" form="conditionForm" id="searchBtn"class="btn btn-primary btn-sm">조회</button>
    </div>
</form>



<div class="panel panel-default table-grid">
    <div class="panel-heading panel-heading-custom1">권한그룹 목록</div>
    <div class="panel-body">
        <table id="adminGrid" class="data-grid enter-reload"></table>
        <div id="adminGridPager"></div>
    </div>
</div>

<div class="action-buttons">
    <a href="${pageContext.request.contextPath}/bnd/master/admin/insert" class="btn btn-primary btn-sm">운영자 등록</a>
    <button type="button" id="deleteAdmins" disabled class="btn btn-primary btn-sm">운영자 삭제</button>
</div>