package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.ChatSession;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 聊天会话Mapper接口
 */
@Mapper
public interface ChatSessionMapper {

    /**
     * 根据ID查询会话
     */
    @Select("SELECT * FROM chat_session WHERE id = #{id}")
    ChatSession selectById(@Param("id") String id);

    /**
     * 根据用户ID查询会话列表
     */
    @Select("SELECT * FROM chat_session WHERE user_id = #{userId} ORDER BY start_time DESC")
    List<ChatSession> selectByUserId(@Param("userId") String userId);

    /**
     * 查询用户与特定数字人的会话列表
     */
    @Select("SELECT * FROM chat_session WHERE user_id = #{userId} AND digital_human_id = #{digitalHumanId} ORDER BY start_time DESC")
    List<ChatSession> findByUserIdAndDigitalHumanId(@Param("userId") String userId, @Param("digitalHumanId") String digitalHumanId);

    /**
     * 查询用户进行中的会话
     */
    @Select("SELECT * FROM chat_session WHERE user_id = #{userId} AND status = 'ongoing' LIMIT 1")
    ChatSession findOngoingSessionByUserId(@Param("userId") String userId);

    /**
     * 插入新会话
     */
    @Insert("INSERT INTO chat_session(id, user_id, digital_human_id, start_time, status) " +
            "VALUES(#{id}, #{userId}, #{digitalHumanId}, #{startTime}, #{status})")
    int insert(ChatSession chatSession);

    /**
     * 更新会话状态
     */
    @Update("UPDATE chat_session SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") String id, @Param("status") String status);

    /**
     * 结束会话
     */
    @Update("UPDATE chat_session SET end_time = #{endTime}, duration = #{duration}, status = 'ended', update_time = NOW() WHERE id = #{id}")
    int endSession(@Param("id") String id, @Param("endTime") Date endTime, @Param("duration") int duration);

    /**
     * 计算用户的总聊天时间(分钟)
     */
    @Select("SELECT IFNULL(SUM(duration), 0) FROM chat_session WHERE user_id = #{userId} AND status = 'ended'")
    int getTotalChatDurationByUserId(@Param("userId") String userId);

    /**
     * 根据数字人ID查询会话列表
     */
    @Select("SELECT * FROM chat_session WHERE digital_human_id = #{digitalHumanId} ORDER BY start_time DESC")
    List<ChatSession> selectByDigitalHumanId(@Param("digitalHumanId") String digitalHumanId);

    /**
     * 删除会话
     */
    @Delete("DELETE FROM chat_session WHERE id = #{id}")
    int deleteById(@Param("id") String id);
} 