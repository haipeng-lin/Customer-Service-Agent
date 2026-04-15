package com.csagent.application.service;

import com.csagent.knowledge.domain.KbDatasetSearch;
import com.csagent.knowledge.domain.vo.SearchVo;

import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:57
 * @description: 知识库检索Service
 */
public interface IKbDatasetSearchService {
    /**
     * 检索
     *
     * @param kbDatasetSearch 知识库检索
     * @return 结果
     */
    List<SearchVo> search(KbDatasetSearch kbDatasetSearch);
}
