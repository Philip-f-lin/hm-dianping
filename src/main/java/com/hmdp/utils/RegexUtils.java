package com.hmdp.utils;

import cn.hutool.core.util.StrUtil;

public class RegexUtils {
    /**
     * 是否為無效手機格式
     * @param phone 要校驗的手機號碼
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneInvalid(String phone){
        return mismatch(phone, RegexPatterns.PHONE_REGEX);
    }
    /**
     * 是否為無效信箱格式
     * @param email 要校驗的信箱
     * @return true:符合，false：不符合
     */
    public static boolean isEmailInvalid(String email){
        return mismatch(email, RegexPatterns.EMAIL_REGEX);
    }

    /**
     * 是否為無效驗證碼格式
     * @param code 要校驗的驗證碼
     * @return true:符合，false：不符合
     */
    public static boolean isCodeInvalid(String code){
        return mismatch(code, RegexPatterns.VERIFY_CODE_REGEX);
    }

    // 校驗是否不符合正則格式
    private static boolean mismatch(String str, String regex){
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }
}
