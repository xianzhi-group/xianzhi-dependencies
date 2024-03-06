package io.xianzhi.boot.oauth2.resource.config;

import io.xianzhi.boot.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


/**
 * 资源服务器配置类<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class OAuth2ResourceServerConfig {

    /**
     * 安全配置
     */
    private final SecurityProperties securityProperties;
    /**
     * token拦截器
     */
    private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;
    /**
     * 认证端点
     */
    private final AuthenticationEntryPoint authenticationEntryPoint;
    /**
     * 权限不足处理
     */
    private final AccessDeniedHandler accessDeniedHandler;

    /**
     * 资源服务器配置
     *
     * @param http http
     * @return SecurityFilterChain
     * @throws Exception ex
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AntPathRequestMatcher[] requestMatchers = securityProperties.getPermitAllList()
                .stream()
                .map(AntPathRequestMatcher::new)
                .toList()
                .toArray(new AntPathRequestMatcher[]{});
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(requestMatchers)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.opaqueToken(
                                        token -> token.introspector(customOpaqueTokenIntrospector))
                                .accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .bearerTokenResolver(new DefaultBearerTokenResolver()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
