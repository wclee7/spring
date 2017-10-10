<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="/she/resources/js/page/board/board_list.js" ></script>
<div class="row input-field row-full-height">
    <div class="col-xs-2 label-wrapper" style="height : 105px;">
        <label>게시판 리스트</label>
    </div>
    <div class="col-xs-10 full-height" >
        <table class="table entity-meta col-xs-12">
            <thead>
                <tr>
                    <td class="n_number">번호</td>
                    <td class="n_title">제목</td>
                    <td class="n_regDate">작성일</td>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${boardList}" var="boardTO" varStatus="status">
                <tr onclick="boardDetail(${boardTO.id})">
                    <td>${nListSize - status.index}</td> 
                    <td>${boardTO.title}</td>
                    <td>${boardTO.regDate}</td>
                </tr>
            </c:forEach> 
        </table>
        <!--<table class="table entity-function col-xs-12">-->
        <div class="search-buttons">
            <input type="button" width="100px;" class="form-control" value="게시물 작성" onclick="location.href = '/she/bnd/board/save'"/>
        </div>
        <script>
                                                    console.log("${boardList}");
        </script>    
    </div>
</div>