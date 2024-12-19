package com.github.belousovea.sockwarehouse.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String color;
    private int cottonContent;
    private int quantity;
}
