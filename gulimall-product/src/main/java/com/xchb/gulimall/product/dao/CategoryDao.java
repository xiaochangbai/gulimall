package com.xchb.gulimall.product.dao;

import com.xchb.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author xchb
 * @email HeJieLin@gulimall.com
 * @date 2020-05-22 19:00:18
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
