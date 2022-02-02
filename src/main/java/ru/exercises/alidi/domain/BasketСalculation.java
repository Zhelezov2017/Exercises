package ru.exercises.alidi.domain;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BasketСalculation {

    private List<Product> products;
    private Float amount;
}
