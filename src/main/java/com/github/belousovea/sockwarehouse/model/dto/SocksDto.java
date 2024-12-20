package com.github.belousovea.sockwarehouse.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocksDto implements GoodsDto {

    @NotNull
    private String color;

    @Min(value = 0, message = "Процент содержания хлопка не может быть меньше 0")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private int cottonContent;
    @Positive(message = "Количество должно быть больше нуля")
    private int quantity;

}
