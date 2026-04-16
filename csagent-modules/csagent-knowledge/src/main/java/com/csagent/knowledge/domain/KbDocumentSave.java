package com.csagent.knowledge.domain;

import com.csagent.knowledge.domain.vo.KbDocumentSplitVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/20 17:14
 * @description: 文档保存
 */
@Data
public class KbDocumentSave implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文档分段列表
     */
    private List<KbDocumentSplitVo> documentList;

    /**
     * 知识库datasetId
     */
    private Long datasetId;

}
