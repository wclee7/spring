
$(function () {
    $("#nSaveForm").validate({
        rules: {
            title: {required: true},
            content: {required: true}
        },
        messages: {
            title: {
                required: "제목을 입력해 주십시오."
            },
            content: {
                required: "내용을 입력해 주십시오."
            }
        },
        submitHandler: function (form) {
            
            var ajaxData = {
                url: '/she/bnd/board/save',
                type: 'post',
                data: $(form).serialize() ,
                success: function (data) {
                    alert(data.message);
                    if (data.code === "N200")
                        location.href = "/she/bnd/board/list";
                }
            };
            sendAjax(ajaxData);
        }
    });
});