package crise.studio.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crise.studio.common.cipher.AES256;
import crise.studio.common.cipher.SeedUtil;

public class DecryptNumberUtil {
	
	private DecryptNumberUtil(){}
	
	private static final Logger logger = LoggerFactory.getLogger(DecryptNumberUtil.class);
	
	/**
     *  전화번호 복호화
     * @param encryptedStr
     * @param key
     */
    public static String decryptPhoneNumber(String encryptedStr, String key) {
        logger.debug("ENTRY decryptPhoneNumber encryptedStr :: {}, key :: {}", encryptedStr, key);

        String decodePhone = "";

        if (StringUtils.isEmpty(encryptedStr)) {
            return decodePhone;
        }
        
        if (StringUtils.isEmpty(key)) {
            return decodePhone;
        }
        
        try {
            decodePhone = SeedUtil.getSeedDecrypt(encryptedStr, key);
            logger.debug("MHP decodePhone :: {}", decodePhone);
        } catch (Exception ex) {
            //getSeedDecrypt 복호화 에러시 WEB 복호화 진행
            try {
                decodePhone = AES256.AES_Decode(encryptedStr, key);
                logger.debug("WEB decodePhone :: {}", decodePhone);
            } catch (Exception e1) {
                logger.error("PHONE DECRYPT ERROR :: {}", e1.getMessage());
                decodePhone = "";
            }
        }

        return decodePhone;
    }
	
}
