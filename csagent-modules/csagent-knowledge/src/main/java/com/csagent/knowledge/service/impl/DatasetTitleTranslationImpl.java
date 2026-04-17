package com.csagent.knowledge.service.impl;

import cn.hutool.core.convert.Convert;
import com.csagent.common.translation.annotation.TranslationType;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.common.translation.core.TranslationInterface;
import com.csagent.knowledge.service.IKbDatasetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 知识库名称翻译实现
 *
 * @author haipeng-lin
 */
@Slf4j
@Service
@RequiredArgsConstructor
@TranslationType(type = TransConstant.DATASET_ID_TO_TITLE)
public class DatasetTitleTranslationImpl implements TranslationInterface<String> {

    private final IKbDatasetService kbDatasetService;

    @Override
    public String translation(Object key, String other) {
        if (key == null) {
            return null;
        }
        Long datasetId = Convert.toLong(key);
        if (datasetId == null) {
            return null;
        }
        return kbDatasetService.queryById(datasetId).getTitle();
    }
}
