# 项目总结

（牛客网学习）头条资讯，包含登录注册，添加资讯，上传图片，评论，点赞，站内信，使用技术：前端使用模版FreeMarker,后台：springboot，mybatis,redis,拦截器，实现消息队列，异步更新数据库 

------

## 项目总的架构：freeMaker+springboot+mybatis+redis+七牛云图片存储（实时缩图+cdn加速）+异步处理

------

## 访问地址  

[github地址](https://github.com/staringOrz/toutiaoNews)

[项目展示](http://119.29.20.88:8080/)

------

## 演示截图

1. 首页

   ![1531532751582](http://pbu6wqsbp.bkt.clouddn.com/%E9%A6%96%E9%A1%B5.png)

2. 登录注册

   ![1531532787746](http://pbu6wqsbp.bkt.clouddn.com/%E7%99%BB%E5%BD%95.png)

3. 评论

   ![1531532902287](http://pbu6wqsbp.bkt.clouddn.com/%E8%AF%84%E8%AE%BA.png)

4. 站内信

   ![1531532951116](http://pbu6wqsbp.bkt.clouddn.com/%E7%AB%99%E5%86%85%E4%BF%A1.png)

5. 站内信详情

    

   ![1531532999704](http://pbu6wqsbp.bkt.clouddn.com/%E7%AB%99%E5%86%85%E4%BF%A1%E8%AF%A6%E6%83%85.png)

   

------

## 项目目录结构

![1531532170106](http://pbu6wqsbp.bkt.clouddn.com/%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png)

|     命名      |                      描述                      |
| :-----------: | :--------------------------------------------: |
|     bean      |         实体类，所有的model类都存在这          |
|  controller   |        控制层，相应前端请求，调用服务层        |
|      dao      |                 操作数据库了层                 |
|      vo       | 展示数据实体层，我写在了bean里面，可以独立出来 |
|    service    |     服务层，controller层调用，与dao层交互      |
|     util      |     工具类，与业务无关常用的工具类（静态）     |
|  intercepter  |                     拦截器                     |
| configuration |              配置 注册拦截器顺序               |
|     aysnc     |    里面写异步操作（包括事件注册，事件消费）    |

------

## 实现的功能

- 登录注册
- 资讯页面展示
- 图片上传
- 内容点赞
- 资讯详情评论
- 站内信

------

## 登录注册

- 使用md加随机salt加密,保证数据安全
- 登录成功产生Tiket,Ticket对应用户信息，Tiket保存在redis上，用户再次登录带上Ticket,优先在redis校验Tiket的合法性，一旦匹配，登录成功，返回用户信息，否则在数据库查询用户名密码是否正确。这样做加速登录速度，削减数据库压力
- 关于如何保持Tiket,通过JSON序列化用户信息，存储在redis上，取数据时，通过JSON反序列化，得到，用户对于的类
- 使用本地线程ThreadLocal 保存各自用户信息，使得登录成功的用户每次请求服务器时不需要传入用户Id,也可以获得用户信息。

------

## 资讯页面展示

- 通过数据库查询出资讯列表，放入页表展示类中，然后想model里面存入放回的信息，通过freemaker模版渲染出对应的每一个资讯页面

------

## 图片上传

- 图片上传与下载是实质都是图片文件转换成流对象，写到目的流对象（其实很简单吧）
- 我使用了七牛云提供的云存储，好处是七牛云提供很好的增值服务，如实时缩图，CDN加速，这可以大大优化系统性能，最重要的是**免费**

------

## 内容点赞

- 用户点赞，点赞是一件经常频繁发生的事情，如果每次点赞都去操作数据库，人多起来，数据库就会吃不消，但是总的点赞数一定要确保正确，于是可以这样做，点赞与取消点赞的实时操作，交给redis处理，返回的点赞总也由redis返回，这样就可以大大优化网页性能，至于数据库数据的同步，完全可以开一个异步消息，让线程异步更新数据库

------

## 资讯详情页评论

- 用户评论了某条信息，想数据库插入评论内容，返回是否评论成功，然后异步的想被评论的用户发送一条站内信，对于被评论者受到被评论了具有一点延时性，但是这完全可以被接受，而且大大提升了用户体验。

------

## 站内信

- 没什么好说，出现数据库，查询出于他相关的信，然后对应每一个会发分组，并且选择最新的，以及该会话信息。点击进去就展示该会话详情。并且异步更新，该会话以及被read 过了。

------

## 关于异步处理小框架是实现

一个分为5部分

1. 处理的事件Event类（通用）
2. 事件类型Type(用来标识每个事件的类型应该给哪个对应的处理类执行)
3. 执行处理的类（包含一个统一接口）
4. 事件发起类（生产者producer）
5. 接收事件消费类（消费者consumer）

------

## 关于项目后期的扩展

- 添加页面分页功能
- 写一个爬虫程序，实时更新页面资讯，让页面看起来一直有人在维护
- 添加扩展功能，个人设置（包含个人信息填写与修改）

------

**Author:邓喜健**

联系：923124803@qq.com
