package com.csagent.common.langchain4j.handler.impl;

import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.vo.KbDocumentItemVo;
import com.csagent.common.langchain4j.handler.FileHandlerInterface;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:34
 * @description: MD文件切分实现
 */
public class MarkdownFileHandler implements FileHandlerInterface {

    @Override
    public List<KbDocumentItemVo> handle(byte[] bytes, KbDocumentPreview kbDocumentPreview) {

        List<KbDocumentItemVo> itemListVo = new LinkedList<>();
        List<MarkdownFileHandler.Section> markDownList = MarkdownFileHandler.parseMarkdown(new String(bytes));
        markDownList.forEach(item -> {
            KbDocumentItemVo itemVo = new KbDocumentItemVo();
            itemVo.setTitle(item.getTitle());
            itemVo.setContent(item.getContent());

            itemListVo.add(itemVo);
        });

        return itemListVo;
    }

    @Data
    public static class Section {
        private String title;
        private String content;
    }

    public static List<Section> parseMarkdown(String markdown) {
        Parser parser = Parser.builder().build();
        Document document = (Document) parser.parse(markdown);
        List<Section> sections = new ArrayList<>();
        Node currentNode = document.getFirstChild();
        StringBuilder currentContent = new StringBuilder();
        Section currentSection = null;

        while (currentNode != null) {
            if (currentNode instanceof Heading) {
                // 遇到新标题时保存前一个章节
                if (currentSection != null) {
                    currentSection.setContent(currentContent.toString().trim());
                    sections.add(currentSection);
                }
                // 创建新章节
                currentSection = new Section();
                currentSection.setTitle(((Heading) currentNode).getText().toString());
                currentContent = new StringBuilder();
            } else if (currentSection != null) {
                // 收集非标题内容
                currentContent.append(BasedSequence.of(currentNode.getChars())).append("\n");
            }
            currentNode = currentNode.getNext();
        }
        // 处理最后一个章节
        if (currentSection != null) {
            currentSection.setContent(currentContent.toString().trim());
            sections.add(currentSection);
        }
        return sections;
    }
}
