<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/she/resources/js/common/ajax.js" ></script>
<form id="nUpdateForm">
    <br/><br/>
    <div class="page-header">
        <h4 page-title><span class="glyphicon glyphicon-ok"></span>게시판 수정</h4>
    </div>
    <div class="middle">
        <div class="edit_div">
            <input type="hidden" value="${bbs.id}" name="id" id="id">
            <div id="detail_top"><span>작성자 : 테스터</span><span>작성일 : ${bbs.simpleRegDate}</span></div>
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요."value="${bbs.title}"/>
            <textarea  id="content" class="form-control" name="content" rows="15" >${bbs.content}</textarea>
            작성자 : <input type="text" class="form-control" id="writer" name="writer" placeholder="작성자를 입력해주세요."value="${bbs.writer}"/>
            비밀번호:  <input type="password" class="form-control" id="bpass" name="bpass" placeholder="비밀전호를 입력해주세요."value="${bbs.bpass}"/>
        </div>
    </div>
    <div class="bottom">
        <input type="submit" value="수정" id="updateBtn"/>
        <input type="button" value="취소" onclick="location.href = '/she/free/bbs/list'"/>
    </div>
</form>
