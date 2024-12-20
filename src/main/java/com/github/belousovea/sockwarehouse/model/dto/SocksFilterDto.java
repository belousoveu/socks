package com.github.belousovea.sockwarehouse.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.belousovea.sockwarehouse.exception.IllegalRequestParameterException;
import com.github.belousovea.sockwarehouse.exception.TooManyFilterParametersException;
import com.github.belousovea.sockwarehouse.exception.TooManySortingParametersException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SocksFilterDto implements GoodsFilterDto {


    private String color;
    private Integer cottonContentMin;
    private Integer cottonContentMax;
    private Integer cottonContent;

    private String operator;

    private boolean sortedByColor;
    private boolean sortedByCottonContent;

    @JsonIgnore
    private final Set<String> operators = Set.of("moreThan", "lessThan", "equals");

    @JsonIgnore
    private String queryCondition = "";

    @JsonIgnore
    private boolean filterCondition1;
    @JsonIgnore
    private boolean filterCondition2;

    public void setCottonContent(Integer cottonContent) {
        if (cottonContent != null && (cottonContent < 0 || cottonContent > 100)) {
            throw new IllegalRequestParameterException("cottonContent", cottonContent);
        }
        this.cottonContent = cottonContent;
    }

    public void setCottonContentMin(Integer cottonContent) {
        if (cottonContent != null && (cottonContent < 0 || cottonContent > 100)) {
            throw new IllegalRequestParameterException("cottonContentMin", cottonContent);
        }
        this.cottonContentMin = cottonContent;
    }

    public void setCottonContentMax(Integer cottonContent) {
        if (cottonContent != null && (cottonContent < 0 || cottonContent > 100)) {
            throw new IllegalRequestParameterException("cottonContentMax", cottonContent);
        }
        this.cottonContentMax = cottonContent;
    }

    public void setOperator(String operator) {
        if (operator != null && !operators.contains(operator)) {
            throw new IllegalRequestParameterException("operator", operator);
        }
        this.operator = operator;
    }

//    public String getQueryCondition() {
//        return  this.queryCondition;
//
//    }

    public SocksFilterDto filter() {
        validateFilterParameters();

        StringBuilder sb = new StringBuilder();

        if (this.color != null) {
            sb.append("s.color=").append("'").append(color).append("'");
        }
        if (this.filterCondition1) {
            sb.append(sb.isEmpty() ? "" : " AND ");
            switch (this.operator) {
                case "moreThan":
                    sb.append(" s.cotton_content > ").append(cottonContent);
                    break;
                case "lessThan":
                    sb.append(" s.cotton_content < ").append(cottonContent);
                    break;
                case "equals":
                    sb.append(" s.cotton_content = ").append(cottonContent);
                    break;
            }
        }
        if (this.filterCondition2) {
            sb.append(sb.isEmpty() ? "" : " AND ");
            sb.append(" s.cotton_content >= ").append(cottonContentMin);
            sb.append(" AND s.cotton_content <= ").append(cottonContentMax);
        }
        sb.insert(0, sb.isEmpty() ? "" : " WHERE ");

        this.queryCondition = sb.toString();
        return this;
    }

    public SocksFilterDto order() {
        validateOrderParameters();
        StringBuilder sb = new StringBuilder(this.queryCondition);
//        sb.append(sb.isEmpty() ? " TRUE" :"");
        if (this.sortedByColor) {
            sb.append(" ORDER BY s.color");
        }
        if (this.sortedByCottonContent) {
            sb.append(" ORDER BY s.cotton_content");
        }
        this.queryCondition = sb.toString();
        return this;
    }


    private void validateFilterParameters() {
        this.filterCondition1 = this.cottonContent != null && this.operator != null;
        this.filterCondition2 = this.cottonContentMin != null && this.cottonContentMax != null;
        if (this.filterCondition1 && this.filterCondition2) {
            throw new TooManyFilterParametersException();
        }
    }

    private void validateOrderParameters() {
        if (this.sortedByColor && this.sortedByCottonContent) {
            throw new TooManySortingParametersException();
        }
    }
}
