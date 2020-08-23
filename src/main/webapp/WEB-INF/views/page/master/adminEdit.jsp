<%-- 
    Document   : adminEdit
    Created on : 2015. 7. 12, 오전 3:10:19
    Author     : HK
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span>
        <c:if test="${editMode == 'insert'}"> 운영자 등록</c:if>
        <c:if test="${editMode == 'update'}"> 운영자 상세 (수정)</c:if>
            <!--      <small>Subtext for header</small>-->
        </h4>
    </div>

    <form method="post" id="editForm" enctype="multipart/form-data" action="${pageContext.request.contextPath}/bnd/master/admin/${editMode}" onsubmit="return false;">

    <div class="panel panel-default edit-background">
        <div class="panel-heading panel-heading-custom1">운영자 정보</div>
        <div class="panel-body edit-fields">
            <fieldset>
                <div class="column-wrapper">
                    <div class="col-xs-6 column-side-wrapper">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>운영자 이름</label>
                            </div>
                            <div class="col-xs-8">
                                <input type="hidden" id="id" name="id" value="${admin.id}" />
                                <input type="hidden" id="adminGroupId"  name="adminGroup.id" value="${admin.adminGroup.id}"/>
                                <input type="text" id="name" name="name" class="form-control" value="${admin.name}" />
                            </div>
                        </div>
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>사용 여부</label>
                            </div>
                            <div class="col-xs-8">
                                <select name="isActive" id="isActive" class="form-control">
                                    <option value="1" <c:if test="${admin.isActive eq true}">selected</c:if> >Y</option>
                                    <option value="0" <c:if test="${admin.isActive eq false}">selected</c:if> >N</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row input-field">
                                <div class="col-xs-4 label-wrapper">
                                    <label>비밀번호</label>
                                </div>
                                <div class="col-xs-8">
                                    <input id="password" name="password" type="password" class="form-control"  maxlength="31" />
                                </div>
                            </div>
                            <div class="row input-field">
                                <div class="col-xs-4 label-wrapper">
                                    <label>전화번호</label>
                                </div>
                                <div class="col-xs-8">
                                    <input type="text" id="contact" name="contact" class="form-control" value="${admin.contact}" maxlength="15"  />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 column-side-wrapper">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>운영자 ID</label>
                            </div>
                            <div class="col-xs-8">
                                <div class="row">
                                    <div class="col-xs-6">
                                        <input type="text" id="account" name="account" maxlength="63" class="form-control" value="${admin.account}" <c:if test="${editMode eq 'update'}">readonly</c:if> />    
                                        </div>
                                        <div class="col-xs-6">
                                            <button type="button" id="accountOverapCheck" class ="col-xs-3 btn btn-default form-control" <c:if test="${editMode eq 'update'}">disabled</c:if> >중복 체크</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row input-field">
                                <div class="col-xs-12" style="height:35px" ></div>
                            </div>
                            <div class="row input-field">
                                <div class="col-xs-4 label-wrapper">
                                    <label>비밀번호 재확인</label>
                                </div>
                                <div class="col-xs-8">
                                    <input type="password" id="passwordConfirm" name="passwordConfirm" class="form-control" maxlength="31"  />
                                </div>
                            </div>
                            <div class="row input-field">
                                <div class="col-xs-4 label-wrapper">
                                    <label>E-Mail</label>
                                </div>
                                <div class="col-xs-8">
                                    <input type="text" id="email" name="email" class="form-control" value="${admin.email}"  maxlength="63" />
                            </div>
                        </div>

                    </div>
                </div>
            </fieldset>
        </div>
    </div>
</form>

<div class="panel panel-default edit-background table-grid">

    <form method="post" id="searchForm" onsubmit="return false;" >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <div class="panel-heading panel-heading-custom1">권한 그룹 정보</div>
    <div class="panel-body edit-fields">
        <div>
            <table id="adminGroupGrid" class="data-grid"></table>
            <div id="adminGroupGridPager"></div>
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
                                <input type="text" class="form-control" value="${admin.lastUpdate.admin.name}" disabled />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 column-side-wrapper">
                        <div class="row input-field">
                            <div class="col-xs-4 label-wrapper">
                                <label>등록(수정) 일시</label>
                            </div>
                            <div class="col-xs-8">
                                <fmt:parseDate var="updateTIme" value="${fn:replace(admin.lastUpdate.datetime, 'T', ' ')}" pattern="yyyy-MM-dd HH:mm" type="both" />
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
