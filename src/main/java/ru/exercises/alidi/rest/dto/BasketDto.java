package ru.exercises.alidi.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Accessors(chain = true)
public class BasketDto {
    @NotNull
    private List<ProductDto> products;
    @NotEmpty
    private String paymentType;
    private List<Integer> addressIds;
}
