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

package io.xianzhi.common.exception;


import io.xianzhi.common.result.ResponseResult;
import io.xianzhi.common.result.Result;
import lombok.Getter;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Project custom business exception. Usually a foreseeable anomaly.<br>
 *
 * @author Ethan Wang
 * @since 1.0.0 2024-02-29 13:28
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * Corresponding response information can be obtained in case of abnormalities
     */
    private final transient Result result;


    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BizException(Result result) {
        super(result.message());
        this.result = new Result() {
            @Override
            public String code() {
                return result.code();
            }

            @Override
            public boolean success() {
                return false;
            }

            @Override
            public String message() {
                return ResponseResult.messageSource.getMessage(result.message(), null, LocaleContextHolder.getLocale());
            }
        };

    }
}
