package com.csagent.common.langchain4j.handler;

import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.vo.KbDocumentItemVo;

import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:16
 * @description: 文件操作接口
 */
public interface FileHandlerInterface {
    List<KbDocumentItemVo> handle(byte[] bytes, KbDocumentPreview kbDocumentPreview);

}
