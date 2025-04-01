package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.Package;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 充值套餐Mapper接口
 */
@Mapper
public interface PackageMapper {

    /**
     * 根据ID查询套餐
     */
    @Select("SELECT * FROM package WHERE id = #{id}")
    Package findById(@Param("id") String id);

    /**
     * 查询所有有效套餐
     */
    @Select("SELECT * FROM package WHERE status = 'active' ORDER BY price ASC")
    List<Package> findAllActive();

    /**
     * 查询所有套餐
     */
    @Select("SELECT * FROM package ORDER BY price ASC")
    List<Package> findAll();

    /**
     * 插入新套餐
     */
    @Insert("INSERT INTO package(id, name, description, price, original_price, duration, benefits, status) " +
            "VALUES(#{id}, #{name}, #{description}, #{price}, #{originalPrice}, #{duration}, #{benefits}, #{status})")
    int insert(Package pkg);

    /**
     * 更新套餐
     */
    @Update("UPDATE package SET name = #{name}, description = #{description}, price = #{price}, " +
            "original_price = #{originalPrice}, duration = #{duration}, benefits = #{benefits}, " +
            "status = #{status}, update_time = NOW() WHERE id = #{id}")
    int update(Package pkg);

    /**
     * 更新套餐状态
     */
    @Update("UPDATE package SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") String id, @Param("status") String status);
} 