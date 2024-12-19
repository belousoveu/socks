package com.github.belousovea.sockwarehouse.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SocksFilterDto implements GoodsFilterDto {
    private String color;
    private int cottonContentMin;
    private int cottonContentMax;
    private int cottonContent;
    private Operation operation;
    private boolean sortedByColor;
    private boolean sortedByCottonContent;
    private List<SortedParams> sortedParams;


    private enum Operation {
        MORE_THAN ("moreThan"),
        LESS_THAN ("lessThan"),
        EQUALS ("equals");

        private String operation;

        Operation(String title) {
        }


    }

    private enum SortedParams {
        color,
        cottonContent
    }
}
