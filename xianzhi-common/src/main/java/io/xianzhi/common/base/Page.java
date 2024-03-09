package io.xianzhi.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页基础条件<br>
 *
 * @author Ethan Wang
 * @since 1.0.0
 */
@Data
public class Page implements Serializable {
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页数量
     */
    private Integer pageSize;
}
