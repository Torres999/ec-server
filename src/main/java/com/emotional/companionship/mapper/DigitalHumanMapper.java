package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.DigitalHuman;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 数字人Mapper接口
 */
@Mapper
public interface DigitalHumanMapper {

    /**
     * 根据用户ID查询所有数字人
     */
    @Select("SELECT * FROM digital_human WHERE user_id = #{userId} ORDER BY last_chat_time DESC")
    List<DigitalHuman> selectByUserId(@Param("userId") String userId);

    /**
     * 根据ID查询数字人
     */
    @Select("SELECT * FROM digital_human WHERE id = #{id}")
    DigitalHuman selectById(@Param("id") String id);

    /**
     * 根据ID和用户ID查询数字人，用于验证数字人是否属于当前用户
     */
    @Select("SELECT * FROM digital_human WHERE id = #{id} AND user_id = #{userId}")
    DigitalHuman selectByIdAndUserId(@Param("id") String id, @Param("userId") String userId);

    /**
     * 插入数字人
     */
    @Insert("INSERT INTO digital_human(id, user_id, name, relation, personality, avatar_url, last_chat_time) " +
            "VALUES(#{id}, #{userId}, #{name}, #{relation}, #{personality}, #{avatarUrl}, #{lastChatTime})")
    int insert(DigitalHuman digitalHuman);

    /**
     * 更新数字人
     */
    @Update("UPDATE digital_human SET name = #{name}, relation = #{relation}, " +
            "personality = #{personality}, avatar_url = #{avatarUrl}, " +
            "update_time = NOW() WHERE id = #{id}")
    int update(DigitalHuman digitalHuman);

    /**
     * 删除数字人
     */
    @Delete("DELETE FROM digital_human WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    /**
     * 更新最后聊天时间
     */
    @Update("UPDATE digital_human SET last_chat_time = #{lastChatTime}, update_time = NOW() WHERE id = #{id}")
    int updateLastChatTime(@Param("id") String id, @Param("lastChatTime") java.util.Date lastChatTime);

    /**
     * 计算用户的数字人总数
     */
    @Select("SELECT COUNT(*) FROM digital_human WHERE user_id = #{userId}")
    int countByUserId(@Param("userId") String userId);
} 