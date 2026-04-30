package com.csagent.web.controller.front;


import com.csagent.application.service.IAgentService;
import com.csagent.application.service.IKbDatasetSearchService;
import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.R;
import com.csagent.knowledge.domain.*;
import com.csagent.knowledge.domain.vo.KbDocumentSplitVo;
import com.csagent.knowledge.domain.vo.SearchVo;
import com.csagent.knowledge.service.IKbQuestionParagraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/13 14:34
 * @description: 应用AiController
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/application")
public class AgentController {

    @Autowired
    private IAgentService agentService;

    @Autowired
    private IKbDatasetSearchService kbDatasetSearchService;

    @Autowired
    private IKbQuestionParagraphService kbQuestionParagraphService;

    /**
     * 向量化知识库
     */
    @PostMapping("/embeddingDatasetId")
    public R<Void> embeddingDatasetId(@RequestParam("datasetId") Long datasetId) {
        agentService.embeddingDataset(datasetId);
        return R.ok();
    }

    /**
     * 向量化文档
     */
    @PostMapping("/embeddingDocument")
    public R<Void> embeddingDocument(@RequestParam("documentIds") String documentIds) {
        agentService.embeddingDocument(documentIds);
        return R.ok();
    }

    /**
     * 预览文档
     */
    @PostMapping("/previewDocument")
    public R<List<KbDocumentSplitVo>> previewDocument(KbDocumentPreview kbDocumentPreview) {
        return R.ok(agentService.previewFile(kbDocumentPreview));
    }

    /**
     * 保存文档
     */
    @PostMapping("/saveDocument")
    public R<Void> saveDocument(@RequestBody KbDocumentSave kbDocumentSave) {
        agentService.saveDocument(kbDocumentSave);
        return R.ok();
    }

    /**
     * 命中测试
     */
    @PostMapping("/hitTest")
    public R<List<SearchVo>> hitTest(@RequestBody KbDatasetSearch kbDatasetSearch) {
        return R.ok(kbDatasetSearchService.search(kbDatasetSearch));
    }

    /**
     * 生成问题
     */
    @PostMapping("/generateQuestion")
    public R<Void> generateQuestion(@RequestBody KbQuestionGeneration questionGeneration) {
        agentService.generateQuestion(questionGeneration);
        return R.ok();
    }


    /**
     * 获取问题关联的知识库、文档、段落
     *
     * @param questionIds 问题Id字符串
     * @param datasetId   知识库Id
     * @return 问题关联信息列表
     */
    @GetMapping("/getQuestionRelation")
    public R<List<KbQuestionParagraph>> getQuestionRelation(@RequestParam("questionIds") String questionIds,
                                                            @RequestParam("datasetId") Long datasetId) {
        List<Long> questionIdList = Arrays.stream(questionIds.split(","))
                .map(Long::parseLong)
                .toList();
        List<KbQuestionParagraph> voList = agentService.selectByQuestionIdsAndDatasetId(questionIdList, datasetId);
        return R.ok(voList);
    }

    /**
     * 操作问题关联
     */
    @PostMapping("/doQuestionRelation")
    public R<Void> doQuestionRelation(@RequestBody KbQuestionRelationOperate kbQuestionRelationOperate) {
        agentService.doQuestionRelation(kbQuestionRelationOperate);
        return R.ok();
    }

}
