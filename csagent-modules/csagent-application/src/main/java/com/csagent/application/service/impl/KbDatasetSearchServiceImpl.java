package com.csagent.application.service.impl;

import cn.hutool.json.JSONUtil;
import com.csagent.application.helper.ApplicationHelper;
import com.csagent.application.helper.EmbeddingModelBuildHelper;
import com.csagent.application.service.IKbDatasetSearchService;
import com.csagent.common.core.exception.ServiceException;
import com.csagent.common.core.utils.vector.TsVectorGeneratorUtils;
import com.csagent.knowledge.domain.KbDataset;
import com.csagent.knowledge.domain.KbDatasetSearch;
import com.csagent.knowledge.domain.KbDocument;
import com.csagent.knowledge.domain.KbParagraph;
import com.csagent.knowledge.domain.vo.SearchVo;
import com.csagent.knowledge.mapper.KbDatasetMapper;
import com.csagent.knowledge.mapper.KbDocumentMapper;
import com.csagent.knowledge.mapper.KbEmbeddingMapper;
import com.csagent.knowledge.mapper.KbParagraphMapper;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:58
 * @description: 知识库检索service实现
 */
@Service
public class KbDatasetSearchServiceImpl implements IKbDatasetSearchService {

    @Autowired
    private KbDocumentMapper kbDocumentMapper;

    @Autowired
    private KbParagraphMapper kbParagraphMapper;

    @Autowired
    private KbDatasetMapper kbDatasetMapper;

    @Autowired
    private EmbeddingModelBuildHelper embeddingModelBuildHelper;

    @Autowired
    private KbEmbeddingMapper kbEmbeddingMapper;

    @Autowired
    private ApplicationHelper applicationHelper;

    /**
     * 命中测试
     *
     * @param kbDatasetSearch 知识库检索Dto
     */
    @Override
    public List<SearchVo> search(KbDatasetSearch kbDatasetSearch) {

        if (kbDatasetSearch.getQuestion().isBlank()) {
            throw new ServiceException("输入的问题不能为空");
        }

        if (kbDatasetSearch.getSimilarity() < 0) {
            throw new ServiceException("设信度应该大于0");
        }

        if (kbDatasetSearch.getTopRank() < 1) {
            throw new ServiceException("召回数应该大于1");
        }

        List<SearchVo> searchRes = new LinkedList<>();

        // 取对应的embedding模型
        String datasetId = kbDatasetSearch.getDatasetIds().split(",")[0];
        KbDataset kbDataset = kbDatasetMapper.selectById(Long.valueOf(datasetId));
        EmbeddingModel embeddingModel = embeddingModelBuildHelper.build(kbDataset);

        // 文本检索
        if (kbDatasetSearch.getSearchType().equals("text")) {
            searchRes = textSearch(kbDatasetSearch);
        } else {
            Response<Embedding> response = embeddingModel.embed(kbDatasetSearch.getQuestion());
            List<Float> vector = response.content().vectorAsList();
//            applicationHelper.writeEmbeddingTokenLog(kbDataset, response);
            if (kbDatasetSearch.getSearchType().equals("embedding")) {
                // 向量检索
                searchRes = embeddingSearch(kbDatasetSearch, vector);
            } else if (kbDatasetSearch.getSearchType().equals("mix")) {
                // 混合检索
                searchRes = mixSearch(kbDatasetSearch, vector);
            }
        }
        return searchRes;
    }

    /**
     * 向量检索
     *
     * @param datasetSearchVo DatasetSearchVo
     * @param vector          List<Float>
     * @return List<KbDatasetSearchVo>
     */
    private List<SearchVo> embeddingSearch(KbDatasetSearch datasetSearchVo, List<Float> vector) {

        List<String> datasetIds = Arrays.stream(datasetSearchVo.getDatasetIds().split(",")).toList();
        List<SearchVo> searchRes = kbEmbeddingMapper.embeddingSearch(JSONUtil.toJsonStr(vector), datasetIds,
            datasetSearchVo.getSimilarity(), datasetSearchVo.getTopRank());

        if (!CollectionUtils.isEmpty(searchRes)) {
            return buildFinalRes(searchRes);
        }

        return searchRes;
    }

    /**
     * 全文检索
     *
     * @param datasetSearchVo DatasetSearchVo
     * @return List<KbDatasetSearchVo>
     */
    private List<SearchVo> textSearch(KbDatasetSearch datasetSearchVo) {

        String searchKeywords = TsVectorGeneratorUtils.toTsQuery(datasetSearchVo.getQuestion());
        List<String> datasetIds = Arrays.stream(datasetSearchVo.getDatasetIds().split(",")).toList();
        List<SearchVo> searchRes = kbEmbeddingMapper.textSearch(searchKeywords, datasetIds, datasetSearchVo.getSimilarity(),
            datasetSearchVo.getTopRank());

        if (!CollectionUtils.isEmpty(searchRes)) {
            return buildFinalRes(searchRes);
        }

        return searchRes;
    }

    /**
     * 混合检索
     *
     * @param datasetSearchVo DatasetSearchVo
     * @param vector          List<Float>
     * @return List<KbDatasetSearchVo>
     */
    private List<SearchVo> mixSearch(KbDatasetSearch datasetSearchVo, List<Float> vector) {

        List<String> datasetIds = Arrays.stream(datasetSearchVo.getDatasetIds().split(",")).toList();
        String searchKeywords = TsVectorGeneratorUtils.toTsQuery(datasetSearchVo.getQuestion());
        List<SearchVo> searchRes = kbEmbeddingMapper.mixSearch(JSONUtil.toJsonStr(vector), searchKeywords, datasetIds,
            datasetSearchVo.getSimilarity(), datasetSearchVo.getTopRank());

        if (!CollectionUtils.isEmpty(searchRes)) {
            return buildFinalRes(searchRes);
        }

        return searchRes;
    }

    /**
     * 构建最终的信息
     *
     * @param searchRes List<SearchVo>
     * @return List<SearchVo>
     */
    private List<SearchVo> buildFinalRes(List<SearchVo> searchRes) {
        // 所有的文档
        List<Long> documentIds = searchRes.stream().map(SearchVo::getDocumentId).toList();
        // 所有的段落
        List<Long> paragraphIds = searchRes.stream().map(SearchVo::getParagraphId).toList();

        List<KbDocument> documentList = kbDocumentMapper.selectByIds(documentIds);

        // 文档Id：集合
        Map<Long, String> documentId2Name = new HashMap<>();
        for (KbDocument knowledgeDocumentEntity : documentList) {
            documentId2Name.put(knowledgeDocumentEntity.getId(), knowledgeDocumentEntity.getTitle());
        }

        List<KbParagraph> kbParagraphList = kbParagraphMapper.selectByIds(paragraphIds);
        Map<Long, KbParagraph> paragraphId2Info = new HashMap<>();
        for (KbParagraph kbParagraph : kbParagraphList) {
            paragraphId2Info.put(kbParagraph.getId(), kbParagraph);
        }

        for (SearchVo searchVo : searchRes) {
            // 文档标题
            searchVo.setDocumentName(documentId2Name.get(searchVo.getDocumentId()));
            // 段落内容
            searchVo.setTitle(paragraphId2Info.get(searchVo.getParagraphId()).getTitle());
            searchVo.setContent(paragraphId2Info.get(searchVo.getParagraphId()).getContent());
        }

        return searchRes;
    }
}
