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

package io.xianzhi.common.context;

import io.xianzhi.common.code.CommonCode;
import io.xianzhi.common.exception.BizException;

/**
 * User Information Context<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public class UserContext {

    /**
     * User Information Context
     */
    private static final ThreadLocal<UserBO> USER_BO_THREAD_LOCAL = new InheritableThreadLocal<>();


    /**
     * Get the current user ID
     *
     * @return current user ID
     */
    public static String getCurrentUserId() {
        UserBO userBO = USER_BO_THREAD_LOCAL.get();
        if (null == userBO) {
            throw new BizException(CommonCode.UNAUTHORIZED);
        }
        return userBO.getId();
    }

    /**
     * Get the current user
     *
     * @return current user
     */
    public static UserBO get() {
        return USER_BO_THREAD_LOCAL.get();
    }

    /**
     * Set the current user
     *
     * @param userBO current user
     */
    public static void set(UserBO userBO) {
        USER_BO_THREAD_LOCAL.set(userBO);
    }
}

