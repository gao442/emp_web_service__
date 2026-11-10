# 员工管理系统（demoproject01）

基于 **Spring Boot 3 + MyBatis** 的前后端一体化员工管理系统，涵盖部门管理、员工管理、登录认证、操作日志、文件云存储等 Web 后端核心能力，是一个完整的 Java Web 学习实践项目。

## 技术栈

| 类别 | 技术 |
|---|---|
| 基础框架 | Spring Boot 3.0.2、Spring MVC |
| 持久层 | MyBatis 3.5、MySQL 8、PageHelper 分页 |
| 认证 | JWT（jjwt 0.9.1）+ Token 拦截器/过滤器 |
| 切面 | Spring AOP + 自定义注解（操作日志记录） |
| 文件存储 | 阿里云 OSS SDK |
| 其他 | Lombok、FastJSON、Jackson、spring-boot-configuration-processor |
| 前端 | 原生 HTML/CSS/JS（静态资源托管于 `static` 目录） |

## 功能模块

- **登录认证**：登录成功后签发 JWT（含 id、name 声明与过期时间），后续请求通过 Token 校验身份；`LoginCheckFilter` / `LoginCheckInterceptor` 双层拦截未授权访问
- **部门管理**：部门的增删改查与分页列表（`DeptController` → `DeptService` → `DeptMapper`）
- **员工管理**：员工的增删改查、分页查询、按条件查询，员工-部门关联展示
- **操作日志**：自定义 `@Log` 注解 + `LogAspect` 切面，无侵入记录操作方法、参数、返回值与操作类型，落库 `OperateLog`
- **文件上传**：图片上传至阿里云 OSS 并返回访问地址，配置项通过 `@ConfigurationProperties` 绑定（`AliOssProperties`）
- **全局异常处理**：`GlobalExceptionHandler` 统一捕获业务异常，返回规范 JSON 结果（`Result` 统一响应体）

## 项目结构

```
src/main/java/com/itheima
├── annotation        # 自定义注解（@Log）
├── aop / aoptest     # 切面（操作日志、方法耗时）
├── config            # Web 配置（拦截器注册等）
├── controller        # 控制层（部门/员工/登录/上传）
├── exception         # 全局异常处理
├── filter            # Servlet 过滤器（登录校验等）
├── Interceptor       # Spring MVC 拦截器
├── mapper            # MyBatis Mapper 接口
├── pojo              # 实体与通用返回对象（Result/PageBean）
├── service           # 业务层接口与实现
└── utils             # JWT 工具、OSS 工具与配置绑定类
```

SQL 映射文件位于 `resources/com/itheima/mapper/*.xml`，静态页面位于 `resources/static`（`login.html`、`manage.html`、`index.html`、`upload.html`）。

## 快速开始

1. 环境要求：JDK 17、Maven 3.x、MySQL 8
2. 创建数据库 `test01`，导入部门表与员工表
3. 修改 `application.yml` 中的数据源账号密码，以及阿里云 OSS 的 `endpoint` / `bucketName`
4. 运行主类 `com.itheima.demoproject01Application`
5. 浏览器访问 `http://localhost:8080/login.html`，登录后进入管理页

## 说明

- 配置文件优先级：`properties` > `yml` > `yaml`，外部配置与命令行参数优先级更高
- 测试类与启动类同包（`com.itheima`），保证 `@SpringBootTest` 能正确定位 `@SpringBootConfiguration`
