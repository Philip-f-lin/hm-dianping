package com.hmdp.utils;

public abstract class RegexPatterns {
    /**
     * 手機號碼正則
     */
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
    /**
     * email正則
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**
     * 密碼正則。 4~32位的字母、數字、底線
     */
    public static final String PASSWORD_REGEX = "^\\w{4,32}$";
    /**
     * 驗證碼正則, 6位數字或字母
     */
    public static final String VERIFY_CODE_REGEX = "^[a-zA-Z\\d]{6}$";

}
