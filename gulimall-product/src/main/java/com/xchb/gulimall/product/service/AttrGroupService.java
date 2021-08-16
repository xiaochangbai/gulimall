package com.xchb.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xchb.gulimall.common.utils.PageUtils;
import com.xchb.gulimall.product.entity.AttrGroupEntity;
import com.xchb.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.xchb.gulimall.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author xchb
 * @email HeJieLin@gulimall.com
 * @date 2020-05-22 19:00:18
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

