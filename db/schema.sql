-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` VARCHAR(64) NOT NULL COMMENT '用户唯一标识',
  `user_id` VARCHAR(20) NOT NULL COMMENT '用户展示ID',
  `name` VARCHAR(50) NOT NULL COMMENT '用户名称',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '用户描述',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `register_date` DATETIME NOT NULL COMMENT '注册日期',
  `vip_level` INT DEFAULT 0 COMMENT 'VIP等级',
  `vip_expire_date` DATETIME DEFAULT NULL COMMENT 'VIP到期日期',
  `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 数字人表
CREATE TABLE IF NOT EXISTS `digital_human` (
  `id` VARCHAR(64) NOT NULL COMMENT '数字人唯一标识',
  `user_id` VARCHAR(64) NOT NULL COMMENT '所属用户ID',
  `name` VARCHAR(50) NOT NULL COMMENT '数字人称呼',
  `relation` VARCHAR(20) NOT NULL COMMENT '关系，可选值: "亲子"、"好友"、"其他"',
  `personality` VARCHAR(50) NOT NULL COMMENT '性格特征，可选值: "温柔善解人意"、"聪明伶牙俐齿"',
  `avatar_url` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `last_chat_time` DATETIME DEFAULT NULL COMMENT '上次对话时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数字人表';

-- 聊天会话表
CREATE TABLE IF NOT EXISTS `chat_session` (
  `id` VARCHAR(64) NOT NULL COMMENT '会话唯一标识',
  `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID',
  `digital_human_id` VARCHAR(64) NOT NULL COMMENT '数字人ID',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  `duration` INT DEFAULT 0 COMMENT '持续时间(秒)',
  `status` VARCHAR(20) NOT NULL COMMENT '状态: "ongoing", "ended"',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_digital_human_id` (`digital_human_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天会话表';

-- 记忆表
CREATE TABLE IF NOT EXISTS `memory` (
  `id` VARCHAR(64) NOT NULL COMMENT '记忆唯一标识',
  `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID',
  `digital_human_id` VARCHAR(64) NOT NULL COMMENT '数字人ID',
  `title` VARCHAR(100) NOT NULL COMMENT '记忆标题',
  `content` TEXT NOT NULL COMMENT '记忆内容',
  `memory_date` DATETIME NOT NULL COMMENT '记忆日期',
  `image_url` VARCHAR(255) DEFAULT NULL COMMENT '图片URL',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_digital_human_id` (`digital_human_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记忆表';

-- 充值套餐表
CREATE TABLE IF NOT EXISTS `package` (
  `id` VARCHAR(64) NOT NULL COMMENT '套餐唯一标识',
  `name` VARCHAR(50) NOT NULL COMMENT '套餐名称',
  `description` VARCHAR(200) NOT NULL COMMENT '套餐描述',
  `price` DECIMAL(10,2) NOT NULL COMMENT '当前价格',
  `original_price` DECIMAL(10,2) NOT NULL COMMENT '原价',
  `duration` INT NOT NULL COMMENT '有效期(天)',
  `benefits` VARCHAR(500) NOT NULL COMMENT '套餐权益列表，JSON格式',
  `status` VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '状态: "active", "inactive"',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值套餐表';

-- 充值订单表
CREATE TABLE IF NOT EXISTS `payment_order` (
  `id` VARCHAR(64) NOT NULL COMMENT '订单唯一标识',
  `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID',
  `package_id` VARCHAR(64) NOT NULL COMMENT '套餐ID',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
  `payment_method` VARCHAR(20) NOT NULL COMMENT '支付方式: "wechat", "alipay"',
  `status` VARCHAR(20) NOT NULL COMMENT '订单状态: "created", "paid", "failed", "cancelled"',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `payment_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `expiry_time` DATETIME NOT NULL COMMENT '订单过期时间',
  `transaction_id` VARCHAR(100) DEFAULT NULL COMMENT '第三方支付交易ID',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值订单表';

-- 充值记录表
CREATE TABLE IF NOT EXISTS `payment_record` (
  `id` VARCHAR(64) NOT NULL COMMENT '记录唯一标识',
  `order_id` VARCHAR(64) NOT NULL COMMENT '订单ID',
  `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID',
  `package_name` VARCHAR(50) NOT NULL COMMENT '套餐名称',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
  `payment_method` VARCHAR(20) NOT NULL COMMENT '支付方式',
  `status` VARCHAR(20) NOT NULL COMMENT '支付状态',
  `payment_time` DATETIME NOT NULL COMMENT '支付时间',
  `validity_start` DATETIME NOT NULL COMMENT '有效期开始',
  `validity_end` DATETIME NOT NULL COMMENT '有效期结束',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录表'; 