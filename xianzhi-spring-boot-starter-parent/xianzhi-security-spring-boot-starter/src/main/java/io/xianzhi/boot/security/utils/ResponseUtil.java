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

package io.xianzhi.boot.security.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.xianzhi.common.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应工具类<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Slf4j
public class ResponseUtil {
    /**
     * 响应JSON到前端
     *
     * @param result   响应的数据
     * @param response 响应对象
     */
    public static void responseUtf8(Result result, HttpServletResponse response) {
        try {
            // 设置响应头和字符编码
            response.setContentType("application/json;charset=UTF-8");
            // 将JSON字符串写入响应体中
            response.getWriter().write(JSON.toJSONString(result, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
