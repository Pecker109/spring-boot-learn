package com.example.demo.model.request;

import lombok.Data;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-10-09
 */
@Data
public class BasePageRequest {
    /**
     * pageNumber
     */
    private Integer pageNumber;
    /**
     * pageSize
     */
    private Integer pageSize;
}
