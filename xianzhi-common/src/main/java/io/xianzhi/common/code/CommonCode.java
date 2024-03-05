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

package io.xianzhi.common.code;

import io.xianzhi.common.result.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Define some public custom response information<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum CommonCode implements Result {
    /**
     * Operation successful
     */
    SUCCESS("200", true, "success"),
    /**
     * Operation failed, and the reason for the failure is foreseeable
     */
    FAIL("500", false, "fail"),
    /**
     * Operation failed, unforeseeable error
     */
    ERROR("-1", false, "error"),
    /**
     * unauthorized
     */
    UNAUTHORIZED("401", false, "unauthorized"),
    /**
     * forbidden
     */
    FORBIDDEN("403", false, "forbidden"),
    /**
     * parameter check fail
     */
    PARAMETER_CHECK_FAILED("001", false, "parameter.check.failed"),
    /**
     * http media type not supported exception
     */
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION("002", false, "http.media.type.not.supported"),
    /**
     * missing servlet request parameter exception
     */
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION("003", false, "missing.servlet.request.parameter"),
    /**
     * http request method not supported exception
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION("004", false, "http.request.method.not.supported"),
    /**
     * http message not readable exception
     */
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION("005", false, "http.message.not.readable"),

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
