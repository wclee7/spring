package crise.studio.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotExistException extends CustomException{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String MSG_PREFIX = "요청한 ";

    private static final String MSG_POSTFIX = " 이(가) 존재하지 않습니다.";

    public NotExistException(String msg) {
    	super(MSG_PREFIX + msg + MSG_POSTFIX, "-1000");
        logger.error("### not exsist exception :: {}", MSG_PREFIX + msg + MSG_POSTFIX);
    }
    
}
