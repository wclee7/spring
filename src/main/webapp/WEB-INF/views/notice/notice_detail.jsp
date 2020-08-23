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
        <link rel="stylesheet"  type="text/css" href="/SpringHibernamteExample/resources/css/notice/notice_detail.css" />
        <link rel="stylesheet"  type="text/css" href="/SpringHibernamteExample/resources/css/libs/bootstrap/bootstrap.min.css" />

        <script src="/SpringHibernamteExample/resources/js/libs/jquery/jquery-1.11.1.min.js" ></script>
        <script src="/SpringHibernamteExample/resources/js/notice/notice_detail.js" ></script>
        <script src="/SpringHibernamteExample/resources/js/common/ajax.js" ></script>

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
                <h1>공지사항 상세</h1>
                <hr>
            </div>
            
            <div class="middle">
                <div class="detail_div">
                    <div id="detail_top"><span>작성자 : 테스터</span><span>작성일 : ${notice.simpleRegDate}</span></div>
                    <p class="form-control">${notice.title}</p>
                    <div  class="form-control content">${notice.content}
                    </div>
                </div>

            </div>
            <div class="bottom">
                <input id="deleteBtn" type="button" value="삭제" />
                <input type="button" value="수정" onclick="location.href = '/SpringHibernamteExample/notice/update/${notice.id}'"/>
                <input type="button" value="목록" onclick="location.href = '/SpringHibernamteExample/notice/list'"/>
            </div>
        </div>
    </body>
</html>
