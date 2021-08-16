package com.xchb.gulimall.product.fallback;

import com.xchb.gulimall.common.exception.BizCodeEnum;
import com.xchb.gulimall.common.utils.R;
import com.xchb.gulimall.product.feign.SeckillFeignService;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: xchb
 * @createTime: 2020-07-13 14:45
 **/

@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {
    @Override
    public R getSkuSeckilInfo(Long skuId) {
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(),BizCodeEnum.TO_MANY_REQUEST.getMessage());
    }
}
