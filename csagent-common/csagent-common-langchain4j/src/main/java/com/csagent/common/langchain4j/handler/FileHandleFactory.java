package com.csagent.common.langchain4j.handler;

import com.csagent.common.langchain4j.handler.impl.DefaultFileHandler;
import com.csagent.common.langchain4j.handler.impl.MarkdownFileHandler;
import com.csagent.common.langchain4j.handler.impl.PdfFileHandler;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:27
 * @description: 文件操作工厂
 */
public class FileHandleFactory {

    /**
     * 根据文件选择文件处理器
     * @param ext String
     * @return FileSplitterInterface
     */
    public FileHandlerInterface getSplitter(String ext) {

        switch (ext) {
            case "md":
                return new MarkdownFileHandler();
            case "pdf":
                return new PdfFileHandler();
//            case "xls":
//            case "xlsx":
//            case "docx":
//            case "csv":
//                return new OfficeHandle();
            case "html":
        }

        return new DefaultFileHandler();
    }

}
