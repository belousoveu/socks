package com.github.belousovea.sockwarehouse.exception;

import com.github.belousovea.sockwarehouse.model.dto.GoodsDto;

public class NotEnoughGoodsException extends RuntimeException {
    public NotEnoughGoodsException(GoodsDto dto) {

        super("Не достаточно товара для отгрузки данной партии" + dto.toString());
    }
}
