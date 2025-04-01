package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.Memory;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 记忆Mapper接口
 */
@Mapper
public interface MemoryMapper {

    /**
     * 根据ID查询记忆
     */
    @Select("SELECT * FROM memory WHERE id = #{id}")
    Memory findById(@Param("id") String id);

    /**
     * 根据用户ID查询记忆列表
     */
    @Select("SELECT * FROM memory WHERE user_id = #{userId} ORDER BY memory_date DESC LIMIT #{offset}, #{size}")
    List<Memory> findByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 根据用户ID和数字人ID查询记忆列表
     */
    @Select("SELECT * FROM memory WHERE user_id = #{userId} AND digital_human_id = #{digitalHumanId} ORDER BY memory_date DESC LIMIT #{offset}, #{size}")
    List<Memory> findByUserIdAndDigitalHumanId(@Param("userId") String userId, @Param("digitalHumanId") String digitalHumanId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 插入新记忆
     */
    @Insert("INSERT INTO memory(id, user_id, digital_human_id, title, content, memory_date, image_url) " +
            "VALUES(#{id}, #{userId}, #{digitalHumanId}, #{title}, #{content}, #{memoryDate}, #{imageUrl})")
    int insert(Memory memory);

    /**
     * 更新记忆
     */
    @Update("UPDATE memory SET title = #{title}, content = #{content}, memory_date = #{memoryDate}, " +
            "image_url = #{imageUrl}, update_time = NOW() WHERE id = #{id}")
    int update(Memory memory);

    /**
     * 删除记忆
     */
    @Delete("DELETE FROM memory WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    /**
     * 计算用户记忆总数
     */
    @Select("SELECT COUNT(*) FROM memory WHERE user_id = #{userId}")
    int countByUserId(@Param("userId") String userId);

    /**
     * 计算用户和特定数字人的记忆总数
     */
    @Select("SELECT COUNT(*) FROM memory WHERE user_id = #{userId} AND digital_human_id = #{digitalHumanId}")
    int countByUserIdAndDigitalHumanId(@Param("userId") String userId, @Param("digitalHumanId") String digitalHumanId);
} 