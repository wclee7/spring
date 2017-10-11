<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="/shem/resources/js/page/board/board_detail.js" ></script>
<script src="/shem/resources/js/common/ajax.js" ></script>
<br/><br/>
<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span>게시판 상세</h4>
</div> 
<div class="middle">
    <div class="detail_div">
        <div id="detail_top"><span>작성자 : 테스터</span><span>작성일 : ${board.simpleRegDate}</span></div>
        <p class="form-control">${board.title}</p>
        <div  class="form-control content">${board.content}
        </div>
        <p class="form-control">${board.regDate}</p>
        <p class="form-control">${board.hit}</p>                   
    </div>

</div>
<div class="bottom">
    <input id="deleteBtn" type="button" value="삭제" />
    <input type="button" value="수정" onclick="location.href = '/shem/bnd/board/update/${board.id}'"/>
    <input type="button" value="목록" onclick="location.href = '/shem/bnd/board/list'"/>
</div>
