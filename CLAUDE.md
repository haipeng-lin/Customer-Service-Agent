# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在本仓库中工作时提供指导。

## 项目简介

RuoYi-Vue-Plus 是基于 Spring Boot 3.5 + Java 17/21 重写的多租户分布式管理系统，支持分布式集群和多租户场景。

**技术栈**: Spring Boot 3.5, MyBatis-Plus, Sa-Token (认证授权), Redisson (Redis客户端), Dynamic-datasource (多数据源), Undertow (Web容器), Warm-Flow (工作流), SnailJob (分布式任务调度)

## 构建命令

```bash
# 清理并构建（开发环境，默认）
mvn clean package -Pdev

# 生产环境构建
mvn clean package -Pprod

# 跳过测试构建（默认）
mvn clean package -Pdev -DskipTests

# 运行测试（通过 profile 标签执行对应的 @Tag 测试方法）
mvn test -Pdev
```

**Maven Profile**: `local`、`dev`（默认）、`prod` - 会激活对应的配置文件和测试标签。

## 运行项目

**主类**: `ruoyi-admin` 模块下的 `org.dromara.DromaraApplication`

**默认端口**: 8080

**启动脚本**:
- Linux/Mac: `script/bin/ry.sh {start|stop|restart|status}`
- Windows: `script/bin/ry.bat`

**IDE**: IntelliJ IDEA 运行配置在 `.run/` 目录下。

## 架构

### 模块结构

```
ruoyi-vue-plus/
├── ruoyi-admin/          # Web 入口，包含主启动类
├── ruoyi-common/         # 通用共享库（25+ 个模块）
│   ├── ruoyi-common-core/          # 核心工具类
│   ├── ruoyi-common-mybatis/       # MyBatis-Plus 扩展（分页、多租户、数据权限）
│   ├── ruoyi-common-security/      # 安全相关工具
│   ├── ruoyi-common-satoken/       # Sa-Token 认证集成
│   ├── ruoyi-common-redis/         # Redis 缓存扩展
│   ├── ruoyi-common-tenant/        # 多租户支持
│   ├── ruoyi-common-translation/   # 数据翻译（注解 + Jackson 序列化期间动态修改）
│   ├── ruoyi-common-sensitive/     # 数据脱敏
│   ├── ruoyi-common-excel/         # FastExcel 集成
│   └── ... (20+ 个其他通用模块)
├── ruoyi-modules/        # 业务模块
│   ├── ruoyi-system/     # 系统管理（用户、角色、部门、菜单等）
│   ├── ruoyi-job/        # 定时任务集成
│   ├── ruoyi-generator/  # 代码生成器
│   ├── ruoyi-demo/       # 示例代码
│   └── ruoyi-workflow/   # Warm-Flow 工作流引擎
└── ruoyi-extend/         # 扩展服务
    ├── ruoyi-monitor-admin/   # Spring Boot Admin 监控
    └── ruoyi-snailjob-server/ # SnailJob 调度服务器
```

### 关键设计

1. **插件化通用模块**: 每个 `ruoyi-common-*` 模块都是一个专注的插件（如 redis、security、mybatis 等），可独立理解。

2. **业务模块分层规范**（各 `ruoyi-modules/*/` 下）:
   ```
   controller/   # REST 接口层
   service/      # 业务逻辑（通常为接口 + 实现）
   mapper/       # MyBatis-Plus Mapper（接口）
   domain/       # 实体对象
   ```

3. **多租户**: 通过 MyBatis-Plus 插件在 Mapper 层处理，配置在 `application.yml` 的 `tenant:` 节点下。

4. **数据权限**: MyBatis-Plus 插件自动分析和注入 SQL 条件，基于 `@DataPermission` 注解实现。

## 配置

配置文件位于 `ruoyi-admin/src/main/resources/`:
- `application.yml` - 基础配置，包含 Spring profiles 定义
- `application-dev.yml` - 开发环境配置（数据源、Redis 等）
- `application-prod.yml` - 生产环境配置

Profile 通过 `spring.profiles.active` 激活，由 Maven profile 驱动（`-Pdev` 会设置 `profiles.active=dev`）。

## 代码规范

- **Java**: 遵守阿里巴巴代码规范，统一格式化
- **缩进**: Java 代码 4 空格（YAML/JSON 使用 2 空格）
- **换行**: LF
- **Lombok**: 大量使用，简化代码
- **MapStruct-Plus**: 用于对象映射（需配合 Lombok binding）
- **Javadoc**: 通过 `therapi-runtime-javadoc` 注解处理，需按规范编写注释

## 注意事项

- 数据库初始化 SQL 位于 `script/sql/`（包含 MySQL、PostgreSQL、Oracle、SQLServer 等版本）
- `application-dev.yml` 中的 Redis 密码和数据库账号密码为示例值，需修改
- `application.yml` 中的接口加解密密钥需在生产环境替换
- 工作流模块启用后，UI 访问路径为 `/warm-flow-ui/`
