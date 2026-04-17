package com.csagent.knowledge.service.impl;

import cn.hutool.core.convert.Convert;
import com.csagent.common.translation.annotation.TranslationType;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.common.translation.core.TranslationInterface;
import com.csagent.knowledge.service.IKbDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文档标题翻译实现
 *
 * @author haipeng-lin
 */
@Slf4j
@Service
@RequiredArgsConstructor
@TranslationType(type = TransConstant.DOCUMENT_ID_TO_TITLE)
public class DocumentTitleTranslationImpl implements TranslationInterface<String> {

    private final IKbDocumentService kbDocumentService;

    @Override
    public String translation(Object key, String other) {
        if (key == null) {
            return null;
        }
        Long documentId = Convert.toLong(key);
        if (documentId == null) {
            return null;
        }
        return kbDocumentService.queryById(documentId).getTitle();
    }
}