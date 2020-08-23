package crise.studio.common.cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SeedUtil {
    /*
     * AES의 경우 128, 192, 256bit의 키 길이를 지원합니다 key 에 해당하는 문자열을 16byte(128) 또는 24byte(192) 또는 32byte(256) 생성
     */

    private static final String CHARACTER_SET = "UTF-8";
    //private static final String key = "1234567890123456";

    /**
     * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
     *
     * @param hex hex string
     * @return
     */
    private static Logger logger = LoggerFactory.getLogger(SeedUtil.class);

    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer
                    .parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    /**
     * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
     *
     * @param ba byte[]
     * @return
     */
    public static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer(ba.length * 2);
        String hexNumber;
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

    private static byte[] StringToByte(final String nomal) {
        try {

            byte[] tempByte = nomal.getBytes(CHARACTER_SET);

            // seed 암호화를 위해선 byte가 무조건 16byte씩 배열이 생성되어야 함으로 빈배열의 공간수를 계산
            int needBlankLength = 0;
            if (tempByte.length % 16 != 0) {
                needBlankLength = 16 - (tempByte.length % 16);
            }

            // 위에서 구한 필요한 빈공간의 수를 더하여 다시 배열 생성
            byte[] newTempByte = new byte[tempByte.length + needBlankLength];
            System.arraycopy(tempByte, 0, newTempByte, 0, newTempByte.length);
            /*
            for (int i = 0; i < newTempByte.length; i++) {
                newTempByte[i] = tempByte[i];
            }*/

            return newTempByte;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * seed로 암호화된 문자열을 복호화
     *
     * @param encVal - 암호화 대상 string
     * @param key - encrypt할때 사용한 key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getSeedDecrypt(String encVal,String key) throws UnsupportedEncodingException{

        final byte[] pbszUserKey = StringToByte(key);

        // 암호화문 byte 배열 리스트로 변환
        List encByteList = getByteList(encVal, false);
        logger.debug("encByteList.size : " + encByteList.size());
        // 복호화된 byte 배열 저장할 리스트 선언
        List decByteList = new ArrayList();

        for (int i = 0; i < encByteList.size(); i++) {
            byte[] encByte = (byte[]) encByteList.get(i);

            logger.debug("encByte.length : " + encByte.length);
            byte[] tempDecByte = new byte[16];

            tempDecByte = KISA_SEED_ECB.SEED_ECB_Decrypt(pbszUserKey, encByte,
                    0, 32);

            for (int j = 0; j < tempDecByte.length; j++) {
                logger.debug("tempDecByte[" + j + "] : " + tempDecByte[j]);

            }

            decByteList.add(tempDecByte);
        }

        return getByteListStr(decByteList, false);
    }

    /**
     * seed로 문자열을 암호화
     *
     * @param strVal - 암호화 할 string
     * @param key - encrypt에 사용할 key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getSeedEncrypt(String strVal,String key) throws UnsupportedEncodingException {
        logger.debug("key byte length : " + key.getBytes().length);

        final byte[] pbszUserKey = StringToByte(key);

        // 원문을 byte[] list로 변환
        List byteList = getByteList(strVal, true);

        // 암호화된 byte[]를 저장할 list
        List encByteList = new ArrayList();

        for (int i = 0; i < byteList.size(); i++) {

            byte[] byteVal = (byte[]) byteList.get(i);
            logger.debug("byteVal length : " + byteVal.length);
            byte[] tempEncVal = new byte[16];

            tempEncVal = KISA_SEED_ECB.SEED_ECB_Encrypt(pbszUserKey, byteVal,
                    0, 16);

            logger.debug("tempEncVal length : " + tempEncVal.length);

            for (int j = 0; j < tempEncVal.length; j++) {
                logger.debug("tempDecByte[" + j + "] : " + tempEncVal[j]);

            }

            encByteList.add(tempEncVal);
        }

        // list에 담긴 enc문을 str형태로 변환하여 반환
        return getByteListStr(encByteList, true);
    }

    /**
     * seed는 128bit 블록 단위로 암호화 처리한다. 문자열을 128bit단위로 byte배열을 생성하고, 그 배열을 list형태로
     * 반환한다.
     *
     * @param nomal
     * @param isEncrypt
     * @return
     * @throws Exception
     */
    private static List getByteList(String nomal, final boolean isEncrypt) throws UnsupportedEncodingException{

        int array;
        List byteList = new ArrayList();

        byte[] tempByte = null;

        if (isEncrypt) {
            tempByte = nomal.getBytes(CHARACTER_SET);
            array = 16;
        } else {
            tempByte = hexToByteArray(nomal);
            array = 32;
        }

        for (int i = 0; i < tempByte.length; i++) {
            logger.debug("tempByte[" + i + "] : " + tempByte[i]);
        }

        // seed 암호화를 위해선 byte가 무조건 16byte씩 배열이 생성되어야 함으로 빈배열의 공간수를 계산
        int needBlankLength = 0;
        if (tempByte.length % array != 0) {
            needBlankLength = array - (tempByte.length % array);
        }

        // 위에서 구한 필요한 빈공간의 수를 더하여 다시 배열 생성
        byte[] newTempByte = new byte[tempByte.length + needBlankLength];
        System.arraycopy(tempByte, 0, newTempByte, 0, tempByte.length);
        /*
        for (int i = 0; i < tempByte.length; i++) {
            newTempByte[i] = tempByte[i];
        }*/

		// 이제 newTempByte를 16의 배수의 사이즈를 갖는 배열이 되었다.
        // 16개씩 짤라서 List에 add 한다.
        int inListByteIdx = 0;
        byte[] inListByte = new byte[array];
        for (int i = 0; i < newTempByte.length; i++) {

            inListByte[inListByteIdx] = newTempByte[i];
            inListByteIdx++;

            if ((i + 1) % array == 0 && i != 0) {
                byteList.add(inListByte);
                inListByte = new byte[array];
                inListByteIdx = 0;
            }
        }

        return byteList;
    }

    /**
     * 128bit단위로 쪼개진 byte배열이 담긴 list를 문자열로 변환해준다
     *
     * @param byteList
     * @param isEncrypt
     * @return
     * @throws Exception
     */
    private static String getByteListStr(List byteList, final boolean isEncrypt) throws UnsupportedEncodingException{

        logger.debug("byteList : " + byteList.size());
        // List에 담긴 byte배열이 16으로 고정임으로 * 16
        int array;
        if (isEncrypt) {
            array = 32;
        } else {
            array = 16;
        }
        byte[] listByte = new byte[byteList.size() * array];
        logger.debug("listByte : " + listByte.length);
        // List에 담긴 byte배열을 하나의 배열(listByte)에 merge
        for (int i = 0; i < byteList.size(); i++) {
            byte[] temp = (byte[]) byteList.get(i);

            logger.debug("temp : " + temp.length);

            for (int j = 0; j < temp.length; j++) {
                logger.debug(" i : " + i + " | j : " + j);
                listByte[j + (array * i)] = temp[j];
                logger.debug("temp : " + temp[j]);
            }
        }

        // blank byte 카운트 세기
        int blankCnt = 0;
        for (int i = listByte.length; i > 0; i--) {
            if (listByte[i - 1] == 0) {
                blankCnt++;
            } else {
                break;
            }
        }

        // blank를 제외한 만큼의 데이터만 추출
        byte[] resultByte = new byte[listByte.length - blankCnt];
        System.arraycopy(listByte, 0, resultByte, 0, resultByte.length);
        /*
        for (int i = 0; i < resultByte.length; i++) {
            resultByte[i] = listByte[i];
        }*/

        String retStr = null;

        if (isEncrypt) {
            retStr = byteArrayToHex(resultByte);
        } else {
            retStr = new String(resultByte, CHARACTER_SET);
        }
        return retStr;
    }
}
