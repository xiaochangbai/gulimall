package com.xchb.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xchb.gulimall.common.utils.PageUtils;
import com.xchb.gulimall.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author xchb
 * @email HeJieLin@gulimall.com
 * @date 2020-05-22 19:49:53
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

