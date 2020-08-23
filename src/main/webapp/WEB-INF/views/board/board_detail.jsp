<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet"  type="text/css" href="/she/resources/css/notice/board_detail.css" />
        <link rel="stylesheet"  type="text/css" href="/she/resources/css/libs/bootstrap/bootstrap.min.css" />

        <script src="/she/resources/js/libs/jquery/jquery-1.11.1.min.js" ></script>
        <script src="/she/resources/js/notice/board_detail.js" ></script>
        <script src="/she/resources/js/common/ajax.js" ></script>

        <style>
            .content{
                height: 300px;
            }
        </style>


    </head>
    <body>
        <div class="container">
            <input type="hidden" id="noticeId" value="${notice.id}">
            <div class="header">
                <h1>게시판 상세</h1>
                <hr>
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
                <input type="button" value="수정" onclick="location.href = '/she/board/update/${board.id}'"/>
                <input type="button" value="목록" onclick="location.href = '/she/board/list'"/>
            </div>
        </div>
    </body>
</html>
