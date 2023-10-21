package com.education.common.utils;

import com.education.common.constants.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author zengjintao
 * @version 1.0
 * @create 2018/8/29 17:22
 **/
public class NumberUtils {

    private static final Logger logger = LoggerFactory.getLogger(NumberUtils.class);

    /**
     * 生成指定位数的随机字符串
     * @param length
     * @return
     */
    public static String getNumberCode(int length) {
        String number = "1234567890";
        return getNumberCode(number, length);
    }

    private static final String[] CHINESE_NUMBER = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] CHINESE_NUMBER_UNITS = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十"};

    /**
     * 将数字转成中文
     * @param number
     * @return
     */
    public static String numberToChinese(int number) {
        String sign = number < 0 ? "负" : "";
        if (number < 0) {
            number = -number;
        }
        StringBuilder result = new StringBuilder(sign);
        String string = String.valueOf(number);
        int n = string.length();
        char[] numberCharArray = string.toCharArray();
        for (int i = 0; i < n; i++) {
            int digNum = n - i; // 位数
            int num = numberCharArray[i] - '0';
            if (num != 0) {
                result.append(CHINESE_NUMBER[num]).append(CHINESE_NUMBER_UNITS[digNum - 1]);
                continue;
            }

            if (result.toString().endsWith(CHINESE_NUMBER[0])) {
                // 如果是单位所在的位数，则去除上一个0，加上单位
                if (digNum % 4 == 1) {
                    result.deleteCharAt(result.length() - 1);
                    result.append(CHINESE_NUMBER_UNITS[digNum - 1]);
                }
            } else {
                if (result.toString().contains("百")) {
                    result.append(CHINESE_NUMBER[0]);
                }
            }
        }
        if (result.toString().startsWith("一十")) { // 避免已1开头的两位数返回类似一十一样的中文
            return result.toString().substring(1, result.toString().length());
        }
        return result.toString();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取16位随机字符串
     * @return String
     */
    public static String getUUID16()
    {
        String uuid = UUID.randomUUID().toString();
        char[] cs = new char[32];
        char c = 0;
        for (int i = uuid.length() / 2,j = 1; i-->0; ) {
            if ((c = uuid.charAt(i))!= '-'){
                cs[j++] = c;
            }
        }
        String uid = String.valueOf(cs);
        return uid;
    }

    private static String getNumberCode(String numberCode, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(numberCode.length());
            sb.append(numberCode.charAt(num));
            numberCode = numberCode.replace((numberCode.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    /**
     * 请指定集合中随机取不同的数组成新的集合
     * @param dataList
     * @param dataSize 新的集合大小
     * @param <T>
     * @return
     */
    public static <T> List<T> getRandomListFromData(List<T> dataList, int dataSize) {
        if (dataList.size() == dataSize || dataList.size() < dataSize) {
            return dataList;
        }
            //throw new RuntimeException("dataSize setting can not be more than dataList");
        List<T> listRandom = new ArrayList<>();
        //随机取出n条不重复的数据
        for (int i = dataSize; i >= 1; i--) {
            Random random = new Random();
            //在数组大小之间产生一个随机数 j
            int j = random.nextInt(dataList.size() - 1);
            //取得list 中下标为j 的数据存储到 listRandom 中
            listRandom.add(dataList.get(j));
            //把已取到的数据移除,避免下次再次取到出现重复
            dataList.remove(j);
        }
        return listRandom;
    }
    /**
     * 生成邀请码
     * @param length
     * @return
     */
    public static String generateInviteCode(int length) {
        String number = "1234567890ABCDEFGHIJKMNXYZW";
        return getNumberCode(number, length);
    }

    /**
     * 随机生成指定length的16进制数
     * @param length
     * @return
     */
    public static String randomHexString(int length) {
        try {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < length; i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            logger.error("生成随机数异常", e);
        }
        return null;
    }

    /**
     * 保留两位小数
     * @param number
     * @return
     */
    public static double doubleToBigDecimal(double number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isInt(String value) {
        return RegexUtils.compile("\\d{1,2}", value);
    }



    public static double division(int divisor, int dividend) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String num = df.format((float)divisor / dividend);//返回的是String类型
        return Double.valueOf(num);
    }

    static List<String> letter = new ArrayList<>();

    /**
     * 根据 number 获取英文字母
     * @param number
     * @return
     */
    public static String generateLetter(int number) {
        if (letter.size() == 0) {
            for (int i = 1; i <= 26; i++) {
                letter.add(ObjectUtils.totoUpperCaseFirst(String.valueOf((char)(96 + i))));
            }
        }
        return letter.get(number);
    }

    /**
     * 生成指定个数的随机整数的和等于totalNumber
     * @param totalNumber
     * @param number
     * @return
     */
    public static int[] generateRandomNumberForTotal(int totalNumber, int number) {
        int[] result = new int[number];
        int min = totalNumber / number - 1;
        return generateRandomNumberForTotal(number, totalNumber, min, result);
    }

    public static int[] generateRandomNumberForTotal(int count, int total, int min, int[] result){
        int random = 0 ;
        if (count > 1) {
            int useTotal = total - (count - 1) * min;
            random = (int) (Math.random() * (useTotal - 1) + 1);
        } else {
            random = total;
        }
        result[count - 1] = random;
        int surplusTotal = total- random;
        count--;
        if (count > 0) {
            generateRandomNumberForTotal(count, surplusTotal, min, result);
        }
        return result;
    }

    /**
     * 获取百分号比例
     * @param param1
     * @param param2
     * @return
     */
    public static String getPercentRate(Integer param1, Integer param2) {
        BigDecimal rate = new BigDecimal(param1)
                .divide(new BigDecimal(param2), 4, RoundingMode.HALF_DOWN)
                .setScale( 4, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100))
                .setScale(2);
        return rate + SystemConstants.PERCENT;
    }
}
