package com.simple.common.utils;

import org.apache.commons.lang.StringUtils;

public class EmojiFilterUtil {
    /** 
     * 检测是否有emoji字符 
     * @param source 
     * @return 一旦含有就抛出 
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isNotEmojiCharacter(codePoint)) {
                //判断到了这里表明，确认有表情字符  
                return true;
            }
        }
        return false;
    }

    /** 
     * 判断是否为非Emoji字符 
     * 
     * @param codePoint 比较的单个字符 
     * @return 
     */
    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
               || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
               || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
               || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 特殊表情符号过滤
     * @param in
     * @return
     */
    public static String decode(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.
        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        for (int i = 0, n = in.length(); i < n; i++) {
            current = in.charAt(i);
            String str = String.valueOf(current);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                || ((current > 0x20) && (current <= 0xD7FF))
                || ((current >= 0xE000) && (current <= 0xFFFD))
                || ((current >= 0x10000) && (current <= 0x10FFFF)) || (current < 0x0)) {
                out.append(current);
            } else if (" ".equals(str)) {
                out.append(" ");
            }
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(containsEmoji("张三"));
    }

}
