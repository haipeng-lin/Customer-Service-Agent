package com.csagent.common.core.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:19
 * @description: 文档预览
 */
@Data
public class KbDocumentPreview implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 上传的文件
     */
    private MultipartFile[] files;

    /**
     * 拆分规则
     */
    private String pattern;

    /**
     * 切割长度
     */
    private Integer splitLen;

    /**
     * 导入时把标题关联成问题
     */
    private Boolean addTitle;

    /**
     * 查分类型（0-默认 1-自定义）
     */
    private Integer splitType;

    /**
     * 自动清洗
     */
    private Integer autoClean;

    /**
     * 文档模式 text:文本文件 excel:Excel表格 qa:QA问答
     */
    private String fileType;

    /**
     * 所属知识库id
     */
    private String datasetId;
}
