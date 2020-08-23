<%-- 
    Document   : notice_list
    Created on : 2017. 8. 7, 오후 3:10:05
    Author     : Devel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="/she/resources/js/libs/jquery/jquery-1.11.1.min.js" ></script>
        <script src="/she/resources/js/board/board_list.js" ></script>
        
        <link rel="stylesheet"  type="text/css" href="/she/resources/css/board/board_list.css" />
        <link rel="stylesheet"  type="text/css" href="/she/resources/css/libs/bootstrap/bootstrap.min.css" />


    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>게시판 리스트</h1>
                <hr>

            </div>
            <div class="middle">
                <div class="board_list">
                    <table class="table-hover table">
                        <thead>
                            <tr>
                                <td class="n_number">번호</td>
                                <td class="n_title">제목</td>
                                <td class="n_regDate">작성일</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="board" items="${boardList}" varStatus="status">
                                <tr onclick="noticeDetail(${board.id});">
                                    <td>${nListSize - status.index}</td>
                                    <td>${board.title}</td>
                                    <td>${board.simpleRegDate}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="bottom">
                <input type="button" width="100px;" class="form-control" value="게시물 작성" onclick="location.href = '/she/notice/save'"/>
            </div>
        </div>

    </body>
</html>
