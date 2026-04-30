package com.csagent.knowledge.mapper;

import com.csagent.knowledge.domain.KbDataset;
import com.csagent.knowledge.domain.vo.KbDatasetVo;
import com.csagent.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 知识库Mapper接口
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
public interface KbDatasetMapper extends BaseMapperPlus<KbDataset, KbDatasetVo> {

    /**
     * 查询知识库列表（含文档数量和应用数量）
     */
    List<KbDatasetVo> selectDatasetVoWithCount();

}
