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

import lombok.Data;

import java.util.List;

/**
 * page list result
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Data
public class ListResult<E> {

    /**
     * The data that needs to be returned to the caller
     */
    private List<E> list;

    /**
     * The total number of data
     */
    private long total;


}
