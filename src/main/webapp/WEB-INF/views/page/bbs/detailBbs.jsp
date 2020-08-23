<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="/she/resources/js/common/ajax.js" ></script>
<br/><br/>
<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span>게시판 상세</h4>
</div> 
<div class="middle">
    <div class="detail_div">
        <div id="detail_top"><span>작성자 : ${bbs.writer}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>작성일 : ${bbs.simpleRegDate}</span></div>
        <p class="form-control">${bbs.title}</p>
        <div  class="form-control content">${bbs.content}
        </div>
        <p class="form-control">${bbs.regDate}</p>
        <p class="form-control">${bbs.hit}</p>                   
    </div>

</div>
<div class="bottom">
<!--    <input id="deleteBtn" type="button" value="삭제" />-->
    <input type="button" value="삭제" onclick="location.href = '/she/free/bbs/bIdPwdChkFrm4del/${bbs.id}'"/>
<!--    <input type="button" value="수정" onclick="location.href = '/she/free/bbs/update/${bbs.id}'"/>-->
    <input type="button" value="수정" onclick="location.href = '/she/free/bbs/bIdPwdChkFrm/${bbs.id}'"/>
    <input type="button" value="목록" onclick="location.href = '/she/free/bbs/list'"/>
</div>
