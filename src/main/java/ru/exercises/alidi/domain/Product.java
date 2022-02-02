package ru.exercises.alidi.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {

    private Integer id;
    private Integer count;
    private Float positionAmount;
}
