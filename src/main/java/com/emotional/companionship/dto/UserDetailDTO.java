package com.emotional.companionship.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户详细信息数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailDTO extends UserDTO {
    /**
     * 数字人总数
     */
    private Integer totalDigitalHumans;
    
    /**
     * 聊天总分钟数
     */
    private Integer totalChatMinutes;
} 