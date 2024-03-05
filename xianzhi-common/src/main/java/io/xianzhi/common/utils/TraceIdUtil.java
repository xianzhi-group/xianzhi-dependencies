/*
 * Copyright (c) 2023-2024  XianZhi Group  All Rights
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

package io.xianzhi.common.utils;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;

/**
 * TraceIdUtil
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public class TraceIdUtil {
    /**
     * Define the traceId key
     */
    public static final String TRACE_ID = "X-Trace-Id";

    /**
     * structure
     */
    private TraceIdUtil() {
    }


    /**
     * get current traceId
     *
     * @return current traceId
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    /**
     * generate traceId
     */
    public static void generateTraceId() {
        MDC.put(TRACE_ID, IdUtil.fastSimpleUUID());
    }

    /**
     * remove traceId
     */
    public static void removeTraceId() {
        MDC.remove(TRACE_ID);
    }
}
