package crise.studio.common.handler;

import crise.studio.common.exception.*;
import crise.studio.model.VO.ErrorInformationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(basePackages = {"crise.studio.controller.api"})
public class ApiExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String INVALID_ERROR_CODE = "-1000";

//    @ExceptionHandler({
//            AlreadyClosedTvaLifecycle.class,
//            AlreadyClosedVodPlay.class,
//            NotEqualsHistoryStbId.class,
//            AlreadyParticipatedUser.class
//    })
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorInformationVO badRequestCase(RuntimeException e) {
//        return getErrorInformation(e);
//    }

    @ExceptionHandler({
               NotExistException.class
                })
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformationVO notFoundCase(RuntimeException e) {
        return getErrorInformation(e);
    }

    private ErrorInformationVO getErrorInformation(RuntimeException e) {

        ErrorInformationVO error = new ErrorInformationVO();

        if (e instanceof CustomException) {
            error.setErrorCode(((CustomException) e).getErrorCode());
        }
        error.setMessage(e.getMessage());

        return error;
    }

//      @ExceptionHandler(InvalidRequestException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorInformationVO parameterValidationException(RuntimeException ex, WebRequest request) {
//
//        InvalidRequestException ire = (InvalidRequestException) ex;
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("[").append(ire.getMessage()).append("]:");
//        for (FieldError fieldError : ire.getErrors().getFieldErrors()) {
//            logger.debug("-- InvalidRequestException[code] : {}", fieldError.getCode());
//            logger.debug("-- InvalidRequestException[defaultMessage] : {}", fieldError.getDefaultMessage());
//            builder.append(fieldError.getDefaultMessage()).append(" ");
//        }
//
//        return new ErrorInformationVO(INVALID_ERROR_CODE, builder.toString());
//    }
}

