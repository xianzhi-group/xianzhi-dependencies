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

package io.xianzhi.authorization.converters;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.xianzhi.authorization.enums.GrantTypeEnum;
import io.xianzhi.authorization.token.AbstractBaseAuthenticationToken;
import io.xianzhi.authorization.token.PasswordAuthenticationToken;
import io.xianzhi.authorization.utils.OAuth2Assert;
import io.xianzhi.authorization.utils.OAuth2EndpointUtils;
import io.xianzhi.boot.oauth2.resource.exception.OAuth2Exception;
import io.xianzhi.common.code.CommonCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 密码模式转换<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public class PasswordAuthenticationConverter extends AbstractAuthenticationConverter {


    /**
     * 是否支持此convert
     *
     * @param grantType 授权类型
     * @return 是否支持
     */
    @Override
    public boolean support(String grantType) {
        return StringUtils.hasText(grantType) && GrantTypeEnum.PASSWORD.getCode().equals(grantType);
    }

    /**
     * 检查认证参数是否合法
     *
     * @param request              request
     * @param additionalParameters 扩展参数
     */
    @Override
    public void checkedParams(HttpServletRequest request, Map<String, Object> additionalParameters) {
        OAuth2Assert.isTrue(request.getMethod().equals(HttpMethod.POST.name()), CommonCode.ERROR);
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            try {
                JSONObject userDetails = JSON.parseObject(request.getInputStream());
                if (null == userDetails) {
                    throw new OAuth2Exception(CommonCode.ERROR);
                }
                if (StringUtils.hasText(userDetails.getString(OAuth2ParameterNames.USERNAME))) {
                    additionalParameters.put(OAuth2ParameterNames.USERNAME, userDetails.get("username"));
                } else {
                    throw new OAuth2Exception(CommonCode.ERROR);
                }
                if (StringUtils.hasText(userDetails.getString(OAuth2ParameterNames.PASSWORD))) {
                    additionalParameters.put(OAuth2ParameterNames.PASSWORD, userDetails.get("password"));
                } else {
                    throw new OAuth2Exception(CommonCode.ERROR);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
            String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
            if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
                OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME,
                        OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
            additionalParameters.put(OAuth2ParameterNames.USERNAME, username);

            // password (REQUIRED)
            String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
            if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
                OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD,
                        OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
            additionalParameters.put(OAuth2ParameterNames.PASSWORD, password);
        }
    }


    /**
     * 构建token
     *
     * @param clientPrincipal      客户端认证信息
     * @param requestedScopes      scope
     * @param additionalParameters 扩展信息
     * @return 认证token
     */
    @Override
    public AbstractBaseAuthenticationToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new PasswordAuthenticationToken(new AuthorizationGrantType(GrantTypeEnum.PASSWORD.getCode()), clientPrincipal,
                requestedScopes, additionalParameters);
    }
}
