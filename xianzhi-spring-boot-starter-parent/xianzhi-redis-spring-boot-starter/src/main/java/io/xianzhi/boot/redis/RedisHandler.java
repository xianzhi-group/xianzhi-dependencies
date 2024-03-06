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

package io.xianzhi.boot.redis;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis handler
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public class RedisHandler {

    /**
     * redisTemplate
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * set a cache
     *
     * @param key cache key
     * @param obj cache value
     */
    public void valueSet(String key, Object obj) {
        valueSet(key, obj, 0L);
    }

    /**
     * set a cache and set the expiration time, the unit is seconds
     *
     * @param key  cache key
     * @param obj  cache value
     * @param time cache expiration time
     */
    public void valueSet(String key, Object obj, Long time) {
        valueSet(key, obj, time, TimeUnit.SECONDS);
    }

    /**
     * set a cache and set the expiration time, the unit is seconds
     *
     * @param key      cache key
     * @param obj      cache value
     * @param time     cache expiration time
     * @param timeUnit cache expiration time unit
     */
    public void valueSet(String key, Object obj, Long time, TimeUnit timeUnit) {
        if (null == time || time <= 0) {
            redisTemplate.opsForValue().set(key, obj);
        } else {
            redisTemplate.opsForValue().set(key, obj, time, timeUnit);
        }
    }

    /**
     * get cache
     *
     * @param key cache key
     * @return cache value
     */
    public Object valueGet(String key) {
        if (StringUtils.hasText(key) && Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    /**
     * get cache
     *
     * @param key    cache key
     * @param tClass return type
     * @param <T>    cache generic
     * @return cache value
     */
    public <T> T valueGet(String key, Class<T> tClass) {
        Object obj = valueGet(key);
        return null == obj ? null : JSON.parseObject(JSON.toJSONString(obj), tClass);

    }


    /**
     * set key expiration
     *
     * @param key need to set the expiration time of the key
     */
    public void expire(String key) {
        expire(key, null);

    }

    /**
     * get key expiration time
     *
     * @param key need to get the expiration time of the key
     * @return expiration time
     */
    public long getExpire(String key) {
        if (StringUtils.hasText(key)) {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        }
        return 0L;
    }

    /**
     * set expiration time
     *
     * @param key  need to set the expiration time of the key
     * @param time expiration time
     */
    public void expire(String key, Long time) {
        expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * set expiration time and specify the unit at the same time
     *
     * @param key      need to set the expiration time of the key
     * @param time     expiration time
     * @param timeUnit expiration time unit
     */
    public void expire(String key, Long time, TimeUnit timeUnit) {
        if (StringUtils.hasText(key)) {
            if (null == time || time <= 0) {
                redisTemplate.expire(key, 0, TimeUnit.SECONDS);
            } else {
                redisTemplate.expire(key, time, timeUnit);
            }
        }
    }

    /**
     * deleted cache
     *
     * @param key cache key
     */
    public void deleted(String key) {
        if (StringUtils.hasText(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * deleted cache
     *
     * @param kes cache keys
     */
    public void deleted(List<String> kes) {
        if (!ObjectUtils.isEmpty(kes)) {
            redisTemplate.delete(kes);
        }
    }
}
