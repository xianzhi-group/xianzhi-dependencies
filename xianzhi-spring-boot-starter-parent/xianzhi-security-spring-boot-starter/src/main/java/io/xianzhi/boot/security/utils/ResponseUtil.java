package io.xianzhi.boot.security.utils;


import com.alibaba.fastjson2.JSON;
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
            response.getWriter().write(JSON.toJSONString(result));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
