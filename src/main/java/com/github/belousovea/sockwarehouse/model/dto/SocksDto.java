package com.github.belousovea.sockwarehouse.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @Min(value = 1, message = "Количество должно быть больше нуля")
    private int quantity;

}
