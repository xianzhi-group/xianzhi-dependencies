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

package io.xianzhi.authorization.properties;

import lombok.Data;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

/**
 * <br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "xianzhi.security.oauth2")
public class OAuth2Properties implements InitializingBean {

    /**
     * 发布机构
     */
    private String issuer = "https://www.xianzhi.io";

    /**
     * rsa文件路径
     */
    private String keyPairPath;

    /**
     * ras密码
     */
    private String keyPairPassword;

    /**
     * ras别名
     */
    private String keyPairAlias;


    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(keyPairPath, "rsa文件路径为空");
        Assert.hasText(keyPairPassword, "ras密码为空");
        Assert.hasText(keyPairAlias, "ras别名为空");
    }

}
