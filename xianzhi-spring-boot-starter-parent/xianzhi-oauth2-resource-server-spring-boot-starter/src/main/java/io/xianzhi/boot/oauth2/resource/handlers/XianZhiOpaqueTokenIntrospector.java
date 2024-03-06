package io.xianzhi.boot.oauth2.resource.handlers;

/**
 * <br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */

import io.xianzhi.boot.oauth2.resource.exception.OAuth2Exception;
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
    private RedisTemplate<String,Object>  authorizationInfoRedisTemplate;

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
                authorizationInfoRedisTemplate.opsForValue().get(jwt.getClaimAsString("id"));
                UserBO authorizationInfo = authorizationHandler.getAuthorizationCacheByUserId(jwt.getClaimAsString(SystemConstant.ID))
                        .orElseThrow(() -> new OAuth2Exception(CommonCode.UNAUTHORIZED));
                String id = authorizationInfo.getId();
                WebUtils.getRequest().getParameterNames()
                OAuth2UserDetails userDetails = (OAuth2UserDetails) authorizationInfo;
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