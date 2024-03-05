/*
 * Copyright (c) 2023-2024  XianZhi Group  All Rights
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

package io.xianzhi.boot.web.handlers;

import io.xianzhi.common.code.CommonCode;
import io.xianzhi.common.result.ResponseResult;
import io.xianzhi.common.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * global exception handler
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * i18n
     */
    private final MessageSource messageSource;

    /**
     * Capture unexpected exception information and uniformly return response results
     *
     * @param exception Unexpected exception information
     * @return Unified response results
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<Object> exception(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseResult.fail(CommonCode.ERROR);
    }


    /**
     * Capture HttpRequestMethodNotSupportedException and process the return of unified
     * response information
     *
     * @param exception HttpRequestMethodNotSupportedException
     * @return Unified response results
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseResult.fail(CommonCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION);
    }

    /**
     * Capture HttpMessageNotReadableException and process the return of unified
     * response information
     *
     * @param exception HttpMessageNotReadableException
     * @return Unified response results
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseResult<Object> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseResult.fail(CommonCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
    }

    /**
     * Capture MethodArgumentNotValidException and process the return of unified
     * response information
     *
     * @param exception MethodArgumentNotValidException
     * @return Unified response results
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        String defaultMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseResult.fail(new Result() {
            @Override
            public String code() {
                return "000";
            }

            @Override
            public boolean success() {
                return false;
            }

            @Override
            public String message() {
                if (defaultMessage != null) {
                    return messageSource.getMessage(defaultMessage, null, LocaleContextHolder.getLocale());
                } else {
                    return messageSource.getMessage(CommonCode.PARAMETER_CHECK_FAILED.message(), null, LocaleContextHolder.getLocale());
                }
            }
        });
    }
}
