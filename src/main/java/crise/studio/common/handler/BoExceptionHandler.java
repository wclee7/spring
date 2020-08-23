package crise.studio.common.handler;

//import kr.co.tlab.cptv.common.exception.ExistRelationStbUserException;
import crise.studio.common.util.ModelAndViewUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice(basePackages = {"crise.studio.controller.bnd"})
public class BoExceptionHandler {

//    @ResponseBody
//    @ExceptionHandler(ExistRelationStbUserException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, Object> existRelationStbUserException(HttpServletRequest request, ExistRelationStbUserException e) {
//    	e.printStackTrace();
//        return ModelAndViewUtils.getAttributeMap(new String[]{"data"}, new Object[]{e.getExceptionStbModelIds()});
//    }

    @ExceptionHandler({Exception.class})
    public ModelAndView exception(HttpServletRequest request, Exception e) {
    	e.printStackTrace();
        return ModelAndViewUtils.getExceptionModelAndRequestAcceptView(request, HttpStatus.INTERNAL_SERVER_ERROR, "서버 작업중 오류가 발생하였습니다.");
    }

    @ExceptionHandler({DataAccessException.class})
    public ModelAndView dataAccessException(HttpServletRequest request, DataAccessException e) {
    	e.printStackTrace();
        return ModelAndViewUtils.getExceptionModelAndRequestAcceptView(request, HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 작업중 오류가 발생하였습니다.");
    }
}

