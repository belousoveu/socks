package com.github.belousovea.sockwarehouse.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "color_index", columnList = "color"),
        @Index(name = "cotton_content_index", columnList = "cottonContent")
})
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String color;
    private int cottonContent;
    private int quantity;
}
