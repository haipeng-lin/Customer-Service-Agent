package com.csagent.common.langchain4j.handler.file;

import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.vo.KbDocumentItemVo;
import com.csagent.common.core.utils.file.FileUtils;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:29
 * @description: CsAgent文档切分
 */
@Slf4j
public class CsAgentDocumentSplitter {

    /**
     * 执行文本拆分
     * @param parser DocumentParser
     * @param inputStream InputStream
     * @param kbDocumentPreview PreviewVo
     * @return List<DocumentItemVo>
     */
    public static List<KbDocumentItemVo> splitter(DocumentParser parser, InputStream inputStream, KbDocumentPreview kbDocumentPreview) {

        try {

            // 文本解析器
            Document document = parser.parse(inputStream);
            List<TextSegment> segments;
            // 如果是自定义拆分
            if (kbDocumentPreview.getSplitType().equals(1) && !kbDocumentPreview.getPattern().isBlank()) {

                TextSplitter splitter = new TextSplitter(kbDocumentPreview.getPattern(), kbDocumentPreview.getSplitLen(), 10);
                segments = splitter.split(document);
            } else {
                // 512个字符 10个重合度拆分文本
                DocumentSplitter splitter = new DocumentByParagraphSplitter(kbDocumentPreview.getSplitLen(), 10);
                segments = splitter.split(document);
            }

            List<KbDocumentItemVo> itemListVo = new LinkedList<>();
            segments.forEach(segment -> {
                KbDocumentItemVo itemVo = new KbDocumentItemVo();
                itemVo.setTitle("");

                // 自动清理
                String content = segment.text();
                if (kbDocumentPreview.getAutoClean().equals(1)) {
                    content = FileUtils.cleanText(content);
                }
                itemVo.setContent(content);

                itemListVo.add(itemVo);
            });

            return itemListVo;
        } catch (Exception e) {
            log.error("解析文本 {}, 出错: {}", kbDocumentPreview, e.getMessage());
            return new LinkedList<>();
        }
    }

}
