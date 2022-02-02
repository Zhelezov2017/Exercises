package ru.exercises.alidi.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.exercises.alidi.mapper.BasketMapper;
import ru.exercises.alidi.rest.dto.BasketDto;
import ru.exercises.alidi.rest.dto.BasketСalculationDto;
import ru.exercises.alidi.service.BasketService;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/api/v1/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PutMapping("/basketСalculation")
    public BasketСalculationDto basketСalculation(@NotNull @RequestBody BasketDto dto) {

        log.trace("Транзакция по перерасчету корзины началась");

        return BasketMapper.toBasketСalculationDto(basketService.basketСalculation(dto));
    }
}
