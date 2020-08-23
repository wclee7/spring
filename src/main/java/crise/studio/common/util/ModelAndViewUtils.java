package crise.studio.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *     모델 앤 뷰 유틸 클래스
 * </pre>
 *
 * @author zuku
 * @since 1.1
 */
public class ModelAndViewUtils {

    private static final Map<MediaType, String> ACCEPT_VIEW_MAP;

    static {
        ACCEPT_VIEW_MAP = new HashMap<>(2);
        ACCEPT_VIEW_MAP.put(MediaType.APPLICATION_JSON, "jacksonJsonView");
        ACCEPT_VIEW_MAP.put(MediaType.APPLICATION_JSON_UTF8, "jacksonJsonView");
    }

    private ModelAndViewUtils() { }

    /**
     * <pre>
     *     요청 정보 동의 형식 뷰를 가진 오류 모델 앤 뷰를 가져온다.
     * </pre>
     *
     * @param request 요청 정보
     * @param httpStatus http 상태
     * @param messages 메세지 들
     * @return 요청 정보 동의 형식 뷰를 가진 오류 모델 앤 뷰
     */
    public static ModelAndView getExceptionModelAndRequestAcceptView(HttpServletRequest request, HttpStatus httpStatus, String... messages) {
        String exceptionView = getRequestAcceptView(request, "redirect:/bnd/error");
        if (exceptionView.startsWith("redirect:")) {
            return getModelAndViewWithSettingRedirect(request, exceptionView, new String[]{"code", "messages"}, new Object[]{httpStatus.value(), messages});
        } else {
            return getModelAndView(exceptionView, new String[]{"code", "messages"}, new Object[]{httpStatus.value(), messages}, httpStatus);
        }
    }

    /**
     * <pre>
     *     요청 정보 동의 형식 뷰를 가진 모델 앤 뷰를 가져온다.
     * </pre>
     *
     * @param request 요청 정보
     * @param attributeNames 속성 이름들
     * @param attributeValues 속성 값들
     * @return 요청 정보 동의 형식 뷰를 가진 모델 앤 뷰
     */
    public static ModelAndView getModelAndRequestAcceptView(HttpServletRequest request, String[] attributeNames, Object[] attributeValues) {
        return getModelAndRequestAcceptView(request, attributeNames, attributeValues, null);
    }

    /**
     * <pre>
     *     요청 정보 동의 형식 뷰를 가진 모델 앤 뷰를 가져온다.
     * </pre>
     *
     * @param request 요청 정보
     * @param attributeNames 속성 이름들
     * @param attributeValues 속성 값들
     * @param httpStatus http 상태
     * @return 요청 정보 동의 형식 뷰를 가진 모델 앤 뷰
     */
    public static ModelAndView getModelAndRequestAcceptView(HttpServletRequest request, String[] attributeNames, Object[] attributeValues, HttpStatus httpStatus) {
        String acceptView = getRequestAcceptView(request, null);

        if (Objects.isNull(acceptView)) {
            throw new IllegalArgumentException("not supported accept view");
        }

        return getModelAndView(acceptView, attributeNames, attributeValues, httpStatus);
    }

    /**
     * <pre>
     *     요청 정보 동의 형식 뷰를 가져온다.
     *     - 요청 정보 동의 형식 에 맞는 뷰가 없을시 기본뷰를 가져온다.
     * </pre>
     *
     * @param request 요청 정보
     * @param defaultView 기본 뷰
     * @return 요청 정보 동의 형식 뷰
     */
    public static String getRequestAcceptView(HttpServletRequest request, String defaultView) {
        return getAcceptView(request.getHeader("accept"), defaultView);
    }

    /**
     * <pre>
     *     동의 형식 뷰를 가져온다.
     *     - 동의 형식 에 맞는 뷰가 없을시 기본뷰를 가져온다.
     * </pre>
     *
     * @param accept 동의 형식
     * @param defaultView 기본 뷰
     * @return 동의 형식 뷰
     */
    public static String getAcceptView(String accept, String defaultView) {
        String view = defaultView;

        List<MediaType> mediaTypes = MediaType.parseMediaTypes(accept);

        for (MediaType mediaType : mediaTypes) {
            if (ACCEPT_VIEW_MAP.containsKey(mediaType)) {
                view = ACCEPT_VIEW_MAP.get(mediaType);
                break;
            }
        }

        return view;
    }

    /**
     * <pre>
     *     속성 맵을 가져온다.
     * </pre>
     *
     * @param attributeNames 속성 이름들
     * @param attributeValues 속성 값들
     * @return 속성 맵
     */
    public static Map<String, Object> getAttributeMap(String[] attributeNames, Object[] attributeValues) {
        if (!ArrayUtils.isSameLength(attributeNames, attributeValues)) {
            throw new IllegalArgumentException("attributeNames attributeValues not same length");
        }

        Map<String, Object> attributeMap = new HashMap<>();

        if (ArrayUtils.isNotEmpty(attributeNames)) {
            for (int index = 0, length = attributeNames.length; index < length; index++) {
                attributeMap.put(attributeNames[index], attributeValues[index]);
            }
        }

        return attributeMap;
    }

    /**
     * <pre>
     *     모델 앤 뷰를 가져온다.
     * </pre>
     *
     * @param view 뷰
     * @param attributeNames 속성 이름들
     * @param attributeValues 속성 값들
     * @param httpStatus http 상태
     * @return 모델 앤 뷰
     */
    public static ModelAndView getModelAndView(String view, String[] attributeNames, Object[] attributeValues, HttpStatus httpStatus) {
        if (StringUtils.isBlank(view)) {
            throw new IllegalArgumentException("blank view");
        }

        return new ModelAndView(view, getAttributeMap(attributeNames, attributeValues), httpStatus);
    }

    /**
     * <pre>
     *     리다이렉트 설정, 모델 앤 뷰를 가져온다.
     * </pre>
     *
     * @param request 요청 정보
     * @param view 뷰
     * @param attributeNames 속성 이름들
     * @param attributeValues 속성 값들
     * @return 모델 앤 뷰
     */
    public static ModelAndView getModelAndViewWithSettingRedirect(HttpServletRequest request, String view, String[] attributeNames, Object[] attributeValues) {
        FlashMap outputFlashMap = Objects.nonNull(request) ? RequestContextUtils.getOutputFlashMap(request) : null;

        if (Objects.nonNull(outputFlashMap)) {
            outputFlashMap.putAll(getAttributeMap(attributeNames, attributeValues));
        }

        return getModelAndView(view, null, null, HttpStatus.TEMPORARY_REDIRECT);
    }
}
