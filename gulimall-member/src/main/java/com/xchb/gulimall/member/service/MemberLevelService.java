package com.xchb.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xchb.gulimall.common.utils.PageUtils;
import com.xchb.gulimall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author xchb
 * @email HeJieLin@gulimall.com
 * @date 2020-05-22 19:42:06
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

