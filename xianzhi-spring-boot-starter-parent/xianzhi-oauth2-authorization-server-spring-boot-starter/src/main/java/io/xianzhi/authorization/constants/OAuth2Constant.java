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

package io.xianzhi.authorization.constants;

/**
 * OAuth2相关常量<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public interface OAuth2Constant {

    /**
     * 客户端主键ID缓存key
     */
    String CACHE_OAUTH_CLIENT_ID = "oauth:client:id:%s";

    /**
     * 客户端客户端ID缓存key
     */
    String CACHE_OAUTH_CLIENT_CLIENT_ID = "oauth:client:client_id:%s";
}
