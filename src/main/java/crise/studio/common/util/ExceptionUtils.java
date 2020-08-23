package crise.studio.common.util;

import crise.studio.common.exception.NotExistException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ExceptionUtils {

    private ExceptionUtils(){ }

    /**
     * <pre>
     *     불린 표현식에 따라 존재 하지 않음 오류를 발생시킨다.
     * </pre>
     *
     * @param isError 불린 표현식
     * @param message 오류 메세지
     * @throws NotExistException 존재 하지 않음 오류
     */
    public static void notExistException(boolean isError, String message) {
        if (isError) {
            throw new NotExistException(message);
        }
    }

    /**
     * <pre>
     *     객체 가 null 과 같다면 존재 하지 않음 오류를 발생시킨다.
     * </pre>
     *
     * @param o 객체
     * @param message 오류 메세지
     * @throws NotExistException 존재 하지 않음 오류
     */
    public static void notExistException(Object o, String message) {
        notExistException( Objects.isNull(o), message );
    }

    /**
     *
     * @param errorPredicate : 에러 표현식
     * @param target
     * @param message
     */
    public static void notExistExceptionWithIntPredicate(Predicate<Integer> errorPredicate, Integer target, String message) {
        notExistException(errorPredicate.test(target), message);
    }

    /**
     *
     * @param checkObj
     * @param klazz
     * @param message
     */
    public static <T> T invalidInstanceException(Object checkObj, Class<?> klazz, String message) {
        notExistException( ! klazz.isInstance(checkObj), message);
        return (T) checkObj;
    }

}
