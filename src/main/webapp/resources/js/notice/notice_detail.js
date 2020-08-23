
$(function () {
    $("#deleteBtn").click(function () {
        var data = {
            noticeId : $("#noticeId").val()
        };
        
        var ajaxData = {
            url: '/SpringHibernamteExample/notice/delete',
            type: 'post',
            data: $.param(data),
            success: function (data) {
                alert(data.message);
                if(data.code==="N200")
                    location.href="/SpringHibernamteExample/notice/list";
            }
        };
        
        sendAjax(ajaxData);
    });
});