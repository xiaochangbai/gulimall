# 谷粒商城简介

### 项目介绍

gulimall（谷粒商城） 项目是一套电商项目，包括前台商城系统以及后台管理系统，基于 SpringCloud + SpringCloudAlibaba + MyBatis-Plus实现，采用 Docker 容器化部署。前台商城系统包括：用户登录、注册、商品搜索、商品详情、购物车、下订单流程、秒杀活动等模块。后台管理系统包括：系统管理、商品系统、优惠营销、库存系统、订单系统、用户系统、内容管理等七大模块。


### 组织结构

```
gulimall
├── gulimall-admin  后台管理系统
├── gulimall-admin-vue 后台管理系统前端
├── gulimall-auth-server -- 认证中心（社交登录、OAuth2.0、单点登录）
├── gulimall-cart -- 购物车服务
├── gulimall-codegenerator -- 项目的代码生成器
├── gulimall-common -- 工具类及通用代码
├── gulimall-coupon -- 优惠卷服务
├── gulimall-gateway -- 统一配置网关
├── gulimall-member -- 会员服务
├── gulimall-order -- 订单服务
├── gulimall-product -- 商品服务
├── gulimall-search -- 检索服务
├── gulimall-seckill -- 秒杀服务
├── gulimall-third-party -- 第三方服务
└── gulimall-ware -- 仓储服务

```


### 技术选型

**后端技术**

|        技术        |           说明           |
| :----------------: | :----------------------: | 
|     SpringBoot     |       容器+MVC框架       |    
|    SpringCloud     |        微服务架构        |  
| SpringCloudAlibaba |        一系列组件        |
|    MyBatis-Plus    |         ORM框架          |
|   Elasticsearch    |         搜索引擎         | 
|      RabbitMQ      |         消息队列         | 
|   Springsession    |        分布式缓存        | 
|      Redisson      |         分布式锁         | 
|       Docker       |       应用容器引擎       | 
|        Minio       |        对象云存储        |
|   swagger+knife4j  |        接口文档        |

**前端技术**

|   技术    |    说明    |           官网            |
| :-------: | :--------: | :-----------------------: |
|    Vue    |  前端框架  |     https://vuejs.org     |
|  Element  | 前端UI框架 | https://element.eleme.io  |
| thymeleaf |  模板引擎  | https://www.thymeleaf.org |
|  node.js  | 服务端的js |   https://nodejs.org/en   |

### 架构图

**系统架构图**

[![UUvRAS.png](https://images.gitee.com/uploads/images/2020/0714/193425_4a1056c4_4914148.png)](https://imgchr.com/i/UUvRAS)


### 如果你喜欢，要是觉得对你有帮助的话，请点个赞是对我最大的支持！


