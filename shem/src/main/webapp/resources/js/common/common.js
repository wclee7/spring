String.prototype.capitalizeFirstLetter = function () {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

/**
 * 
 * @returns {Boolean}
 */
String.prototype.parseToBoolean = function () {
    var lower;
    if ((typeof (this) === "string") || this instanceof String) {

        lower = this.toLowerCase();
    }
    switch (lower) {
        case "true":
        case "yes":
        case "1":
        case "y":
            return true;
        case "false":
        case "no":
        case "0":
        case "n":
        case null:
            return false;
        default:
            return Boolean(lower);
    }
};

Date.prototype.customFormat = function (formatString) {
    var YYYY, YY, MMMM, MMM, MM, M, DDDD, DDD, DD, D, hhh, hh, h, mm, m, ss, s, ampm, AMPM, dMod, th;
    var dateObject = this;
    YY = ((YYYY = dateObject.getFullYear()) + "").slice(-2);
    MM = (M = dateObject.getMonth() + 1) < 10 ? ('0' + M) : M;
    MMM = (MMMM = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"][M - 1]).substring(0, 3);
    DD = (D = dateObject.getDate()) < 10 ? ('0' + D) : D;
    DDD = (DDDD = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"][dateObject.getDay()]).substring(0, 3);
    th = (D >= 10 && D <= 20) ? 'th' : ((dMod = D % 10) == 1) ? 'st' : (dMod == 2) ? 'nd' : (dMod == 3) ? 'rd' : 'th';
    formatString = formatString.replace("#YYYY#", YYYY).replace("#YY#", YY).replace("#MMMM#", MMMM).replace("#MMM#", MMM).replace("#MM#", MM).replace("#M#", M).replace("#DDDD#", DDDD).replace("#DDD#", DDD).replace("#DD#", DD).replace("#D#", D).replace("#th#", th);

    h = (hhh = dateObject.getHours());
    if (h == 0)
        h = 24;
    if (h > 12)
        h -= 12;
    hh = h < 10 ? ('0' + h) : h;
    AMPM = (ampm = hhh < 12 ? 'am' : 'pm').toUpperCase();
    mm = (m = dateObject.getMinutes()) < 10 ? ('0' + m) : m;
    ss = (s = dateObject.getSeconds()) < 10 ? ('0' + s) : s;
    return formatString.replace("#hhh#", hhh).replace("#hh#", hh).replace("#h#", h).replace("#mm#", mm).replace("#m#", m).replace("#ss#", ss).replace("#s#", s).replace("#ampm#", ampm).replace("#AMPM#", AMPM);
}

var logger = log4javascript.getLogger();
loggerSetting('${log.level}', false);
var popUp;
var contextPath;
var grid;
var gridUrl;

/**
 * HTML 로딩 후 모든 페이지 공통적으로 적용해야 할 로직들
 */
$(document).ready(function () {

    $(document).ajaxSend(function (event, xhr, option) {
        var key = $("meta[name=_csrf_header").attr('content');
        var token = $('meta[name=_csrf]').attr('content');
        xhr.setRequestHeader(key, token);
        logger.debug('ajax send!');
    });
    //$("#sidebar").height($("#content").height());

    var textAreaObj = document.getElementsByTagName("textarea"), textareaIsFocus = false;

    for (var i in textAreaObj) {
        textAreaObj[i].onfocus = function (e) {
            textareaIsFocus = true;
        }

        textAreaObj[i].onfocusout = function () {
            textareaIsFocus = false;
        }
    }

    $(document).keydown(function (e) {
        key = e.keyCode;
        if (!textareaIsFocus && e.keyCode === 13) {
            if (gridUrl !== null) {
                var grids = $(".data-grid.enter-reload");
                if (grids.length === 1) {
                    $grid = $(grids);
                    reloadGrid(grids);
                }
                if (grids.length > 1) {
                    for (var i = 0; i < grids.length; i++) {
                        $grid = $(grids[i]);
                        reloadGrid($grid);
                    }
                }
            }
            popUp.getObject().modal('hide');
            return false;
        }
    });

    $.validator.addMethod(
            "myDate",
            function (value, element) {
                return value.match(/\d{4}[.]{1}\d{2}[.]{1}\d{2}\s\d{2}[:]{1}\d{2}/);
                //return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
            },
            "날짜 시간 값이 잘못 입력되었습니다."
            );
    $.validator.addMethod(
            "regex",
            function (value, element, regexp) {
                if (regexp.constructor != RegExp) {
                    regexp = new RegExp(regexp);
                } else if (regexp.global) {
                    regexp.lastIndex = 0;
                }
                return this.optional(element) || regexp.test(value);
            },
            "Please check your input."
            );


    //기본 Alert 형 팝업 객체 생성
    popUp = new Modal('modalAlert', '알림 메시지');
    contextPath = $("#contextPath").val(); //getContextPath();
    // 모든 form 의 ajax 지원을 위한 기본 옵션 적용
    //ajaxSubmitSetting();

    $('textarea').each(function () {
        $(this).html($(this).html().trim());
    });
    // datetime picker 기본 설정 적용
//    $('.datetime').datetimepicker({
//        locale: 'ko',
//        format: "YYYY.MM.DD HH:mm:ss"
//    });
//    $('.dateonly').datetimepicker({
//        locale: 'ko',
//        format: "YYYY.MM.DD"
//    });
    /**
     * 시작일 지정 datetime picker 의 날짜가 지정되면,
     * 짝을 이루는 종료일 지정 datetime picker 의 지정 범위가 시작일보다 빠른 날짜가 될 수 없도록 지정
     * 서로 짝이 되는 기간 datetime-picker 의 tag-id 는 ***OpenDatetime , ***CloseDatetime 을 사용하도록 한다.
     */
    $('.datetime.datetime-start').on('dp.change', function (e) {
        var srcId = $(this).attr('id');
        var tarId = srcId.replace('Start', 'Close');
        $('#' + tarId).data("DateTimePicker").minDate(e.date);
    });
    /**
     * 종료일 지정 datetime picker 의 날짜가 지정되면,
     * 짝을 이루는 시작일 지정 datetime picker 의 지정 범위가 종료일보다 늦은 날짜가 될 수 없도록 지정
     * 서로 짝이 되는 기간 datetime-picker 의 tag-id 는 ***OpenDatetime , ***CloseDatetime 을 사용하도록 한다.
     */
    $('.datetime.datetime-close').on('dp.change', function (e) {
        var srcId = $(this).attr('id');
        var tarId = srcId.replace('Close', 'Start');
        $('#' + tarId).data("DateTimePicker").maxDate(e.date);
    });

    var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="' + contextPath + '/resources/images/loadingimage.gif" />').appendTo(document.body).hide();
    $(window).ajaxStart(function () {
        loading.show();
    }).ajaxStop(function () {
        loading.hide();
    });
});

$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    addHttpStatusEventListener(jqxhr);
});

/**
 * 로거 초기화 세팅
 * @param {string} level DEBUG, INFO ....
 * @param {boolean} popupOn 팝업 로거 출력 여부
 * @returns {undefined}
 */
function loggerSetting(level, popupOn) {
    var consoleAppender = new log4javascript.BrowserConsoleAppender();
    switch (level) {
        case "INFO" :
            consoleAppender.setThreshold(log4javascript.Level.INFO);
            break;

        case "ERROR" :
            consoleAppender.setThreshold(log4javascript.Level.ERROR);
            break;

        default :
            consoleAppender.setThreshold(log4javascript.Level.DEBUG);
            break;
    }
    //consoleAppender.setThreshold(log4javascript.Level.)
    logger.addAppender(consoleAppender);
    if (popupOn) {
        var popupAppender = new log4javascript.PopUpAppender();
        var layout = new log4javascript.PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p - ### %m{0} %n");
        popupAppender.setLayout(layout);
        popupAppender.setWidth(1024);
        popupAppender.setHeight(768);
        logger.addAppender(popupAppender);
    }
}


function getContextPath() {
    var offset = location.href.indexOf(location.host) + location.host.length;
    var ctxPath = location.href.substring(offset, location.href.indexOf('/', offset + 1));
    return ctxPath;
}

function getXhrMessage(xhr, defaultMessage) {
    var message = defaultMessage || '';

    if (xhr.status === 401) {
        message = '사용권한이 없습니다.';
    } else if (xhr.status === 403) {
        message = '로그인 시간이 만료 되었습니다.';
    }

    if (xhr && xhr.responseJSON && xhr.responseJSON.messages) {
        message = $.isArray(xhr.responseJSON.messages) ? xhr.responseJSON.messages[0] : xhr.responseJSON.messages;
    }

    return message;
}

function addHttpStatusEventListener(xhr) {
    if (xhr && xhr.status === 403) {
        popUp.getObject().on('hidden.bs.modal', function () {
            location.href = contextPath + '/office/login';
        });
    }
}
function formSubmitAjax(form, afterUrl, customOption, callback) {

    var defaultOption = {
        beforeSubmit: function (formData, jqForm, options) {
            var queryString = $.param(formData);

        },
        complete: function (xhr) {
            logger.debug(xhr);
        },
        dataType: 'json',
        success: function (data, statusText, xhr) {

            logger.debug(xhr);
            logger.debug(data);
            var status = xhr.status;
            logger.debug(typeof status);
            logger.debug('### status code');
            logger.debug(status);
            logger.debug('### status text');
            logger.debug(statusText);
            logger.debug("### response body");
            logger.debug(data);

            popUp.getObject().on('hidden.bs.modal', function () {
                if (afterUrl) {
                    location.href = contextPath + afterUrl;
                }
            });
            popUp.notice("작업 성공");

            if (callback) {
                callback();
            }
        },
        error: function (xhr, statusText, errorThrown) {
            logger.debug('FAILED!!');
            logger.debug(statusText);
            logger.debug(xhr);
            popUp.error(getXhrMessage(xhr, statusText));
        }
    };
    var option;
    if (typeof (customOption) !== 'undefined') {
        option = $.extend({}, defaultOption, customOption);
    } else {
        option = defaultOption;
    }

    var $form = $(form);
    $form.ajaxSubmit(option);

}

function ajaxSubmit(targetUrl, customOption) {
    logger.debug('페이지 내 모든 form ajax 대응 작업');
    var defaultOption = {
        beforeSubmit: function (formData, jqForm, options) {
            var queryString = $.param(formData);
            logger.debug(formData);
            logger.debug(queryString);
        },
        dataType: 'json',
        success: function (responseText, statusText, xhr, $form) {
            logger.debug('### response text');
            logger.debug(responseText);
            logger.debug('### status text');
            logger.debug(statusText);
            if (responseText.isSuccess) {

                logger.debug(responseText.isSuccess);
                popUp.getObject().on('hidden.bs.modal', function () {
                    if (targetUrl) {
                        location.href = contextPath + targetUrl;
                    }
                });
                popUp.notice(responseText.msg);
            } else {
                logger.debug(responseText.isSuccess);
                popUp.error('작업 실패');
            }

        },
        error: function (xhr, statusText, errorThrown) {
            logger.debug('FAILED!!');
            logger.debug(statusText);
            popUp.error(getXhrMessage(xhr, statusText));
        }
    };
    var option;
    if (typeof (customOption) !== 'undefined') {
        option = $.extend({}, defaultOption, customOption);
    } else {
        option = defaultOption;
    }

    var forms = $('form');
    forms.ajaxSubmit(option);
}

/**
 * 파일을 입력받아 미리보기 이미지를 출력
 * @param {type} fileLoader 파일을 입력받은 <input> Element
 * @param {type} ctnId 입력받은 파일을 출력할 <img> 를 감쌀 컨테이너 DOM ID
 * @param {Object} validDimens 해상도 제한이 필요할 경우 width height 정보
 * @param {string} allowExt 한가지만 허용이 필요한 이미지 확장자 (jpg or png)
 */
function prefareImagePreview(fileLoader, ctnId, validDimens, allowExt) {

    logger.debug(fileLoader);
    logger.debug('preview div zone id : ' + ctnId);
    logger.debug($('#' + ctnId));
    var ctn = $('#' + ctnId);
    if (!fileLoader.files[0]) {
        ctn.empty();
        ctn.css('backgroundColor', '#FFF');
        return false;
    }

    var file = fileLoader.files[0];
    logger.debug("file type : " + file.type);

    var imageType;
    switch (allowExt) {
        case "jpg" :
            imageType = /image[/](jpeg|jpg)/;
            break;

        case "png" :
            imageType = /image[/](png)/;
            break;

        default :
            imageType = /image[/](jpeg|png|jpg)/;
            break;
    }

    if (!file.type.match(imageType)) {
        //ctn.empty();
        clearFileInputWithReplace(fileLoader);
        popUp.error('이미지 포멧을 다시 확인 하십시오');
        return false;
    } else if (file.size === 0) {
        //ctn.empty();
        clearFileInputWithReplace(fileLoader);
        popUp.error('파일을 읽는 중 오류 발생, 다시 시도해주십시오.');
        return false;
    }



    if (!window.FileReader) {
        //ctn.empty();
        ctn.css('lineHeight', ctn.height() + 'px');
        ctn.html('이미지 체크를 지원하지 않는 브라우저 입니다.<br>모던 브라우저를 이용해 주세요.');
        clearFileInputWithReplace(fileLoader);
        return false;
    }



    var reader = new FileReader();
    var image = new Image();
    reader.readAsDataURL(file);
    reader.onload = function (_file) {

        image.src = _file.target.result; // url.createObjectURL(file);
        image.onload = function () {

            var imageWidth = image.width;
            var imageHeight = image.height;
            if (validDimens) {
                if (validDimens.width === imageWidth && validDimens.height === imageHeight) {
                    appendImgToContainer(image, ctnId);
                } else {
                    //ctn.empty();
                    clearFileInputWithReplace(fileLoader);
                    popUp.error("이미지 Size 를 다시 확인 하십시오.");
                }
            } else {
                appendImgToContainer(image, ctnId);
            }
            //appendImgToContainer(image, ctnId);

        };
        image.onerror = function () {
            clearFileInputWithReplace(fileLoader);
            popUp.error('이미지 로딩 중 오류가 발생했습니다.');
            //ctn.html('미리보기 지원이 되지 않는 파일 형식입니다. (' + file.type + ')');
        };
    };
}


/**  * 이미지 객체와 출력을 위한 컨테이너 DOM ID 를 입력받아 컨테이저 크기와 이미지 크기를 고려하여 리사이징 후 출력
 * @param {type} image javascript image 객체.
 * @param {type} ctnId image 출력 <img> 태그를 감싸고 있는 DOM ID
 */
function appendImgToContainer(image, ctnId) {

    var ctn = $('#' + ctnId);
    var imageWidth = image.width;
    var imageHeight = image.height;
    var divWidth = ctn.width();
    var divHeight = ctn.height();
    logger.debug('image width : ' + imageWidth);
    logger.debug('image height : ' + imageHeight);
    logger.debug('prv div width : ' + divWidth);
    logger.debug('prv div height : ' + divHeight);
    ctn.empty();
    if (imageWidth > divWidth || imageHeight > divHeight) {
        console.log('case 1');
        var gap = imageHeight - divHeight;
        var resizedWidth = imageWidth * (1 - (gap / imageHeight));
        image.height = divHeight;
        image.width = resizedWidth;
        // 정배율이 아닌 이미지의 경우
        if (resizedWidth > divWidth) {
            var gap = resizedWidth - divWidth;
            var resizedHeight = divHeight * (1 - (gap / resizedWidth));
            console.log(resizedHeight);
            image.width = divWidth;
            image.height = resizedHeight;
        }

        changeBackground(ctn);
    } else if (imageWidth > divWidth) {
        console.log('case 2');
        var gap = imageWidth - divWidth;
        var resizedHeight = imageHeight * (1 - (gap / imageHeight));
        image.width = divWidth;
        image.height = resizedHeight;
        changeBackground(ctn);
    }

    console.log(image);

    ctn[0].appendChild(image);
    //ctn[0].appendChild($("<img>"))
    function changeBackground(domObj) {

        domObj.css('backgroundColor', '#FFF');
    }

}


/**
 * 
 * @param {type} $parentZone
 * @param {type} imageName
 * @param {type} order
 * @param {type} previewSrcUrl
 * @param {type} validDimens
 * @param {type} height
 * @param {type} allowExt
 * @param {type} playAllId : 이어보기 사용을 하지 않을시 이미지 업로드 비활성화
 * @param {type} sIsFormatedId : 엔티티 관리에서 편성여부가 Y인지 판단으로 이미지 삭제 버튼 비활성화
 * @returns {undefined}
 */
function appendImagePanel(
        $parentZone,
        imageName,
        order,
        previewSrcUrl,
        validDimens,
        height,
        allowExt,
        playAllId,
        sIsFormatedId
        ) {
    console.log("imageName : " + imageName);
    //var $parent = $(parent);
    //var originFilePath = $("#" + fileId).val();

    var $panel = $("<div>", {id: imageName + "ImagePanel" + order, class: "image-panel"}).css({width: "100%", height: height});
    var $previewDiv = $("<div>", {id: imageName + "ImagePreview" + "_" + order, class: "image-preview"});
    var $loadBtn = $("<button>", {type: "button", id: "loadBtn" + "_" + imageName + "_" + order, class: "btn btn-sm btn-file file-load btn-info", text: "찾기"})
            .click(function () {
                if (playAllId != null) {
                    var $oPlayAll = $('#' + playAllId);
                    if ($oPlayAll.length !== 0 && parseInt($oPlayAll.val()) === 0) {
                        var msgBody = $("<div>");
                        msgBody.append($('<p>', {text: '전체 이어보기 사용 여부를 확인해주세요'}));
                        popUp.error(msgBody, '알림 메시지');
                        return false;
                    }
                }

                var loadBtn = this;
                var $btnParent = $(this.parentElement);
                $btnParent.find(".file-input").click();
            });
    var $clearBtn = $("<button>", {type: "button", id: "clearBtn" + "_" + imageName + "_" + order, class: "btn btn-sm btn-file file-clear btn-warning", text: "삭제"}).click(function () {
        if (sIsFormatedId != null) {
            var sIsFormated = $('#' + sIsFormatedId).val();
            if (sIsFormated === 'Y') {
                var msgBody = $("<div>");
                msgBody.append($('<p>', {text: '편성이 되어있는 이미지는 삭제가 불가능합니다.'}));
                popUp.error(msgBody, '알림 메시지');
                return false;
            }
        }
        var $parent = $(this.parentElement);
        $parent.find(".file-cleared-array").val(order);
        $parent.find(".file-changed-array").val("");
        $previewDiv.empty();

        var sInputFileId = imageName + "Input_" + order;
        $('#' + sInputFileId).val('');
    });
    var $fileInput = $("<input>", {type: "file", id: imageName + "Input_" + order, name: imageName, class: "form-control file-input"}).css("display", "hidden").change(function () {
        var $parent = $(this.parentElement);
        //$parent.find("#" + fileId + order + "Clear").val(0);
        $parent.find(".file-cleared-array").val("");
        if (!this.files[0]) {
            //var $previewZone = $(this.parentElement).find('.image-preview');
            $previewDiv.empty();
            $previewDiv.append($("<img>", {src: previewSrcUrl}).css({"width": "100%"}));
            $parent.find(".file-changed-array").val("");
        } else {
            prefareImagePreview(this, $previewDiv.attr('id'), validDimens, allowExt);
            console.log(typeof (previewSrcUrl));
            console.log("previewSrcUrl : " + previewSrcUrl);
            if (typeof (previewSrcUrl) === "string" && previewSrcUrl.length > 0) {
                $parent.find(".file-changed-array").val(order);
            }
            //prefareImagePreview(this, $(this.parentElement).find('.image-preview').attr('id'), validDimens);
        }
    });
    //$fileInput.change(function(){});
    console.info("cleared" + imageName.capitalizeFirstLetter() + "No");
    console.log("CLEARED ID ::", "clearedArray" + "_" + imageName + "_" + order);
    var $clearCheck = $("<input>", {type: "hidden", id: "clearedArray" + "_" + imageName + "_" + order, class: "file-cleared-array", name: "cleared" + imageName.capitalizeFirstLetter() + "No"});
    var $changeCheck = $("<input>", {type: "hidden", id: "changedArray" + "_" + imageName + "_" + order, class: "file-changed-array", name: "changed" + imageName.capitalizeFirstLetter() + "No"});
    var $orderInput = $("<input>", {type: "hidden", class: "image-order", value: order});

    $panel
            .append($previewDiv)
            .append($fileInput)
            .append($clearCheck)
            .append($loadBtn)
            .append($clearBtn)
            .append($changeCheck)
            .append($orderInput);

    $parentZone.append($panel);

    console.log(previewSrcUrl);

    if ((previewSrcUrl !== null) && (typeof (previewSrcUrl) !== "undefined")) {
        if (previewSrcUrl.length > 0) {
            $previewDiv.append($("<img>", {src: previewSrcUrl}).css({"width": "100%"}));
        }
    }
}

/**
 * 
 * @param {type} imageName
 * @param {type} $target
 * @param {type} counter
 * @param {type} previewImgUrl
 * @param {type} validDimens
 * @param {type} height
 * @param {type} allowExt
 * @returns {undefined}
 */
function appendDynamicImagePanel(imageName, $target, counter, previewImgUrl, validDimens, height, allowExt, sClassName) {
    if (sClassName == null) {
        sClassName = 'col-xs-4';
    }
    var $wrapper = $("<div>", {id: imageName + "Wrap" + "_" + counter, class: sClassName + " dynamic-wrapper"});
    appendImagePanel($wrapper, imageName, counter, previewImgUrl, validDimens, height, allowExt);
    $target.append($wrapper);
}


/**
 * bootstrap modal 을 활용한 팝업 유형 객체 생성
 * html body 에 팝업으로 쓸 modal 형상을 선언한 후
 * 선언된 container id 를 parameter 로 하여 객체를 생성한 뒤 생성한 객체를 사용
 * 주의. 팝업 객체 생성은 언제나 html 이 모두 로딩된 다음에 행해야 한다.
 * ex.
 * var popup = new Modal('ctnId');
 * popup.error('팝업 내용');
 * @param {type} selectorId html 로 선언된 modal 형상의 컨테이너 id
 * @param {type} titleMsg optional. 생성할 팝업 객체의 출력 제목 설정
 */
function Modal(selectorId, titleMsg) {

    var ctn = $('#' + selectorId);
//  ctn.on(function (e) {
//    console.log(e);
//  });

    var content = ctn.find('.modal-content');
    var header = ctn.find('.modal-header');
    var footer = ctn.find('.modal-footer');
    var body = ctn.find('.modal-body');
    var title = ctn.find('.modal-title');
    var _titleMsg = titleMsg;
    var dialog = ctn.find('.modal-dialog');
    var confirmBtn = ctn.find("#confirmButton");

    this.error = function (msg, titleMsg) {

        if (titleMsg) {
            title.html(titleMsg);
        } else if (_titleMsg) {
            title.html(_titleMsg);
        } else {
            title.html('ERROR');
        }
        $(confirmBtn).css("display", "none").attr("disabled", true);
        header.css({'backgroundColor': '#A00000', 'color': 'white'});
        showModal(msg);
    };
    this.caution = function (msg, titleMsg) {
        if (titleMsg) {
            title.html(titleMsg);
        } else if (_titleMsg) {
            title.html(_titleMsg);
        } else {
            title.html('CAUTION');
        }
        $(confirmBtn).css("display", "none").attr("disabled", true);
        header.css({'backgroundColor': '#C9C23F', 'color': 'white'});
        showModal(msg);
    };
    this.notice = function (msg, titleMsg) {
        if (titleMsg) {
            title.html(titleMsg);
        } else if (_titleMsg) {
            title.html(_titleMsg);
        } else {
            title.html('NOTICE');
        }
        $(confirmBtn).css("display", "none").attr("disabled", true);
        header.css({'backgroundColor': '#3FC95A', 'color': 'white'});
        showModal(msg);
    };

    this.confirm = function (msg, titleMsg, afterConfirm) {
        if (titleMsg) {
            title.html(titleMsg);
        } else if (_titleMsg) {
            title.html(_titleMsg);
        } else {
            title.html('NOTICE');
        }
        $(confirmBtn).css("display", "inline").attr("disabled", false).click(function () {

            afterConfirm();
            closeModel();
            $(this).unbind("click");

        });
        header.css({'backgroundColor': '#C9C23F', 'color': 'white'});
        showModal(msg);

    };
    this.getObject = function () {
        return ctn;
    };
    function showModal(msg) {
        body.html(msg);
        adjustModalShape();
        ctn.modal('show');
    }

    function closeModel() {
        ctn.modal('hide');
    }

    function adjustModalShape() {
        if (ctn.hasClass('in') === false) {
            ctn.show();
        }

        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        var contentHeight = $(window).height() * 0.4;
        content.css('height', contentHeight);
        body.css('height', contentHeight - (header.outerHeight() + footer.outerHeight()));
        dialog.css
                ({
                    'margin-top': (windowHeight - dialog.height()) / 2,
                    'margin-left': (windowWidth - dialog.width()) / 2
                });
        if (ctn.hasClass('in') === false) {
            ctn.hide();
        }

    }

}


/**
 * sortorder, sortname, url, postdata, colNames, colModel, loadComplete, onCellSelect
 * @param {type} targetId
 * @param {type} customOption
 * @returns {unresolved}
 */
function initGrid(targetId, customOption) {

    var defaultOption = {
        datatype: 'json',
        mtype: 'POST',
        jsonReader: {
            root: '',
            total: 'sc.paging.totalPages',
            page: 'sc.paging.currentPage',
            records: 'sc.paging.totalRows',
            rows: 'sc.paging.rowsPerPage',
            repeatitems: false
        },
        loadError: function (xhr, status, error) {
            initGrid.prototype.commonListener.loadError(xhr, status, error);
        },
        loadonce: true,
        rownumbers: true,
        autowidth: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: $('#' + targetId + 'Pager'),
        viewrecords: true,
        sortable: true,
        height: '250',
        autoencode: true,
        shrinkToFit: false,
        rownumWidth: 50,
        gridview: true
    };
    var option = $.extend({}, defaultOption, customOption);
    logger.debug(option);
    var $gridObj = $("#" + targetId).jqGrid(option);
    return $gridObj;
}
initGrid.prototype = {
    commonListener: {
        /**
         * 
         * @param {type} data
         * @param {type} $grid
         * @returns {undefined}
         */
        loadComplete: function (data, $grid) {
            logger.debug('### Grid loadComplete common logic');
            logger.debug(data);
            logger.debug($grid);
            if ($grid.getGridParam('datatype') === 'json') {
                if ($grid.getGridParam('sortname') !== '') {
                    setTimeout(function () {
                        $grid.triggerHandler('reloadGrid');
                    }, 50);
                }
            }
        },
        /**
         * 
         * @param {type} xhr
         * @param {type} status
         * @param {type} error
         * @returns {undefined}
         */
        loadError: function (xhr, status, error) {
            logger.debug('### loadError common logic');
            logger.debug(xhr);
            logger.debug('response status code : ' + xhr.status);
            logger.debug('error msg : ' + error);
            popUp.error('데이터 로딩에 실패하였습니다.(' + getXhrMessage(xhr, xhr.status) + ')');
        }
    }
};

/**
 * 
 * @param {type} parentGridId
 * @param {type} popupGridId
 * @returns {undefined}
 */
function addParentGridDataFromPopupGridSelected(parentGridId, popupGridId) {
    var selectedData = [];

    var selectedIds = $("#" + popupGridId).getGridParam("selarrrow");
    var parentIds = opener.parent.$("#" + parentGridId).getDataIDs();

    for (var i in selectedIds) {
        console.log(parentIds.indexOf(selectedIds[i]));
        if (parentIds.indexOf(selectedIds[i]) < 0) {
            var data = $("#" + popupGridId).getRowData(selectedIds[i]);
            console.log(data);
            opener.parent.$("#" + parentGridId).addRowData(data.id, data);
        }
    }
}

/**
 * 
 * @param {type} parentIdInputId
 * @param {type} parentNameInputId
 * @param {type} data
 * @returns {undefined}
 */
function addEntityMetaInfoFromPopupGridSelected(popupGridId) {

    var data = $("#" + popupGridId).getRowData($("#" + popupGridId).getGridParam("selrow"));

    if (data) {
        opener.parent.$("#metaId").val(data["id"]);
        opener.parent.$("#metaName").val(data["name"]);
    } else {
        popUp.caution("엔티티로 등록할 메타를 선택해 주세요");
    }

}


/**
 * 사이드 메뉴 고정 길이 재적용
 * CSS 만으로 해결을 못하겠음. CSS 만으로 해결할 방법이 있으면 이 function 은 필요없음
 * @returns {undefined}
 */
function sideMenuSetWidth() {

    $(".sidemenu-list").each(function (index) {
        var th = $(this);
        th.width(th.parent().width());
    });
}

/**
 * String
 * @param {type} string
 * @returns {Boolean}
 */
function stringToBoolean(string) {
    if (typeof (string) === "string") {
        string = string.toLowerCase();
    }
    switch (string) {
        case "true":
        case "yes":
        case "1":
        case "y":
            return true;
        case "false":
        case "no":
        case "0":
        case "n":
        case null:
            return false;
        default:
            return Boolean(string);
    }
}


function reloadGrid(grid, $conditionForm) {

    jQuery(grid).jqGrid().setGridParam(
            {
                datatype: 'json',
                postData: $("#searchForm").serializeJSON(),
                url: gridUrl,
                page: 1
            }
    ).trigger("reloadGrid");
}


/**
 *
 * @param {type} errors
 * @returns {undefined}
 */
function invalidPopup(errors) {
    var msgBody = $("<div>");
    msgBody.append($('<p>', {text: '유효하지 않은 입력값이 존재합니다.'}));
    msgBody.append($('<p>', {text: '확인 후 다시 시도해 주십시오.'}));
//        var list = $('<ul>');
//        list.append($('<li>', {text: '이벤트 명'}));
//        list.append($('<li>', {text: '이벤트 기간(시작일)'}));
//        list.append($('<li>', {text: '이벤트 기간(종료일)'}));
//        list.append($('<li>', {text: '이벤트 설명'}));
//        msgBody.append(list);
    popUp.error(msgBody, '알림 메시지');
}



function clearFileInputWithReplace(fileLoader) {
    var $oldLoader = $(fileLoader);
    var $newLoader = $oldLoader.clone(true);
    $oldLoader.replaceWith($newLoader);
}

/**
 * 
 * @param {type} gridId
 * @returns {undefined}
 */
function moveUpSelectedGridRows(gridId) {

    var $grid = $("#" + gridId);

    var selectedIds = $grid.getGridParam("selarrrow");
    console.log("selectedId length : " + selectedIds.length);
    if (selectedIds.length > 1) {
        popUp.caution("하나의 항목만 선택 후 다시 시도해 주십시오");
    } else if (selectedIds.length === 0) {
        popUp.caution("순서를 조정할 항목을 선택해주십시오");
    } else {
        var totalIds = $grid.getDataIDs();
        var index = totalIds.indexOf(selectedIds[0]);

        if (index === 0) {
            popUp.caution("이미 최상단에 위치한 항목입니다");
        } else {
            var targetId = totalIds[index - 1];
            var data = $grid.getRowData(selectedIds[0]);
            $grid.delRowData(selectedIds[0]);
            $grid.addRowData(data.id, data, "before", targetId);
            $grid.setSelection(data.id);
        }
    }
}

/**
 * 
 * @param {type} gridId
 * @returns {undefined}
 */
function moveTopSelectedGridRows(gridId) {
    var $grid = $("#" + gridId);

    var selectedIds = $grid.getGridParam("selarrrow");
    console.log("selectedId length : " + selectedIds.length);
    if (selectedIds.length > 1) {
        popUp.caution("하나의 항목만 선택 후 다시 시도해 주십시오");
    } else if (selectedIds.length === 0) {
        popUp.caution("순서를 조정할 항목을 선택해주십시오");
    } else {
        var totalIds = $grid.getDataIDs();
        var index = totalIds.indexOf(selectedIds[0]);

        if (index === 0) {
            popUp.caution("이미 최상단에 위치한 항목입니다");
        } else {
            var targetId = totalIds[0];
            var data = $grid.getRowData(selectedIds[0]);
            $grid.delRowData(selectedIds[0]);
            $grid.addRowData(data.id, data, "before", targetId);
            //$grid.setSelection(data.id);
        }
    }
}

function moveLowestSelectedGridRows(gridId) {
    var $grid = $("#" + gridId);

    var selectedIds = $grid.getGridParam("selarrrow");
    console.log("selectedId length : " + selectedIds.length);
    if (selectedIds.length > 1) {
        popUp.caution("하나의 항목만 선택 후 다시 시도해 주십시오");
    } else if (selectedIds.length === 0) {
        popUp.caution("순서를 조정할 항목을 선택해주십시오");
    } else {
        var totalIds = $grid.getDataIDs();
        var index = totalIds.indexOf(selectedIds[0]);

        if (index === (totalIds.length - 1)) {
            popUp.caution("이미 최하단에 위치한 항목입니다");
        } else {
            var length = totalIds.length;
            var targetId = totalIds[length - 1];
            var data = $grid.getRowData(selectedIds[0]);
            $grid.delRowData(selectedIds[0]);
            $grid.addRowData(data.id, data, "after", targetId);
            //$grid.setSelection(data.id);
        }
    }
}

/**
 * 
 * @param {type} gridId
 * @returns {undefined}
 */
function moveDownSelectedGridRows(gridId) {

    var $grid = $("#" + gridId);

    var selectedIds = $grid.getGridParam("selarrrow");
    console.log("selectedId length : " + selectedIds.length);
    if (selectedIds.length > 1) {
        popUp.caution("하나의 항목만 선택 후 다시 시도해 주십시오");
    } else if (selectedIds.length === 0) {
        popUp.caution("순서를 조정할 항목을 선택해주십시오");
    } else {
        var totalIds = $grid.getDataIDs();
        var index = totalIds.indexOf(selectedIds[0]);

        if (index === (totalIds.length - 1)) {
            popUp.caution("이미 최하단에 위치한 항목입니다");
        } else {
            var targetId = totalIds[index + 1];
            var data = $grid.getRowData(selectedIds[0]);
            $grid.delRowData(selectedIds[0]);
            $grid.addRowData(data.id, data, "after", targetId);
            $grid.setSelection(data.id);
        }

    }
}

function removeSelectedGridRowFromGrid(gridId) {
    var $grid = $("#" + gridId);
    var selectedIds = $grid.getGridParam("selarrrow");

    if (selectedIds.length === 0) {
        popUp.caution("하나 이상의 항목을 선택 후 다시 시도해 주십시오");
    } else {
        $grid.resetSelection();
        for (var i in selectedIds) {
            $grid.delRowData(selectedIds[i]);
        }
    }
}


/**
 * 
 * @param {type} $select
 * @param {type} toSelectedValue
 * @param {type} optionUrl
 * @param {type} httpMethod
 * @param {type} postParam
 * @param {type} valueProperty
 * @param {type} afterSuccessExecute
 * @returns {undefined}
 */
function appendSelectionOption($select, toSelectedValue, optionUrl, httpMethod, postParam, valueProperty, afterSuccessExecute, ajaxData) {


    $.ajax({
        url: optionUrl,
        type: httpMethod,
        data: ajaxData,
        success: function (data, testStatus, xhr) {
            console.log(data);
            console.log("is Array? : " + Array.isArray(data));
            if (Array.isArray(data)) {
                console.log("Array Length : " + data.length);
                for (var i in data) {
                    var sOptionText = '';
                    switch (data[i][valueProperty]) {
                        case 'VOD' :
                            sOptionText = '영상';
                            break;
                        case 'TVA' :
                            sOptionText = '퀴즈';
                            break;
                        case 'FAVORVODS' :
                            sOptionText = '인기콘텐츠 형';
                            break;
                        default :
                            sOptionText = data[i]["name"];
                    }
                    var $option = $("<option>", {value: data[i][valueProperty], text: sOptionText});
                    if (toSelectedValue === data[i][valueProperty]) {
                        $option.attr("selected", "true");
                    }
                    $select.append($option);
                }
            }

            if (afterSuccessExecute) {
                console.log(afterSuccessExecute);
                afterSuccessExecute();
            }

        },
        error: function (xhr, textStatus, errorThrown) {
            popUp.error(textStatus);
        }

    });
}

function parseToBoolean(param) {
    var lower;
    if ((typeof (param) === "string") || param instanceof String) {

        lower = param.toLowerCase();
    }
    switch (lower) {
        case "true":
        case "yes":
        case "1":
        case "y":
            return true;
        case "false":
        case "no":
        case "0":
        case "n":
        case null:
            return false;
        default:
            return Boolean(lower);
    }
}

/**
 * 
 * @param {type} gridId
 * @param {type} exportFileName
 * @returns {undefined}
 * reference http://jsfiddle.net/dreaddymck/3kvm24hz/
 */
function exportData(gridId, title, exportFileName) {

    var grid = $("#" + gridId);
    var originalSortNum = grid.getGridParam("rowNum");

    grid.setGridParam({rowNum: 999999999999}).trigger("reloadGrid");

    var gridIds = grid.getDataIDs(); // Get all the ids in array

    var labels = grid.getGridParam("colNames");
    var models = grid.getGridParam("colModel");

    var titles = [];
    var hiddenProps = [];

    for (var i in models) {
        if (!models[i].hidden && ((models[i].name !== "rn") && models[i].name !== "cb")) {
            titles.push(labels[i]);
        }
        if (models[i].hidden) {
            hiddenProps.push(models[i].name);
        }
    }

    var obj = new Object();
    obj.count = gridIds.length;
    obj.titles = titles;

    if (obj.count) {
        obj.items = new Array();
        for (var i in gridIds) {
            var data = grid.getRowData(gridIds[i]);

            for (var hiddenIndex in hiddenProps) {
                if (data.hasOwnProperty(hiddenProps[hiddenIndex])) {
                    delete data[hiddenProps[hiddenIndex]];
                }
            }

            obj.items.push(data);
        }
        grid.setGridParam({rowNum: originalSortNum}).trigger("reloadGrid");
        JSONToCSVConvertor(obj, title, 1, exportFileName);
    } else {
        popUp.caution("다운로드 받을 내용이 없습니다.");
    }

}


/**
 * 
 * @param {type} json
 * @param {type} reportTitle
 * @param {type} isShowLabel
 * @param {type} exportFileName
 * @returns {undefined}
 * reference : http://jsfiddle.net/dreaddymck/3kvm24hz/
 * reference : http://stackoverflow.com/questions/18249290/generate-csv-for-excel-via-javascript-with-unicode-characters
 */
function JSONToCSVConvertor(json, reportTitle, isShowLabel, exportFileName) {

    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof json != 'object' ? JSON.parse(json) : json;
    var CSV = '';

    if (reportTitle) {
        CSV += reportTitle + "\r\n\r\n";
    }

    //This condition will generate the Label/Header
    if (isShowLabel) {
        var row = "";

        //This loop will extract the label from 1st index of on array
        for (var index in arrData.titles) {
            //Now convert each value to string and comma-seprated
            row += arrData.titles[index] + ',';
        }
        row = row.slice(0, -1);
        //append Label row with line break
        CSV += row + '\r\n';
    }

    //1st loop is to extract each row
    for (var i = 0; i < arrData.items.length; i++) {
        var row = "";
        //2nd loop will extract each column and convert it in string comma-seprated
        for (var index in arrData.items[i]) {
            row += '"' + arrData.items[i][index].replace(/(<([^>]+)>)/ig, '') + '",';
        }
        row.slice(0, row.length - 1);
        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {
        alert("Invalid data");
        return;
    }

    /*
     * 
     * FORCE DOWNLOAD
     * 
     */

    //this trick will generate a temp "a" tag
    var link = document.createElement("a");
    link.id = "lnkDwnldLnk";

    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);

    var csv = CSV;
    var csv2 = encodeURI(CSV);
    var blob = new Blob([csv], {type: 'text/csv;charset=utf-8;'});

    console.log(blob);

    var myURL = window.URL || window.webkitURL;

    //var csvUrl = myURL.createObjectURL(blob);
    var filename = exportFileName + ".csv";
    jQuery("#lnkDwnldLnk")
            .attr({
                'download': filename,
                'href': "data:text/csv;charset=utf-8,\uFEFF" + csv2
            });

    jQuery('#lnkDwnldLnk')[0].click();
    document.body.removeChild(link);
}

/**
 * 
 * @param {type} date
 * @param {type} type
 * @returns {unresolved}
 */
function convertDateToString(date, type) {

    var str;

    switch (type) {
        case "filename":
            str = DateFormatter.format(date, "Ymd_His");
            break;

        default :
            str = DateFormatter.format(date, "Y.m.d H:i:s");
            break;
    }

    return str
}

/**
 * 
 * @param {type} from
 * @param {type} target
 * @returns {undefined}
 */
function getIntervalDayBetweenDates(from, target) {
    var intervalMillis = from - target;

    var days = intervalMillis / 1000 / 60 / 60 / 24;

    return parseInt(days);
}

/**
 * 유효성 검사 추가
 * 한글, 영어, 숫자
 * @param {type} param1
 * @param {type} param2
 * @param {type} param3
 */
jQuery.validator.addMethod("lettersonly", function (value, element)
{
    return this.optional(element) || /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s]+$/i.test(value);
}, '');



