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

package io.xianzhi.common.result;

/**
 * Return the top-level interface of the result, and all response information
 * returned to the caller should be the implementation class of this interface
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
public interface Result {

    /**
     * This method returns custom response status codes,
     * non HTTP status codes, or other response status codes
     *
     * @return custom response status code
     */
    String code();

    /**
     * This method returns whether the operation was successful or not
     *
     * @return whether the operation was successful or not
     */
    boolean success();

    /**
     * This method returns custom prompt information
     *
     * @return custom prompt information
     */
    String message();
}
