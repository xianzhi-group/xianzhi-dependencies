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

package io.xianzhi.boot.security.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * security properties<br>
 *
 * @author Ethan Wang
 * @since 1.1.0
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "xianzhi.security")
public class SecurityProperties {

    /**
     * token过期时间，单位是小时
     */
    private Integer tokenExpire = 2;

    /**
     * 刷新token过期时间，单位是小时
     */
    private Integer refreshTokenExpire = tokenExpire * 2;

    /**
     * 放行地址，白名单 即不用登录即可访问的资源
     */
    private List<String> permitAllList = new ArrayList<>();



}