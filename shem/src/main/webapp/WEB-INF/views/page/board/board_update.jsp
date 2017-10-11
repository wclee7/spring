<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/shem/resources/js/board/board_update.js" ></script>
<script src="/shem/resources/js/common/ajax.js" ></script>
<form id="nUpdateForm">
    <br/><br/>
    <div class="page-header">
        <h4 page-title><span class="glyphicon glyphicon-ok"></span>게시판 수정</h4>
    </div>
    <div class="middle">
        <div class="edit_div">
            <input type="hidden" value="${board.id}" name="id" id="id">
            <div id="detail_top"><span>작성자 : 테스터</span><span>작성일 : ${board.simpleRegDate}</span></div>
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요."value="${board.title}"/>
            <textarea  id="content" class="form-control" name="content" rows="15" >${board.content}</textarea>
        </div>
    </div>
    <div class="bottom">
        <input type="submit" value="수정" id="updateBtn"/>
        <input type="button" value="취소" onclick="location.href = '/shem/bnd/board/list'"/>
    </div>
</form>
