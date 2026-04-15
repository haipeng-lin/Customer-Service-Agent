package com.csagent.common.core.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:18
 * @description: 文档项Vo
 */
@Data
public class KbDocumentItemVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 段落标题
     */
    private String title;

    /**
     * 段落内容
     */
    private String content;
}
