<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/she/resources/js/common/ajax.js" ></script>

<form id="nSaveForm" method="post">
    <br/><br/>
    <div class="page-header">
        <h4 page-title><span class="glyphicon glyphicon-ok"></span>게시판 저장</h4>
    </div>         <div class="middle">
        <div class="edit_div">
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요." />
            <textarea  class="form-control" id="content" name="content" rows="15"></textarea>
            <input type="text" class="form-control" id="writer" name="writer" placeholder="작성자." />
            <input type="password" class="form-control" id="bpass" name="bpass" placeholder="패스워드." />  
        </div>

    </div>
    <div class="bottom">
        <input type="submit" value="저장" id="saveBtn" />
        <input type="button" value="취소" id="cancelBtn" onclick="location.href = '/she/free/bbs/list'"/>
    </div>
</form>
