package ru.exercises.alidi.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BasketСalculationDto {
    private List<ProductDto> products;
    private Float amount;
}
