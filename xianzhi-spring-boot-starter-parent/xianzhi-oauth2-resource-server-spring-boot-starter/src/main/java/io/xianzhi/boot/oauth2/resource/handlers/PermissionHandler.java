package io.xianzhi.boot.oauth2.resource.handlers;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

/**
 * 权限校验处理<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Component("xz")
public class PermissionHandler {
    /**
     * 判断当前用户访问的接口是否具有权限
     *
     * @param permissions 访问接口需要的权限
     * @return 是否可以访问
     */
    public boolean hasPermission(String... permissions) {
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> {
                    for (String permission : permissions) {
                        if (PatternMatchUtils.simpleMatch(x, permission)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

}
