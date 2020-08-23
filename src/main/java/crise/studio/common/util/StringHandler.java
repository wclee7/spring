package crise.studio.common.util;

import crise.studio.model.common.Enums;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by JinPark on 2017-01-09.
 */
public class StringHandler {

    private StringHandler() { }

    public static String appendImageServerPath(String storagePath) {
        if (StringUtils.isNotEmpty(storagePath)) {
            boolean hasServerPath = storagePath.contains("http://");

            if ( ! hasServerPath) {
                storagePath = "http://" + Enums.EnviorPath.FILE_SERVER_PATH.name() + storagePath;
            }
        }
        return storagePath;
    }

    /**
     *
     * @param paths
     * @param postfix
     * @param isAppendServerPath
     * @return
     */
    public static List<String> renameImgPathByPlatform(List<String> paths, String postfix, boolean isAppendServerPath) {
        return paths.stream().map(p -> renameImgPathByPlatform(p, postfix, isAppendServerPath)).collect(toList());
    }

    /**
     * 이미지 파일명뒤에 _플랫폼 코드를 붙여준다.
     * ex) abc.jpg -> abc_a.jpg
     * @param path 파일 경로
     * @param postfix _a, _w
     * @return
     */
    public static String renameImgPathByPlatform(String path, String postfix, boolean isAppendServerPath) {
        if (StringUtils.isBlank(path)) { return null; }

        if ( ! StringUtils.contains(path, postfix) ) {
            String renamePath = appendPostfixToFileName(path, postfix);
            return isAppendServerPath ? appendImageServerPath(renamePath) : renamePath;
        }

        String pathWithoutExt = FilenameUtils.removeExtension(path);
        String subStrAfterPostfix = StringUtils.substringAfter(pathWithoutExt, postfix);

        String renamePath = path;
        if (StringUtils.isNotEmpty(subStrAfterPostfix)) {
            renamePath = appendPostfixToFileName(path, postfix);
        }

        return isAppendServerPath ? appendImageServerPath(renamePath) : renamePath;
    }

    /**
     * 파일명 맨뒤에 postfix를 붙여 반환한다.
     * @param path
     * @param postfix
     * @return
     */
    private static String appendPostfixToFileName(String path, String postfix) {
        String ext = FilenameUtils.getExtension(path);
        String pathWithoutExt = FilenameUtils.removeExtension(path);
        return pathWithoutExt.concat(postfix).concat(".").concat(ext);
    }

    public static String newLineDelimeterChange(String platformCode, String text) {
        if (StringUtils.isBlank(text)) { return text; }

        String webDelimeter = "<br/>";

        String acapDelimeter = "|";

        String[] dbDelimeter = {"\r\n", "\n"};

        String replaceDelimeter = "\n";

        switch ( Enums.PlatformCode.valueOf(platformCode) ) {
            case ACAP: replaceDelimeter = acapDelimeter; break;
            case WEB: replaceDelimeter  = webDelimeter; break;
            default :
        }

        return StringUtils.replaceEach(text, dbDelimeter, new String[]{replaceDelimeter, replaceDelimeter});
    }

    public static String getNotEmptyStringFromArr(String defaultStr, String... strs) {

        Optional<String> target = Arrays.asList(strs).stream().filter(StringUtils::isNotBlank).findFirst();

        String notEmptyVal = defaultStr;
        if (target.isPresent()) { notEmptyVal = target.get(); }

        return notEmptyVal;

    }
}
