# Atcrowdfunding尚筹网
帮助创业者发布创业项目，向大众募集启动资金的融资平台
## 零、配套资料
链接：https://pan.baidu.com/s/1P6PjljT9KnWldWnICQazfg
提取码：epmk
复制这段内容后打开百度网盘手机App，操作更方便哦
## 一、后台管理系统
### 1.1 管理员登录 
#### ①MD5 密码加密 
#### ②使用自定义拦截器检查登录状态 
### 1.2 管理员维护 
#### ①使用 MyBatis 的 PageHelper 插件实现分页 
#### ②在页面上使用了 Pagination 实现数字页码 
#### ③在数据库表中给 loginacct 字段添加唯一约束，在保存了重复的 loginacct 时触发异常，从而保证登录账号的唯一 
### 1.3 角色维护 
#### 以 Ajax 交互方式实现数据的增删改查操作 
### 1.4 菜单维护 
#### ①使用 zTree 在页面上显示树形结构 
#### ②并结合 zTree 提供的 API 显示自定义图标 
#### ③对树形节点进行增删改查操作 
### 1.5 RBAC 模型 
#### ①Role Based Authentication Control 
#### ②基于角色的权限控制 
### 1.6 分配资源 
#### ①给 Admin 分配 Role 
#### ②给 Role 分配 Auth 
### 1.7 权限控制
#### 使用 SpringSecurity 接管项目的登录、登录检查、权限验证 
※改源码：让 SpringSecurity 在初始化时不要查找 IOC 容器，而是在第一次请求时 查找； 查找的 IOC 容器也改成了查找 SpringMVC 的 IOC 容器（也就是由 DispatcherServlet 的父类 FrameworkServlet 初始化的 IOC 容器）。 
#####  登录验证：将登录表单对接到 SpringSecurity 
#####  登录检查：SpringSecurity 内置 
#####  全局配置：在 SpringSecurity 配置类中设定 
#####  权限规则注解：@PreAuthority 
#####  页面标签：对页面局部进行权限限定，实现细粒度权限控制
## 二、前台会员系统
### 2.1 用户登录、注册 
#### ①调用第三方接口给用户手机发送短信验证码 
#### ②使用 BCryptPasswordEncoder 实现带盐值的加密 
#### ③使用 SpringSession 解决分布式环境下 Session 不一致问题 
#### ④使用 Redis 作为 SpringSession 的 Session 库 
#### ⑤在 Zuul 中使用 ZuulFilter 实现登录状态检查 
#### ⑥在 Zuul 中配置访问各个具体微服务的路由规则 
### 2.2 发布项目 
#### 使用阿里云 OSS 对象存储服务保存用户上传的图片 
### 2.3 展示项目 
#### 将数据库中的项目数据查询出来到页面上显示 
### 2.4 支持项目 
#### ①确认回报信息 
#### ②生成订单
#### ③进入支付流程：调用支付宝开放平台提供支付接口