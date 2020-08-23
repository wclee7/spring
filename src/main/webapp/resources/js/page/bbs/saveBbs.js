
$(function () {
    $("#nSaveForm").validate({
        rules: {
            title: {required: true},
            content: {required: true},
            writer: {required: true},
            bpass: {required: true}
        },
        messages: {
            title: {
                required: "제목을 입력해 주십시오."
            },
            content: {
                required: "내용을 입력해 주십시오."
            },
             writer: {
                required: "작성자를 입력해 주십시오."
            },
             bpass: {
                required: "비밀번호를 입력해 주십시오."
            }
        },
        submitHandler: function (form) {
            
            var ajaxData = {
                url: '/she/free/bbs/save',
                type: 'post',
                data: $(form).serialize() ,
                success: function (data) {
                    alert(data.message);
                    if (data.code === "N200")
                        location.href = "/she/free/bbs/list";
                }
            };
            sendAjax(ajaxData);
        }
    });
});