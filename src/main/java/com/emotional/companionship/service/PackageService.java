package com.emotional.companionship.service;

import com.emotional.companionship.entity.Package;

import java.util.List;

/**
 * 充值套餐服务接口
 */
public interface PackageService {

    /**
     * 获取所有有效套餐
     */
    List<Package> getAllActivePackages();

    /**
     * 根据ID获取套餐
     */
    Package getPackageById(String id);

    /**
     * 创建套餐
     */
    Package createPackage(Package pkg);

    /**
     * 更新套餐
     */
    Package updatePackage(Package pkg);

    /**
     * 更新套餐状态
     */
    boolean updatePackageStatus(String id, String status);

    /**
     * 检查套餐是否存在
     */
    boolean isPackageExist(String id);

    /**
     * 检查套餐是否有效
     */
    boolean isPackageActive(String id);
} 