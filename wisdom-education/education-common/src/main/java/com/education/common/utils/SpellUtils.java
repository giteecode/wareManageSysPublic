package com.education.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 汉字拼音工具类
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/19 21:10
 */
public class SpellUtils {

    /**
     * 获取汉字全拼
     * @param characters
     * @return
     */
    public static String getSpell(String characters) {
        char[] keyWordChar = characters.toCharArray();
        String[] keyWordArray = new String[keyWordChar.length];
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String result = "";
        try {
            for (int i = 0; i < keyWordChar.length; i++) {
                // 判断是否为汉字字符
                if (Character.toString(keyWordChar[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    keyWordArray = PinyinHelper.toHanyuPinyinStringArray(keyWordChar[i], hanyuPinyinOutputFormat);
                    result += keyWordArray[0];
                } else {
                    result += Character.toString(keyWordChar[i]);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到汉字首字母
     * @param characters
     * @return
     */
    public static String getSpellHeadChar(String characters) {
        String convert = "";
        for (int j = 0; j < characters.length(); j++) {
            char word = characters.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     * @param characters
     * @return
     */
    public static String getCharactersASCII(String characters) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bytes = characters.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            strBuf.append(Integer.toHexString(bytes[i] & 0xff));
        }
        return strBuf.toString();
    }
}
