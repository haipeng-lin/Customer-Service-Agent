package com.csagent.common.core.utils;

import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haipeng-lin
 * @date 2026/3/9 15:09
 * @description: Sse 工具类
 */
public class SseUtils {
    /**
     * 构建发送方法
     * @param runtimeId long
     * @param nodeId String
     * @param content String
     * @return String
     */
    public static String buildSendData(long runtimeId, String nodeId, String content) {

        Map<String, String> returnData = new HashMap<>();
        returnData.put("runtimeId", String.valueOf(runtimeId));
        returnData.put("content", content);
        returnData.put("nodeId", nodeId);

        return JSONUtil.toJsonStr(returnData);
    }
}
