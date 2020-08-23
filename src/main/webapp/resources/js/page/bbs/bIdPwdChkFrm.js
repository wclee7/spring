//
//$(function () {
//     $("#nChkForm").validate({
//        rules: {
//            writer: {required: true},
//            bpass: {required: true}
//        },
//        messages: {
//            writer: {
//                required: "아이디를 입력해 주십시오."
//            },
//            bpass: {
//                required: "비밀번호를 입력해 주십시오."
//            }
//        },
//        submitHandler: function (form) {
//            
//            var ajaxData = {
//                url: '/she/free/bbs/bIdPwdChk',
//                type: 'post',
//                data: $(form).serialize() ,
//                success: function (data) {
//                    alert(data.message);
//                    if (data.code === "N200") {
//                        location.href = "/she/free/bbs/update/${bbs.id}";
//                    } else {
//                        $(form).cleanData;
//                    }
//                }
//            };
//            sendAjax(ajaxData);
//        }
//    });
//});