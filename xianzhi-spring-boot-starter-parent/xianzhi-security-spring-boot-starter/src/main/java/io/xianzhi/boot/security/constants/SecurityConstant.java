package io.xianzhi.boot.security.constants;

/**
 * security constant<br>
 *
 * @author Ethan Wang
 * @since 1.1.0
 */
public interface SecurityConstant {

    /**
     * {bcrypt} encode prefix
     */
    String BCRYPT = "{bcrypt}";

    /**
     * {noop}  encode prefix
     */
    String NOOP = "{noop}";


    /**
     * 授权信息缓存key
     */
    String AUTHORIZATION_INFO_CACHE_KEY = "authorization:info:id:%s";
}
