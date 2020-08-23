package crise.studio.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ObjectUtil {

    private ObjectUtil() { }

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    public static boolean checkAllFieldsEmptyByEntityObjectExceptIdFiled(Object obj, String[] skipNames) {
        List<String> skipNameList = skipNames != null ? Arrays.asList(skipNames) : null;

        Boolean isEmpty = true;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            logger.info("=============================================");
            logger.info("Field Name : {}", field.getName());

            if (field.getName().equals("id")) {

            } else {
                logger.info("FIELD CLASS TYPE SIMPLE NAME : {}", field.getType().getSimpleName());

                if (CollectionUtils.isNotEmpty(skipNameList) && skipNameList.contains(field.getName())) { continue; }

                switch (field.getType().getSimpleName()) {

                    case "String":
                        String str = "";
                        try {
                            str = (String) field.get(obj);
                        } catch (Exception e) {
                            logger.error("Exception, Name : {}, Message : {}", e.getClass().getName(), e.getMessage());
                        } finally {

                        }
                        logger.info("fieldObj :: {}", str);
                        isEmpty = StringUtils.isEmpty(str);
                        break;

                    default:
                        Object fieldObj = null;
                        try {
                            fieldObj = field.get(obj);
                        } catch (Exception e) {
                            logger.error("Exception, Name : {}, Message : {}", e.getClass().getName(), e.getMessage());
                        } finally {

                        }
                        logger.info("fieldObj :: {}", fieldObj);
                        isEmpty = fieldObj == null ? true : false;
                        break;
                }
                logger.info("isEmpty :: {}", isEmpty);

                if (isEmpty) {
                    continue;
                } else {
                    logger.info("NONE EMPTY NAME : {}", field.getType().getSimpleName());
                    break;
                }
            }
            logger.info("=============================================");

        }
        return isEmpty;
    }

}
