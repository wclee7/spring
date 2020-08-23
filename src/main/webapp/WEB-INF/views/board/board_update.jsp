<%-- 
    Document   : notice_list
    Created on : 2017. 8. 7, 오후 3:10:05
    Author     : Devel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet"  type="text/css" href="/she/resources/css/board/board_update.css" />
        <link rel="stylesheet"  type="text/css" href="/she/resources/css/libs/bootstrap/bootstrap.min.css" />

        <script src="/she/resources/js/libs/jquery/jquery-1.11.1.min.js" ></script>
        <script src="/she/resources/js/libs/jquery/jquery.validate.js" ></script>
        <script src="/she/resources/js/board/board_update.js" ></script>
        <script src="/she/resources/js/common/ajax.js" ></script>

    </head>
    <body>
        <div class="container">
            <form id="nUpdateForm">
                <div class="header">
                    <h1>게시판 수정</h1>
                    <hr>
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
                    <input type="button" value="취소" onclick="location.href = '/she/board/list'"/>
                </div>
            </form>
        </div>
    </body>
</html>
