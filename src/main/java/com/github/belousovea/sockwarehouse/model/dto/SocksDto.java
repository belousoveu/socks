package com.github.belousovea.sockwarehouse.model.dto;

import lombok.Data;

@Data
public class SocksDto implements GoodsDto {

    private String color;
    private int cottonContent;
    private int quantity;

}
