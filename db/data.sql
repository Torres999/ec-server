-- 插入用户测试数据
INSERT INTO `user` (`id`, `user_id`, `name`, `description`, `avatar`, `email`, `register_date`, `vip_level`, `vip_expire_date`, `balance`)
VALUES
('wx_123456789', '888888', '张三', '情感陪伴用户', 'https://example.com/avatar1.jpg', 'zhangsan@example.com', '2024-01-15 10:00:00', 1, '2024-12-31 23:59:59', 100.00),
('wx_987654321', '999999', '李四', '情感陪伴用户', 'https://example.com/avatar2.jpg', 'lisi@example.com', '2024-02-10 14:30:00', 0, NULL, 50.00),
('wx_111222333', '777777', '王五', '情感陪伴用户', 'https://example.com/avatar3.jpg', 'wangwu@example.com', '2024-03-05 09:15:00', 2, '2025-03-04 23:59:59', 200.00);

-- 插入数字人测试数据
INSERT INTO `digital_human` (`id`, `user_id`, `name`, `relation`, `personality`, `avatar_url`, `last_chat_time`)
VALUES
('1', 'wx_123456789', '女儿', '亲子', '温柔善解人意', 'https://example.com/avatar_dh1.jpg', '2024-03-24 10:30:00'),
('2', 'wx_123456789', '儿子', '亲子', '聪明伶牙俐齿', 'https://example.com/avatar_dh2.jpg', '2024-03-23 18:15:00'),
('3', 'wx_123456789', '小明', '好友', '温柔善解人意', 'https://example.com/avatar_dh3.jpg', '2024-03-22 12:40:00'),
('4', 'wx_987654321', '妈妈', '亲子', '温柔善解人意', 'https://example.com/avatar_dh4.jpg', '2024-03-20 15:45:00'),
('5', 'wx_111222333', '爸爸', '亲子', '聪明伶牙俐齿', 'https://example.com/avatar_dh5.jpg', '2024-03-18 09:30:00');

-- 插入聊天会话测试数据
INSERT INTO `chat_session` (`id`, `user_id`, `digital_human_id`, `start_time`, `end_time`, `duration`, `status`)
VALUES
('chat_123456789', 'wx_123456789', '1', '2024-03-24 10:00:00', '2024-03-24 10:15:30', 930, 'ended'),
('chat_987654321', 'wx_123456789', '2', '2024-03-23 18:00:00', '2024-03-23 18:20:00', 1200, 'ended'),
('chat_111222333', 'wx_987654321', '4', '2024-03-20 15:30:00', '2024-03-20 15:50:00', 1200, 'ended'),
('chat_444555666', 'wx_111222333', '5', '2024-03-18 09:00:00', '2024-03-18 09:45:00', 2700, 'ended');

-- 插入记忆测试数据
INSERT INTO `memory` (`id`, `user_id`, `digital_human_id`, `title`, `content`, `memory_date`, `image_url`)
VALUES
('1', 'wx_123456789', '1', '第一次视频对话', '今天和女儿进行了第一次视频对话，她看起来很开心...', '2024-03-15 14:30:00', NULL),
('2', 'wx_123456789', '2', '分享生活趣事', '和儿子分享了今天的工作和生活，他给了我很多建议...', '2024-03-14 20:15:00', 'https://example.com/memory2.jpg'),
('3', 'wx_987654321', '4', '母亲节聊天', '今天是母亲节，和妈妈进行了视频聊天，聊了很多童年往事...', '2024-05-12 10:00:00', 'https://example.com/memory3.jpg'),
('4', 'wx_111222333', '5', '父亲近况', '和爸爸聊了近况，他看起来很健康，给了我很多人生建议...', '2024-03-10 16:45:00', NULL);

-- 插入充值套餐测试数据
INSERT INTO `package` (`id`, `name`, `description`, `price`, `original_price`, `duration`, `benefits`, `status`)
VALUES
('1', '月卡', '30天无限视频对话', 30.00, 40.00, 30, '["无限视频对话", "优先客服支持"]', 'active'),
('2', '季卡', '90天无限视频对话', 80.00, 120.00, 90, '["无限视频对话", "优先客服支持", "专属头像框"]', 'active'),
('3', '年卡', '365天无限视频对话', 298.00, 480.00, 365, '["无限视频对话", "优先客服支持", "专属头像框", "生日提醒"]', 'active');

-- 插入充值订单测试数据
INSERT INTO `payment_order` (`id`, `user_id`, `package_id`, `amount`, `payment_method`, `status`, `create_time`, `payment_time`, `expiry_time`, `transaction_id`)
VALUES
('order_123456789', 'wx_123456789', '1', 30.00, 'wechat', 'paid', '2024-03-25 18:30:00', '2024-03-25 18:35:20', '2024-03-25 18:45:00', 'wx_trans_123456'),
('order_987654321', 'wx_987654321', '2', 80.00, 'alipay', 'paid', '2024-02-15 10:15:00', '2024-02-15 10:20:30', '2024-02-15 10:30:00', 'alipay_trans_987654'),
('order_111222333', 'wx_111222333', '1', 30.00, 'wechat', 'failed', '2024-01-05 14:20:00', NULL, '2024-01-05 14:35:00', NULL);

-- 插入充值记录测试数据
INSERT INTO `payment_record` (`id`, `order_id`, `user_id`, `package_name`, `amount`, `payment_method`, `status`, `payment_time`, `validity_start`, `validity_end`)
VALUES
('record_1', 'order_123456789', 'wx_123456789', '月卡', 30.00, '微信支付', '支付成功', '2024-03-25 18:35:20', '2024-03-25 00:00:00', '2024-04-24 23:59:59'),
('record_2', 'order_987654321', 'wx_987654321', '季卡', 80.00, '支付宝', '支付成功', '2024-02-15 10:20:30', '2024-02-15 00:00:00', '2024-05-15 23:59:59'); 