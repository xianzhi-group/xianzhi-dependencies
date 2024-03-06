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

package io.xianzhi.boot.oauth2.resource.handlers;

/**
 * <br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */

import io.xianzhi.boot.oauth2.resource.OAuth2UserDetails;
import io.xianzhi.boot.oauth2.resource.exception.OAuth2Exception;
import io.xianzhi.boot.security.constants.SecurityConstant;
import io.xianzhi.common.code.CommonCode;
import io.xianzhi.common.context.UserBO;
import io.xianzhi.common.context.UserContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * token拦截器<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Slf4j
public class XianZhiOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    /**
     * 资源服务器接口
     */
    @Resource
    private RedisTemplate<String, Object> authorizationInfoRedisTemplate;

    /**
     * jwt解码器
     */
    @Resource
    private JwtDecoder jwtDecoder;


    /**
     * Introspect and verify the given token, returning its attributes.
     * <p>
     * Returning a {@link Map} is indicative that the token is valid.
     *
     * @param token the token to introspect
     * @return the token's attributes
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        if (StringUtils.hasText(token)) {
            try {
                Jwt jwt = jwtDecoder.decode(token);
                String id = jwt.getClaimAsString("id");
                if (StringUtils.hasText(id)) {
                    throw new OAuth2Exception(CommonCode.UNAUTHORIZED);
                }
                // 这里是链接授权服务的redis，获取用户信息
                UserBO userBO = (UserBO) authorizationInfoRedisTemplate.opsForValue().get(String.format(SecurityConstant.AUTHORIZATION_INFO_CACHE_KEY, id));
                if (userBO == null) {
                    throw new OAuth2Exception(CommonCode.UNAUTHORIZED);
                }
                OAuth2UserDetails userDetails = (OAuth2UserDetails) userBO;
                // 设置用户上下文
                UserContext.set(userDetails);
                return userDetails;
            } catch (JwtValidationException exception) {
                log.error("解析Token失败:{}", exception.getMessage(), exception);
                throw new OAuth2Exception(CommonCode.UNAUTHORIZED);
            }
        }
        throw new OAuth2Exception(CommonCode.UNAUTHORIZED);
    }
}