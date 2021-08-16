package com.xchb.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xchb.gulimall.common.utils.PageUtils;
import com.xchb.gulimall.common.utils.Query;
import com.xchb.gulimall.coupon.dao.SeckillPromotionDao;
import com.xchb.gulimall.coupon.entity.SeckillPromotionEntity;
import com.xchb.gulimall.coupon.service.SeckillPromotionService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("seckillPromotionService")
public class SeckillPromotionServiceImpl extends ServiceImpl<SeckillPromotionDao, SeckillPromotionEntity> implements SeckillPromotionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillPromotionEntity> page = this.page(
                new Query<SeckillPromotionEntity>().getPage(params),
                new QueryWrapper<SeckillPromotionEntity>()
        );

        return new PageUtils(page);
    }

}