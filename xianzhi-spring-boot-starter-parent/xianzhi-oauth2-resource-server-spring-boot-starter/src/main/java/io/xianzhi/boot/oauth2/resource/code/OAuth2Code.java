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

package io.xianzhi.boot.oauth2.resource.code;

import io.xianzhi.common.result.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2Code<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum OAuth2Code implements Result {


    /**
     * scope 为空，或者scope传递了多个
     */
    SCOPE_ERROR("OAUTH2-00-10000", false, "scope.error"),

    /**
     * 客户认证异常
     */
    CLIENT_ERROR("OAUTH2-01-10000", false, "client.error"),
    /**
     * 授权类型不支持
     */
    GRANT_TYPE_NOT_SUPPORT("OAUTH2-01-10002", false, "grant.type.not.support"),
    /**
     * 客户端无效
     */
    INVALID_SCOPE("OAUTH2-01-10003", false, "scope.invalid"),

    /**
     * 生成 accessToken错误
     */
    GENERATOR_ACCESS_TOKEN_ERROR("OAUTH2-01-10004", false, "generator.access.token.error"),
    /**
     * 生成 refreshToken错误
     */
    GENERATOR_REFRESH_TOKEN_ERROR("OAUTH2-01-10005", false, "generator.refresh.token.error");


    /**
     * 自定义状态码
     */
    private final String code;
    /**
     * 是否成功
     */
    private final boolean success;
    /**
     * 提示信息
     */
    private final String message;


    /**
     * 自定义状态码
     *
     * @return 自定义状态码
     */
    @Override
    public String code() {
        return this.code;
    }

    /**
     * 是否操作成功
     *
     * @return 是否操作成功
     */
    @Override
    public boolean success() {
        return this.success;
    }

    /**
     * 自定义提示信息
     *
     * @return 自定义提示信息
     */
    @Override
    public String message() {
        return this.message;
    }
}
