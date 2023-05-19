package com.felipe_abreu.login.config;

import lombok.experimental.UtilityClass;

/**
 * Application constants.
 */
@UtilityClass
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String SINGLE_WORD_ALPHANUMERIC = "^[a-zA-Z0-9_çáÁàÀãÃâÂéÉíÍõÕôÔóÓúÚüÜºª]+$";
    public static final String MULTIPLE_WORDS_ALPHANUMERIC = "^[a-zA-Z0-9_çáÁàÀãÃâÂéÉíÍõÕôÔóÓúÚüÜºª?!\\-\"\\s]+$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final int TOKEN_EXPIRATION = 60_000;

    public static final String TOKEN_PASSWORD = "b62e569f-3bfe-44f0-9de5-c5eb3a20298b";

    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String PREFIX_ATTRIBUTE = "Bearer ";

}
