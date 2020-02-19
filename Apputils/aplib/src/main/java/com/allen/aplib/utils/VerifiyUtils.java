package com.allen.apputils.utils;

import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Allen
 * @date 2019/9/16 15:39
 * @describe
 */
public class VerifiyUtils {
    public static String getLocalLa(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    /**
     * 通过反射获取私有的成员变量
     *
     * @param person
     * @return
     */
    private Object getPrivateValue(Object person, String fieldName) {

        try {
            Field field = person.getClass().getDeclaredField(fieldName);
            // 参数值为true，打开禁用访问控制检查
            //setAccessible(true) 并不是将方法的访问权限改成了public，而是取消java的权限控制检查。
            //所以即使是public方法，其accessible 属相默认也是false
            field.setAccessible(true);
            return field.get(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 验证手机号码是否合法
     * 176, 177, 178;
     * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189;
     * 145, 147;
     * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139;
     * 150, 151, 152, 153, 155, 156, 157, 158, 159;
     * 166
     * "13"代表前两位为数字13,
     * "[0-9]"代表第二位可以为0-9中的一个,
     * "[^4]" 代表除了4
     * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
     */
    public static boolean isMobileNumber(String mobiles) {
//        String telRegex = "^((16[0-9])|(13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        String telRegex = "^1\\d{10}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
