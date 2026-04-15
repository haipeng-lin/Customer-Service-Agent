package com.csagent.common.langchain4j.handler.impl;

import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.vo.KbDocumentItemVo;
import com.csagent.common.langchain4j.handler.CsAgentDocumentSplitter;
import com.csagent.common.langchain4j.handler.FileHandlerInterface;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:37
 * @description: Pdf文件切分实现
 */
public class PdfFileHandler implements FileHandlerInterface {

    @Override
    public List<KbDocumentItemVo> handle(byte[] bytes, KbDocumentPreview kbDocumentPreview) {

        InputStream inputStream = new ByteArrayInputStream(bytes);
        DocumentParser parser = new ApachePdfBoxDocumentParser();

        return CsAgentDocumentSplitter.splitter(parser, inputStream, kbDocumentPreview);
    }
}
