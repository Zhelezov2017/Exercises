package ru.exercises.alidi.service;

import ru.exercises.alidi.domain.BasketСalculation;
import ru.exercises.alidi.rest.dto.BasketDto;

public interface BasketService {
    BasketСalculation basketСalculation(BasketDto dto);
}
