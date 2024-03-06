package io.xianzhi.boot.oauth2.resource.exception;

/**
 * <br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */

import io.xianzhi.boot.security.exception.SecurityException;
import io.xianzhi.common.result.Result;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

/**
 * OAuth2相关异常<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Getter
public class OAuth2Exception extends OAuth2AuthenticationException implements SecurityException {

    /**
     * 响应结果
     */
    private final transient Result result;

    /**
     * 构造方法
     *
     * @param result 响应结果
     */
    public OAuth2Exception(Result result) {
        super(result.code());
        this.result = result;
    }
}
