package ru.exercises.alidi.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ProductDto {
    @NotNull
    private Integer id;
    @NotNull
    private Integer count;
    private Float positionAmount;
}
