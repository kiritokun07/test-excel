package com.kirito.excel.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

/**
 * 数据工具类
 *
 * @author kirito
 * @date 2024-01-24 16:57:30
 * @desc ...
 */
public class MyDataUtil {

    private static final DateTimeFormatter PURE_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成20位单号
     *
     * @return String
     */
    public static String generateOrderNo() {
        return LocalDateTime.now().format(PURE_DATETIME_FORMATTER) + getRandomNum(6);
    }

    /**
     * 获取任意长度随机数
     *
     * @param numLength 随机数长度
     * @return 随机数
     */
    public static String getRandomNum(int numLength) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numLength; i++) {
            Random random = new Random();
            String rand = String.valueOf(random.nextInt(10));
            result.append(rand);
        }
        return result.toString();
    }

    public static String getRandomNum2() {
        return String.valueOf((int) (Math.random() * 9 + 1) * Math.pow(10, 5));
    }

    /**
     * 获取限定长度的字符串
     *
     * @param content
     * @param limit
     * @return
     */
    public static String getStrLimit(String content, Integer limit) {
        if (Objects.isNull(content)) {
            return "";
        }
        if (content.length() > limit) {
            return content.trim().substring(0, limit - 1) + ".";
        }
        return content;
    }

}
