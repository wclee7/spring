<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="/she/resources/js/common/ajax.js" ></script>
<br/><br/>
<div class="page-header">
    <h4 page-title><span class="glyphicon glyphicon-ok"></span>게시판 아이디 패스워드 확인</h4>
</div> 
<form id="nChkForm">
<div class="middle">
    <div class="detail_div">
        <div class="edit_div">
          아이디 : <input type="text" class="form-control" id="writer" name="writer" placeholder="작성자 아이디를 입력해주세요." />
          비밀번호 : <input type="password" class="form-control" id="bpass" name="bpass" placeholder="비밀번호를 입력해주세요." />
          <input type="hidden" id="id" name="id" value="${bbsId}" />
        </div>
                      
    </div>

</div>
<div class="bottom">
    <input id="chkBtn" type="submit" value="체크" />
    <input type="button" value="목록" onclick="location.href = '/she/free/bbs/list'"/>
</div>
</form>
<script>
 
$(function () {
   
     $("#nChkForm").validate({
        rules: {
            writer: {required: true},
            bpass: {required: true}
        },
        messages: {
            writer: {
                required: "아이디를 입력해 주십시오."
            },
            bpass: {
                required: "비밀번호를 입력해 주십시오."
            }
        },
        submitHandler: function (form) {
            var ajaxData = {
                url: '/she/free/bbs/bIdPwdChk',
                type: 'post',
                data: $(form).serialize() ,
                success: function (data) {
                    alert(data.message);
                    if (data.code === "N200") {
                        location.href = "/she/free/bbs/update/${bbsId}";
                   } 
                }
            };
            sendAjax(ajaxData);
        }
    });
});
</script>