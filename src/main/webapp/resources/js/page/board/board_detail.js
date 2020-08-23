
$(function () {
    $("#deleteBtn").click(function () {
        var data = {
            boardId : $("#boardId").val()
        };
        
        var ajaxData = {
            url: '/she/bnd/board/delete',
            type: 'post',
            data: $.param(data),
            success: function (data) {
                alert(data.message);
                if(data.code==="N200")
                    location.href="/she/bnd/board/list";
            }
        };
        
        sendAjax(ajaxData);
    });
});