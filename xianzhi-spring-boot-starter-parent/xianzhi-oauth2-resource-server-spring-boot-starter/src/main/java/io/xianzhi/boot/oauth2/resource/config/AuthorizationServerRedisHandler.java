package io.xianzhi.boot.oauth2.resource.config;

import cn.hutool.json.ObjectMapper;
import com.alibaba.fastjson2.support.spring6.data.redis.FastJsonRedisSerializer;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * 授权服务器的redis配置<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Getter
@Setter
public class AuthorizationServerRedisHandler implements InitializingBean {
    /**
     * redis 主机地址
     */

    private String host;
    /**
     * redis 密码
     */
    private String password;

    /**
     * redis 端口
     */
    private Integer port;
    /**
     * 默认数据库
     */
    private Integer database;
    /**
     * 最大连接数
     */
    private Integer maxTotal;
    /**
     * 最大空闲连接数
     */
    private Integer maxIdle;
    /**
     * 最小空闲连接数
     */
    private Integer minIdle;
    /**
     * 最大等待时间（秒）
     */
    private Integer maxWait;
    /**
     * 命令超时时间 （秒）
     */
    private Integer commandTimeOut;


    public LettuceConnectionFactory authorizationSDKRedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setDatabase(database);
        if (StringUtils.hasText(password)) {
            redisConfig.setPassword(password);
        }

        // 设置连接池配置
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        // 最大连接数
        poolConfig.setMaxTotal(maxTotal);
        // 最大空闲连接数
        poolConfig.setMaxIdle(maxIdle);
        // 最小空闲连接数
        poolConfig.setMinIdle(minIdle);
        // 最大等待时间（秒）
        poolConfig.setMaxWait(Duration.ofSeconds(1));

        // 创建 LettucePoolingClientConfiguration
        LettucePoolingClientConfiguration lettuceClientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                // 命令超时时间
                .commandTimeout(Duration.ofSeconds(commandTimeOut))
                .build();
        // 创建并配置 LettuceConnectionFactory
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfig, lettuceClientConfig);
        lettuceConnectionFactory.start();
        return lettuceConnectionFactory;
    }


    @Bean(name = "authorizationInfoRedisTemplate")
    public RedisTemplate<String, Object> authorizationInfoRedisTemplate() {
        // 1.创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 2.加载Redis配置
        redisTemplate.setConnectionFactory(authorizationSDKRedisConnectionFactory());
        // 使用 Fastjson 序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 3.配置key序列化
        RedisSerializer<?> stringRedisSerializer = new StringRedisSerializer();
        // 设置 Key 的序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 设置  Value 的序列化器
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);

        // 5.初始化RedisTemplate
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

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
        File configFile = ResourceUtils.getFile("classpath:authorization-sdk.properties");
        if (!configFile.exists()) {
            throw new IllegalArgumentException("classpath:authorization-sdk.properties file not exists");
        }
        Properties properties = new Properties();
        properties.load(new FileInputStream(configFile));
        host = properties.getProperty("host");
        Assert.hasText(host, "sdk.host not blank");
        // 密码
        String passwordProperty = properties.getProperty("password");
        if (StringUtils.hasText(passwordProperty)) {
            password = passwordProperty;
        }
        String portProperty = properties.getProperty("port");
        Assert.hasText(portProperty, "sdk.port not blank");
        port = Integer.parseInt(portProperty);

        String databaseProperty = properties.getProperty("database");
        Assert.hasText(databaseProperty, "sdk.database not blank");
        database = Integer.parseInt(databaseProperty);

        String maxTotalProperty = properties.getProperty("maxTotal");
        Assert.hasText(maxTotalProperty, "sdk.maxTotal not blank");
        maxTotal = Integer.parseInt(maxTotalProperty);

        String maxIdleProperty = properties.getProperty("maxIdle");
        Assert.hasText(maxIdleProperty, "sdk.maxIdle not blank");
        maxIdle = Integer.parseInt(maxIdleProperty);

        String minIdleProperty = properties.getProperty("minIdle");
        Assert.hasText(minIdleProperty, "sdk.minIdle not blank");
        minIdle = Integer.parseInt(minIdleProperty);

        String commandTimeOutProperty = properties.getProperty("commandTimeOut");
        Assert.hasText(commandTimeOutProperty, "sdk.commandTimeOut not blank");
        commandTimeOut = Integer.parseInt(commandTimeOutProperty);
    }
}
