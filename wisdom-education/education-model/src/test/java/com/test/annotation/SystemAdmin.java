package com.test.annotation;

import com.education.common.utils.ObjectUtils;
import com.jfinal.json.Jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员实体类
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/9 10:29
 * @since 1.0.0
 */
public class SystemAdmin extends ModelBean<SystemAdmin> {

    public static void main(String[] args) {

        String answer = "{{sdsd}sdjhsdjhsdjj(dsd)},{ddddddddd}";
        System.out.println(parse1("#{{sdsd}sdjhsdjhsdjj(dsd)}"));
    }

    public static String parse1(String text) {
        String open = "#{";
        String close = "}";

        int length = text.length();

        String str = text.substring(open.length(), length - close.length());
        String[] s = ObjectUtils.spilt(str);

        System.out.println(new Jackson().toJson(s));
        Jackson.getJson().parse(new Jackson().toJson(s), List.class);
        return null;
    }

    public static String parse(String text) {
        String open = "#{";
        String close = "}";
      //  Map params = new HashMap<>();
        List<String> param = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return "";
        }
        // search open token
        int start = text.indexOf("#{");
        if (start == -1) {
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append("#{");
                offset = start + "#{".length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + "#{".length();
                int end = text.lastIndexOf(close, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append("}");
                        offset = end + close.length();
                        end = text.indexOf(close, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                   // builder.append(handler.handleToken(expression.toString()));
                    System.out.println(expression.toString());
                    param.add(expression.toString());
                    offset = end + close.length();
                }
            }
            start = text.indexOf("#{", offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        System.out.println(param);
        return builder.toString();
    }
}
