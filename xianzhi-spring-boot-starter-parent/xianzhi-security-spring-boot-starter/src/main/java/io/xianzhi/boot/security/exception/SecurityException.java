package io.xianzhi.boot.security.exception;

import io.xianzhi.common.result.Result;

/**
 * 安全异常接口，主要是为了兼容<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public interface SecurityException {

    /**
     * 返回结果
     *
     * @return 响应信息
     */
    Result getResult();
}