/**
 * 음악 업로드 Panel 추가
 * 
 * musicPanel 아래에 file, 삭제유무, 업로드 버튼, 삭제버튼을 생성하고, 각각 이벤트 리스너를 삽입한다.
 * @param {type} musicPanel
 * @param {type} musicFileName
 * @param {type} musicPath
 * @param {type} order
 */
function appendNewMusicPanel(musicPanel, musicFileName, musicPath, order) {
    var musicFile = $("<input>", {type: "file", id: "musicFile", name: musicFileName, class: "form-control file-input"}).css("display", "hidden");
    var musicFileDeleted = $("<input>", {type: "hidden", id: "musicFileDeleted", name: "cleared" + musicFileName.capitalizeFirstLetter() + "No", class: "music-cleared-array"});
    var loadBtn = $("<button>", {type: "button", id: "musicLoadBtn", class: "btn btn-sm btn-file file-load btn-info col-xs-4", text: "찾아보기" , style : "margin-left : 50px;"});
    var clearBtn = $("<button>", {type: "button", id: "musicClearBtn", class: "btn btn-sm btn-file file-clear btn-warning col-xs-4", text: "삭제"});

    settingMusicEvent(musicFile, loadBtn, clearBtn, musicFileDeleted, musicPath);

    musicPanel.append(musicFile);
    musicPanel.append(loadBtn);
    musicPanel.append(clearBtn);
    musicPanel.append(musicFileDeleted);

}

