package com.xchb.gulimall.gateway.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: xchb
 * @createTime: 2020-07-13 17:18
 **/

@Configuration
public class SentinelGatewayConfig {

    public SentinelGatewayConfig() {

        //todo sentinel
//        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
//            //网关限流了请求，就会调用此回调
//            @Override
//            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
//
//                R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMessage());
//                String errorJson = JSON.toJSONString(error);
//
//                Mono<ServerResponse> body = ServerResponse.ok().body(Mono.just(errorJson), String.class);
//                return body;
//            }
//        });

    }

}
