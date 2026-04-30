package com.csagent.knowledge.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.csagent.common.mybatis.core.mapper.BaseMapperPlus;
import com.csagent.knowledge.domain.KbEmbedding;
import com.csagent.knowledge.domain.vo.SearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/10 14:05
 * @description: 向量Mapper
 */
public interface KbEmbeddingMapper  extends BaseMapperPlus<KbEmbedding, KbEmbedding> {

    /**
     * 根据文档ID批量删除
     */
    @DS("postgres")
    void deleteByDocumentIds(@Param("list") List<String> documentIds);

    /**
     * 向量搜索
     */
    @DS("postgres")
    List<SearchVo> embeddingSearch(@Param("vector") String vector,
                                   @Param("datasetIds") List<String> datasetIds,
                                   @Param("score") double score,
                                   @Param("limit") int limit);

    /**
     * 全文搜索
     */
    @DS("postgres")
    List<SearchVo> textSearch(@Param("tsQuery") String tsQuery,
                              @Param("datasetIds") List<String> datasetIds,
                              @Param("score") double score,
                              @Param("limit") int limit);

    /**
     * 混合搜索 (向量 + 全文)
     */
    @DS("postgres")
    List<SearchVo> mixSearch(@Param("vector") String vector,
                             @Param("tsQuery") String tsQuery,
                             @Param("datasetIds") List<String> datasetIds,
                             @Param("score") double score,
                             @Param("limit") int limit);

    /**
     * 批量更新知识库ID
     */
    @DS("postgres")
    void updateDatasetByIds(@Param("list") List<String> documentIds,
                            @Param("datasetId") String datasetId);

    /**
     * 根据段落Id删除
     *
     * @param paragraphId 段落Id
     */
    @DS("postgres")
    void deleteByParagraphId(Long paragraphId);

    /**
     * 根据文档Id删除
     *
     * @param documentId 文档Id
     */
    @DS("postgres")
    void deleteByDocumentId(Long documentId);

    /**
     * 插入向量
     *
     * @param kbEmbedding 向量
     * @return
     */
    @DS("postgres")
    int insert(KbEmbedding kbEmbedding);

    /**
     * 根据来源类型和来源Id删除
     *
     * @param sourceType 来源类型
     * @param sourceIds  来源Id数组
     */
    @DS("postgres")
    void deleteByTypeAndSourceId(@Param("sourceType") String sourceType, @Param("sourceIds") Long[] sourceIds);

    /**
     * 根据段落Id列表删除
     *
     * @param paragraphIds 段落Id列表
     */
    @DS("postgres")
    void deleteByParagraphIds(@Param("paragraphIds") Long[] paragraphIds);

    /**
     * 根据类型、来源Id、段落Id删除
     *
     * @param sourceType  来源类型
     * @param sourceId    来源Id
     * @param paragraphId 段落Id
     */
    @DS("postgres")
    void deleteByTypeAndSourceIdAndParagraphId(@Param("sourceType")String sourceType, @Param("sourceId")Long sourceId, @Param("paragraphId")Long paragraphId);
}
