package com.csagent.model.service.impl;

import cn.hutool.core.convert.Convert;
import com.csagent.common.translation.annotation.TranslationType;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.common.translation.core.TranslationInterface;
import com.csagent.model.service.IMdModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 模型标题翻译实现
 *
 * @author haipeng-lin
 */
@Slf4j
@Service
@RequiredArgsConstructor
@TranslationType(type = TransConstant.MODEL_ID_TO_NAME)
public class ModelNameTranslationImpl implements TranslationInterface<String> {

    private final IMdModelService mdModelService;

    @Override
    public String translation(Object key, String other) {
        if (key == null) {
            return null;
        }
        Long modelId = Convert.toLong(key);
        if (modelId == null) {
            return null;
        }
        return mdModelService.queryById(modelId).getName();
    }
}
