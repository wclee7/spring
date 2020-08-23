
$(function () {
    $("#deleteBtn").click(function () {
        var data = {
            bbsId: $("#bbsId").val()
        };

        var ajaxData = {
            url: '/she/free/bbs/delete',
            type: 'post',
            data: $.param(data),
            success: function (data) {
                alert(data.message);
                if (data.code === "N200")
                    location.href = "/she/free/bbs/list";
            }
        };

        sendAjax(ajaxData);
    });
});