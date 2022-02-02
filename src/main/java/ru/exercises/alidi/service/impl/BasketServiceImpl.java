package ru.exercises.alidi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.exercises.alidi.domain.BasketСalculation;
import ru.exercises.alidi.domain.Product;
import ru.exercises.alidi.rest.dto.BasketDto;
import ru.exercises.alidi.rest.dto.ProductDto;
import ru.exercises.alidi.service.BasketService;
import ru.exercises.alidi.service.internal.ExternalEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@PropertySource("classpath:application.yml")
public class BasketServiceImpl implements BasketService {

    private final RestTemplate restTemplate = new RestTemplate();

    private Map<Integer, Integer> idToPrice;

    @Value("application.external-url")
    private String externalUrl;

    @Override
    public BasketСalculation basketСalculation(BasketDto dto) {

        log.debug("Определение ids которых нету в кеше!");
        List<Integer> nonPresentIds = dto.getProducts().stream()
                .map(ProductDto::getId)
                .filter(id -> Objects.isNull(idToPrice.get(id)))
                .collect(Collectors.toList());

        this.externalRequestByProducts(nonPresentIds);

        log.debug("Проставление данных о productDtos!");
        List<ProductDto> productDtos = dto.getProducts().stream()
                .peek(productDto -> {
                    Integer price = idToPrice.get(productDto.getId());
                    productDto.setPositionAmount((float) (productDto.getCount() * price));
                })
                .collect(Collectors.toList());

        log.debug("Заполнение данных о BasketСalculation!");

        return new BasketСalculation()
                .setProducts(productDtos.stream()
                        .map(productDto -> new Product()
                                .setId(productDto.getId())
                                .setCount(productDto.getCount())
                                .setPositionAmount(productDto.getPositionAmount()))
                        .collect(Collectors.toList()))
                .setAmount(productDtos.stream()
                        .map(ProductDto::getPositionAmount)
                        .reduce(Float::sum).get());

    }

    private void externalRequestByProducts(List<Integer> nonPresentIds) {
        if (!nonPresentIds.isEmpty()) {
            log.debug("Отправление запроса на получение дополнительных данных о товарах!");
            ResponseEntity<ExternalEntity> responseEntity = restTemplate.postForEntity(
                    externalUrl + "/basketСalculation",
                    getEntity(nonPresentIds),
                    ExternalEntity.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.debug("Получили успешный запрос от внешнего сервиса!");

                ExternalEntity body = responseEntity.getBody();

                if (Objects.nonNull(body)) {
                    log.debug("Body из внешнего сервиса усешного преобразовано в Объект!!");
                    Map<Integer, Integer> idToPaymentExternal = body.getIdToPayment();
                    idToPrice.putAll(idToPaymentExternal);
                }
            }

        }
    }

    @SneakyThrows
    private HttpEntity<String> getEntity(List<Integer> request) {

        HttpHeaders headers = getHttpHeaders();

        String json = new ObjectMapper().writeValueAsString(request);

        return new HttpEntity<>(json, headers);
    }

    private HttpHeaders getHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
