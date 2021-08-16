package com.xchb.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xchb.gulimall.product.entity.AttrGroupEntity;
import com.xchb.gulimall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author xchb
 * @email HeJieLin@gulimall.com
 * @date 2020-05-22 19:00:18
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
