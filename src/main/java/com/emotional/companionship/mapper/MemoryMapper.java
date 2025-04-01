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
    Memory selectById(@Param("id") String id);

    /**
     * 根据用户ID查询记忆列表
     */
    @Select("SELECT * FROM memory WHERE user_id = #{userId} ORDER BY memory_date DESC LIMIT #{offset}, #{size}")
    List<Memory> selectByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 根据数字人ID查询记忆列表
     */
    @Select("SELECT * FROM memory WHERE digital_human_id = #{digitalHumanId} ORDER BY memory_date DESC LIMIT #{offset}, #{size}")
    List<Memory> selectByDigitalHumanId(@Param("digitalHumanId") String digitalHumanId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 统计用户记忆总数
     */
    @Select("SELECT COUNT(*) FROM memory WHERE user_id = #{userId}")
    int countByUserId(@Param("userId") String userId);

    /**
     * 统计数字人记忆总数
     */
    @Select("SELECT COUNT(*) FROM memory WHERE digital_human_id = #{digitalHumanId}")
    int countByDigitalHumanId(@Param("digitalHumanId") String digitalHumanId);

    /**
     * 插入记忆
     */
    @Insert("INSERT INTO memory(id, user_id, digital_human_id, title, content, memory_date, image_url) " +
            "VALUES(#{id}, #{userId}, #{digitalHumanId}, #{title}, #{content}, #{memoryDate}, #{imageUrl})")
    int insert(Memory memory);

    /**
     * 删除记忆
     */
    @Delete("DELETE FROM memory WHERE id = #{id}")
    int deleteById(@Param("id") String id);
} 