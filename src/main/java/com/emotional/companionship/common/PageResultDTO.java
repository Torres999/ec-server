package com.emotional.companionship.common;

import lombok.Data;
import java.util.List;

/**
 * 分页结果数据传输对象
 */
@Data
public class PageResultDTO<T> {
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 当前页码
     */
    private int page;
    
    /**
     * 每页大小
     */
    private int size;
} 