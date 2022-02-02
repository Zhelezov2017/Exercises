package ru.exercises.alidi.mapper;

import ru.exercises.alidi.domain.BasketСalculation;
import ru.exercises.alidi.rest.dto.BasketСalculationDto;
import ru.exercises.alidi.rest.dto.ProductDto;

import java.util.stream.Collectors;

public class BasketMapper {

    public static BasketСalculationDto toBasketСalculationDto(BasketСalculation basketСalculation) {
        return new BasketСalculationDto()
                .setAmount(basketСalculation.getAmount())
                .setProducts(basketСalculation.getProducts()
                        .stream()
                        .map(product -> new ProductDto()
                                .setCount(product.getCount())
                                .setId(product.getId())
                                .setPositionAmount(product.getPositionAmount()))
                        .collect(Collectors.toList()));
    }


}
