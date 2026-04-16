package com.csagent.knowledge.domain.vo;

import com.csagent.common.core.domain.vo.KbDocumentItemVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/19 16:28
 * @description: 文档切分Vo
 */
@Data
public class KbDocumentSplitVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件分段
     */
    private List<KbDocumentItemVo> content;

    /**
     * 文件标题
     */
    private String name;

    /**
     * 文本字符数
     */
    private long fileSize;
}
