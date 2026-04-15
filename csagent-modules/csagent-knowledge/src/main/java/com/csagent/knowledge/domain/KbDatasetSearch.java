package com.csagent.knowledge.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/3/9 17:54
 * @description: 知识库检索vo
 */
@Data
public class KbDatasetSearch implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 知识库ids
     */
    private String datasetIds;

    /**
     * 检索方式（embedding-向量检索 text-全文检索 mix-混合检索）
     */
    private String searchType;

    /**
     * 相似度
     */
    private double similarity;

    /**
     * 召回条数
     */
    private Integer topRank;

    /**
     * 问题
     */
    private String question;

}
