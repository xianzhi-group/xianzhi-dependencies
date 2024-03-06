/*
 * Copyright (c) 2023-2024  XianZhi Group All Rights
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xianzhi.common.result;

import cn.hutool.extra.spring.SpringUtil;
import io.xianzhi.common.code.CommonCode;
import io.xianzhi.common.utils.TraceIdUtil;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * <br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Getter
public class ResponseResult<E> implements Result {

    /**
     * i18n message source
     */
    public static MessageSource messageSource = SpringUtil.getBean(MessageSource.class);

    /**
     * The data that needs to be returned to the caller
     */
    private final E data;

    /**
     * Global TraceID
     */
    private final String traceId;

    /**
     * Returns a response message indicating successful operation without any data
     *
     * @param <R> Return the generic type of data
     * @return Response information for successful operation
     */
    public static <R> ResponseResult<R> ok() {
        return new ResponseResult<>(CommonCode.SUCCESS, null);
    }

    /**
     * Returns a response message indicating the success of an operation, along with the returned data
     *
     * @param data The data that needs to be returned for this operation
     * @param <R>  Return the generic type of data
     * @return Response information for successful operation
     */
    public static <R> ResponseResult<R> ok(R data) {
        return new ResponseResult<>(CommonCode.SUCCESS, data);
    }

    /**
     * Return custom error response information
     *
     * @param result Custom error response information
     * @param <R>    Return the generic type of data
     * @return Response operation failure response information
     */
    public static <R> ResponseResult<R> fail(Result result) {
        return new ResponseResult<>(result.success() ? CommonCode.FAIL : result, null);
    }

    /**
     * structure
     *
     * @param result response result
     * @param data   response data
     */
    public ResponseResult(Result result, E data) {
        this.code = result.code();
        this.success = result.success();
        if (null != messageSource) {
            this.message = messageSource.getMessage(result.message(), null, LocaleContextHolder.getLocale());
        } else {
            this.message = result.message();
        }
        this.data = data;
        this.traceId = TraceIdUtil.getTraceId();
    }


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