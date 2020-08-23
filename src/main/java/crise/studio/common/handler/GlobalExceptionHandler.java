package crise.studio.common.handler;

import crise.studio.common.util.ModelAndViewUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class})
    public Object noHandlerFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException e) throws IOException {
        if (e.getRequestURL().startsWith("/bnd")) {
            return ModelAndViewUtils.getExceptionModelAndRequestAcceptView(request, HttpStatus.NOT_FOUND, "요청 URL 이 존재하지 않습니다.");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

}

