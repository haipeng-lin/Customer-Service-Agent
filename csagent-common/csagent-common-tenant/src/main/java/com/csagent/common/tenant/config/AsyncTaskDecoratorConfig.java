package com.csagent.common.tenant.config;

import com.csagent.common.core.context.UserContextHolder;
import com.csagent.common.satoken.utils.LoginHelper;
import com.csagent.common.tenant.helper.TenantHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;

/**
 * 异步任务装饰器配置
 * 用于将主线程的用户上下文（用户Id、租户Id）传递到异步线程
 *
 * @author haipeng-lin
 */
@Configuration
public class AsyncTaskDecoratorConfig {

    @Bean
    public TaskDecorator taskDecorator() {
        return new TaskDecorator() {
            @Override
            @NonNull
            public Runnable decorate(@NonNull Runnable runnable) {
                Long userId = LoginHelper.getUserId();
                String tenantId = LoginHelper.getTenantId();
                Long deptId = LoginHelper.getDeptId();
                return () -> {
                    try {
                        UserContextHolder.setUserId(userId);
                        UserContextHolder.setTenantId(tenantId);
                        UserContextHolder.setDeptId(deptId);
                        if (tenantId != null) {
                            TenantHelper.setDynamic(tenantId);
                        }
                        runnable.run();
                    } finally {
                        UserContextHolder.clear();
                        TenantHelper.clearDynamic();
                    }
                };
            }
        };
    }
}