/**
 * 음악 업로드 설정 셋팅
 * @param {type} musicFile
 * @param {type} loadBtn
 * @param {type} clearBtn
 * @param {type} musicFileDeleted
 * @param {type} musicPath
 * @param {type} order
 */
function settingMusicPanel(musicFile, loadBtn, clearBtn, musicFileDeleted, musicPath, order) {
    settingMusicEvent(musicFile, loadBtn, clearBtn, musicFileDeleted, musicPath);
}

function settingMusicEvent(musicFile, loadBtn, clearBtn, musicFileDeleted, musicPath) {

    musicFile.change(function () {
        var extList = ["mp3"];
        var msg = "mp3 파일만 업로드 하실 수 있습니다.";
        if (!this.files[0]) {
        } 
        else if (extensionCheck(this, extList, msg)) {
            musicPath.val(this.value);
            musicFileDeleted.val("0");//체크 완료
        } else {
            musicPath.val("");
            musicFile.val("");
            musicFileDeleted.val("1"); //확장자 불일치
        }
    });

    musicFile.attr("accept", ".mp3");

    loadBtn.click(function () {
        var $btnParent = $(this.parentElement);
        $btnParent.find(musicFile).click();
    });

    clearBtn.click(function () {
        musicFile.val("");
        musicPath.val("");
        musicFileDeleted.val("1");
    });

}
/*
 * 파일의 확장자가 지정한 확장자가 맞는지 검색한다.
 */
function extensionCheck(fileLoader, extensionList, msg) {
    var fileName = fileLoader.value //파일을 추가한 input 박스의 값

    var extension = fileName.slice(fileName.indexOf(".") + 1).toLowerCase(); //파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.
    for (var i = 0; i < extensionList.length; i++) {
        if (extension === extensionList[i]) {
            return true;
        }
    }
    popUp.error(msg);
    return false;
}

var downloadExcel = function (sExcelType, sFormSerialize) {
    var sContextPath = $('#contextPath').val();
    var sExcelDownBaseUrl = '/office/excel/';

    var sExcelDownUrl = sContextPath + sExcelDownBaseUrl + sExcelType + '?';
    location.href = sExcelDownUrl + sFormSerialize;
};