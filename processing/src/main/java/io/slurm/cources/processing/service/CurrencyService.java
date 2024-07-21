package io.slurm.cources.processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${service.currency.url}")
    private String currencyuUrl;

    public BigDecimal loadCurrency(String code) {
        return restTemplate.getForObject(currencyuUrl + "/currency/rate/{code}", BigDecimal.class, code);
    }
}
