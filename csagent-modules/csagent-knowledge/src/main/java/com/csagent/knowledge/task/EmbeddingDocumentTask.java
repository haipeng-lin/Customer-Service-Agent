//package com.csagent.knowledge.task;
//
//import cn.hutool.core.util.IdUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.csagent.common.core.utils.DateUtils;
//import com.csagent.knowledge.domain.*;
//import com.csagent.knowledge.mapper.*;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.math3.geometry.partitioning.Embedding;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.w3c.dom.DocumentType;
//
//import javax.lang.model.util.Elements;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * @author haipeng-lin
// * @date 2026/4/14 11:27
// * @description: 向量文档任务
// */
//@Slf4j
//@Service
//public class EmbeddingDocumentTask {
//
//    @Autowired
//    private KbParagraphMapper kbParagraphMapper;
//
//    @Autowired
//    private KbDocumentMapper kbDocumentMapper;
//
//    @Autowired
//    private KbEmbeddingMapper kbEmbeddingMapper;
//
//    @Autowired
//    private KbQuestionParagraphMapper kbQuestionParagraphMapper;
//
//    @Autowired
//    private KbQuestionMapper kbQuestionMapper;
//
//    @Autowired
//    private ChatModelBuildHelper chatModelBuildHelper;
//
//    @Autowired
//    private EmbeddingModelBuildHelper embeddingModelBuildHelper;
//
//    @Autowired
//    private KbDatasetMapper kbDatasetMapper;
//
//    @Autowired
//    private ApplicationHelper applicationHelper;
//
//    private EmbeddingModel embeddingModel;
//
//    /**
//     * 向量化文本
//     *
//     * @param documentId 文档Id
//     * @param kbDataset  知识库
//     */
//    @Async
//    public void executeAsyncTask(Long documentId, KbDataset kbDataset) {
//
//        try {
//            // 根据文档Id查询段落列表
//            List<KbParagraph> paragraphEntityList = kbParagraphMapper.selectList(Wrappers.lambdaQuery().eq(documentId != null, KbParagraph::getDocumentId, documentId));
//            if (!CollectionUtils.isEmpty(paragraphEntityList)) {
//                // 删除已经向量化的数据
//                kbEmbeddingMapper.deleteByDocumentId(String.valueOf(documentId));
//
//                // 选择embedding模型
//                embeddingModel = embeddingModelBuildHelper.build(kbDataset);
//
//                for (KbParagraph paragraph : paragraphEntityList) {
//                    this.embeddingSingleParagraph(paragraph);
//                }
//
//                // 标记向量化完成
//                KbDocument document = kbDocumentMapper.selectKbDocumentById(documentId);
//                document.setEmbeddingStatus(EmbeddingStatus.COMPLETE.getCode());
//                document.setEmbeddingTime(DateUtils.getNowDate());
//                document.setUpdateTime(DateUtils.getNowDate());
//                kbDocumentMapper.updateKbDocument(document);
//            }
//        } catch (Exception e) {
//            log.error("向量化段落: {}, 报错: {}", kbDataset, e.getMessage());
//            // 还原状态，方便前端状态显示
//            KbDocument finalUpdateEntity = kbDocumentMapper.selectKbDocumentById(documentId);
//            finalUpdateEntity.setStatus(EmbeddingStatus.COMPLETE.getCode());
//            kbDocumentMapper.updateKbDocument(finalUpdateEntity);
//        }
//    }
//
//    /**
//     * 向量化单个段落
//     *
//     * @param paragraphId String
//     */
//    @Async
//    public void executeAsyncParagraphTask(Long paragraphId) {
//        KbParagraph kbParagraph = kbParagraphMapper.selectById(paragraphId);
//        // 删除已经向量化的数据
//        kbEmbeddingMapper.deleteByParagraphId(paragraphId);
//        // 选择embedding模型
//        KbDataset kbDataset = kbDatasetMapper.selectKbDatasetById(kbParagraph.getDatasetId());
//        embeddingModel = embeddingModelBuildHelper.build(kbDataset);
//
//        this.embeddingSingleParagraph(kbParagraph);
//    }
//
////    /**
////     * 异步执行段落生成问题
////     *
////     * @param mdModel     模型
////     * @param documentIds 文档Id列表
////     * @param appQuestion 问题Dto
////     */
////    @Async
////    public void executeAsyncQuestionTask(MdModel mdModel, List<Long> documentIds, AppQuestion appQuestion) {
////
////        AppApplication appApplication = new AppApplication();
////        appApplication.setTemperature(0.95);
////        appApplication.setModelName(mdModel.getModels().split(";")[0]);
////        ChatModel chatModel = chatModelBuildHelper.build(mdModel, appApplication);
////
////        for (Long documentId : documentIds) {
////            try {
////                KbDocument documentInfo = kbDocumentMapper.selectKbDocumentById(documentId);
////                documentInfo.setQuestionStatus(QuestionStatus.RUNNING.getCode());
////                kbDocumentMapper.updateKbDocument(documentInfo);
////
////                // 查出分段内容
////                List<KbParagraph> paragraphList = kbParagraphMapper.selectListByDocumentIdAndStatus(documentId, UniversalStatus.ENABLE.getCode());
////                for (KbParagraph paragraph : paragraphList) {
////
////                    String question = appQuestion.getPrompt().replace("{data}", paragraph.getContent());
////                    ChatResponse chatResponse = chatModel.chat(UserMessage.from(question));
////
////                    // 记录token使用情况
////                    applicationHelper.writeTokenLog(mdModel.getId(), TokenConsumeSource.QUESTION.getCode(), chatResponse.tokenUsage());
////
////                    Document doc = Jsoup.parse(chatResponse.aiMessage().text());
////                    Elements questions = doc.select("question");
////
////                    for (Element questionMatch : questions) {
////
////                        // 写入问题
////                        KbQuestion kbQuestion = new KbQuestion();
////                        kbQuestion.setContent(questionMatch.text());
////                        kbQuestion.setHitNum(0);
////                        kbQuestion.setDatasetId(paragraph.getDatasetId());
////                        kbQuestion.setCreateTime(DateUtils.getNowDate());
////                        kbQuestionMapper.insertKbQuestion(kbQuestion);
////
////                        // 写入问题关联
////                        KbQuestionParagraph kbQuestionParagraph = new KbQuestionParagraph();
////                        kbQuestionParagraph.setDatasetId(paragraph.getDatasetId());
////                        kbQuestionParagraph.setDocumentId(paragraph.getDocumentId());
////                        kbQuestionParagraph.setParagraphId(paragraph.getId());
////                        kbQuestionParagraph.setQuestionId(kbQuestion.getId());
////                        kbQuestionParagraph.setCreateBy(SecurityUtils.getUsername());
////                        kbQuestionParagraph.setCreateTime(DateUtils.getNowDate());
////                        kbQuestionParagraphMapper.insertKbQuestionParagraph(kbQuestionParagraph);
////                    }
////                }
////                documentInfo.setQuestionTime(DateUtils.getNowDate());
////                documentInfo.setQuestionStatus(QuestionStatus.COMPLETE.getCode()); // 已生成
////                kbDocumentMapper.updateKbDocument(documentInfo);
////            } catch (Exception e) {
////                log.error("生成问题: {}, 失败: {}", documentId, e.getMessage());
////                // 还原状态，方便前端显示
////                KbDocument documentInfo = kbDocumentMapper.selectKbDocumentById(documentId);
////                documentInfo.setQuestionStatus(QuestionStatus.PENDING.getCode()); // 待生成
////                kbDocumentMapper.updateKbDocument(documentInfo);
////            }
////        }
////    }
////
////    /**
////     * 处理数据向量化
////     *
////     * @param paragraph KbParagraph
////     */
////    private void embeddingSingleParagraph(KbParagraph paragraph) {
////        log.info("处理数据向量化: {}", paragraph.getContent());
////
////        // 拆分段落长度，防止截取256的长度，去进行向量化，有一些embedding模型要求的最大上下文是256
////        String paragraphStr = paragraph.getTitle() + paragraph.getContent();
////        List<String> subParagraph = new LinkedList<>();
////        if (paragraphStr.length() > 256) {
////            subParagraph = TextChunkUtils.handle(paragraphStr);
////        } else {
////            subParagraph.add(paragraphStr);
////        }
////
////        KbDataset kbDataset = kbDatasetMapper.selectKbDatasetById(paragraph.getDatasetId());
////
////        for (String content : subParagraph) {
////
////            // 开始向量化，并入库
////            KbEmbedding kbEmbedding = new KbEmbedding();
////            kbEmbedding.setEmbeddingId(IdUtil.randomUUID());
////            kbEmbedding.setDatasetId(paragraph.getDatasetId().toString());
////            kbEmbedding.setDocumentId(paragraph.getDocumentId().toString());
////            kbEmbedding.setParagraphId(paragraph.getId().toString());
////            kbEmbedding.setStatus(Integer.valueOf(UniversalStatus.ENABLE.getCode()));
////            kbEmbedding.setCreateTime(DateUtils.getNowLocalDateTime());
////
////            Response<Embedding> response = embeddingModel.embed(content);
////            List<Float> vector = response.content().vectorAsList();
////            kbEmbedding.setEmbedding(vector);
////            // 记录token消耗记录
////            applicationHelper.writeEmbeddingTokenLog(kbDataset, response);
////
////            kbEmbedding.setSearchVector(TsVectorGeneratorUtils.toTsVector(content)); // 全文检索文本
//////             todo: kbEmbedding.setSearchVector(StatusEnum.YES.getCode());
////            kbEmbedding.setSourceType(DocumentType.DOCUMENT.getCode()); // 来源文本
////            kbEmbedding.setSourceId(String.valueOf(paragraph.getId())); // 来源id
//////            kbEmbedding.setCreateTime(DateUtils.getNowDate());
////
////            kbEmbeddingMapper.insert(kbEmbedding);
////        }
////
////        // 段落关联的问题，需要重新索引
////        List<KbQuestionParagraph> relationList = kbQuestionParagraphMapper.selectListByParagraphId(paragraph.getId());
////
////        // 查出段落问题信息
////        List<Long> questionIds = relationList.stream().map(KbQuestionParagraph::getQuestionId).toList();
////        if (!questionIds.isEmpty()) {
////
////            List<KbQuestion> questionList = kbQuestionMapper.selectByIds(questionIds);
////            HashMap<Long, KbQuestion> questionId2Info = new HashMap<>();
////            for (KbQuestion question : questionList) {
////                questionId2Info.put(question.getId(), question);
////            }
////
////            for (KbQuestionParagraph relation : relationList) {
////
////                // 开始向量化，并入库
////                KbEmbedding kbEmbedding = new KbEmbedding();
////                kbEmbedding.setEmbeddingId(IdUtil.randomUUID());
////                kbEmbedding.setDatasetId(String.valueOf(relation.getDatasetId()));
////                kbEmbedding.setDocumentId(String.valueOf(relation.getDocumentId()));
////                kbEmbedding.setParagraphId(String.valueOf(relation.getParagraphId()));
////
////                String content = questionId2Info.get(relation.getQuestionId()).getContent();
////                Response<Embedding> response = embeddingModel.embed(content);
////                List<Float> vector = response.content().vectorAsList();
////                kbEmbedding.setEmbedding(vector); // 向量化文本
////                // 记录token消耗记录
////                applicationHelper.writeEmbeddingTokenLog(kbDataset, response);
////
////                kbEmbedding.setSearchVector(TsVectorGeneratorUtils.toTsVector(content)); // 全文检索文本
////                kbEmbedding.setStatus(Integer.valueOf(UniversalStatus.ENABLE.getCode()));
////                kbEmbedding.setSourceType(1); // 来源问题
////                kbEmbedding.setSourceId(String.valueOf(relation.getQuestionId())); // 来源id
////                kbEmbedding.setCreateTime(DateUtils.getNowLocalDateTime());
////                kbEmbeddingMapper.insert(kbEmbedding);
////            }
////        }
////    }
//}
