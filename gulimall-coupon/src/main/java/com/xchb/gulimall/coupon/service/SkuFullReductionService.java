package com.xchb.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xchb.gulimall.common.to.SkuReductionTo;
import com.xchb.gulimall.common.utils.PageUtils;
import com.xchb.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author xchb
 * @email HeJieLin@gulimall.com
 * @date 2020-05-22 19:35:30
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

