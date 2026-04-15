package com.csagent.knowledge.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/3/14 11:03
 * @description: 知识库Dto
 */
@Data
public class KbDatasetSimple implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 知识库标题
     */
    private String title;
}
