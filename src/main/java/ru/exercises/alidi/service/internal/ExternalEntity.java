package ru.exercises.alidi.service.internal;

import lombok.Data;

import java.util.Map;

@Data
public class ExternalEntity {
    Map<Integer, Integer> idToPayment;
}
