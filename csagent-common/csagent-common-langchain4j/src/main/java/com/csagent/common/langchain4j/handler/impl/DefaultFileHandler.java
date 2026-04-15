package com.csagent.common.langchain4j.handler.impl;

import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.vo.KbDocumentItemVo;
import com.csagent.common.langchain4j.handler.CsAgentDocumentSplitter;
import com.csagent.common.langchain4j.handler.FileHandlerInterface;
import dev.langchain4j.data.document.parser.TextDocumentParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:33
 * @description: 默认文件切分器
 */
public class DefaultFileHandler implements FileHandlerInterface {

    @Override
    public List<KbDocumentItemVo> handle(byte[] bytes, KbDocumentPreview kbDocumentPreview) {

        InputStream inputStream = new ByteArrayInputStream(bytes);
        TextDocumentParser parser = new TextDocumentParser();

        return CsAgentDocumentSplitter.splitter(parser, inputStream, kbDocumentPreview);
    }
}
