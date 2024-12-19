package com.github.belousovea.sockwarehouse.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.belousovea.sockwarehouse.exception.IllegalRequestParameterException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SocksFilterDto implements GoodsFilterDto {

    private String color;
    @Min(value = 0, message = "Процент содержания хлопка не может быть меньше 0")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private Integer cottonContentMin;
    @Min(value = 0, message = "Процент содержания хлопка не может быть меньше 0")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private Integer cottonContentMax;
    @Min(value = 0, message = "Процент содержания хлопка не может быть меньше 0")
    @Max(value = 100, message = "Процент содержания хлопка не может быть больше 100")
    private Integer cottonContent;

    private String operator;

    private boolean sortedByColor;
    private boolean sortedByCottonContent;

    @JsonIgnore
    private final Set<String> operators = Set.of("moreThan", "lessThan", "equals");


    public void setOperator(String operator) {
        if (!operators.contains(operator)) {
            throw new IllegalRequestParameterException("operator", operator);
        }
    }

}
