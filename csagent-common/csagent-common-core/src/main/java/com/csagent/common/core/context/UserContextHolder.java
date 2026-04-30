package com.csagent.common.core.context;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户上下文持有器
 * 用于在异步线程中传递用户Id、租户Id和部门Id
 *
 * @author haipeng-lin
 */
@Slf4j
public class UserContextHolder {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<Long> DEPT_ID = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void setTenantId(String tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static String getTenantId() {
        return TENANT_ID.get();
    }

    public static void setDeptId(Long deptId) {
        DEPT_ID.set(deptId);
    }

    public static Long getDeptId() {
        return DEPT_ID.get();
    }

    public static void clear() {
        USER_ID.remove();
        TENANT_ID.remove();
        DEPT_ID.remove();
    }
}