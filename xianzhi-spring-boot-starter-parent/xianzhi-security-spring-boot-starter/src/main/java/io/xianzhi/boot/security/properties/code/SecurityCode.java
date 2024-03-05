package io.xianzhi.boot.security.properties.code;

import io.xianzhi.common.result.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br>
 *
 * @author Ethan Wang
 * @since 1.1.0
 */
@Getter
@AllArgsConstructor
public enum SecurityCode implements Result {

    /**
     * username or password error
     */
    USER_NAME_OR_PASSWORD_ERROR("2001", false, "username.or.password.error"),


    ;
    /**
     * custom response status code
     */
    private final String code;
    /**
     * whether the operation was successful or not
     */
    private final boolean success;
    /**
     * custom prompt information
     */
    private final String message;


    /**
     * This method returns custom response status codes,
     * non HTTP status codes, or other response status codes
     *
     * @return custom response status code
     */
    @Override
    public String code() {
        return this.code;
    }

    /**
     * This method returns whether the operation was successful or not
     *
     * @return whether the operation was successful or not
     */
    @Override
    public boolean success() {
        return this.success;
    }

    /**
     * This method returns custom prompt information
     *
     * @return custom prompt information
     */
    @Override
    public String message() {
        return this.message;
    }
}
