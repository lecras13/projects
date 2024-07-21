package io.slurm.cources.processing.service;


import io.slurm.cources.processing.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
@RequiredArgsConstructor
public class ExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeService.class);

    public static final String CURRENCY_RUB = "RUB";

    private final AccountService accountService;

    private final CurrencyService currencyService;

    @Lazy
    @Autowired
    private ExchangeService exchangeService;

    public BigDecimal exchangeCurrency(String uuid, Long fromAccount, Long toAccount, BigDecimal amount) {
        AccountEntity source = accountService.getAccountById(fromAccount);
        AccountEntity target = accountService.getAccountById(toAccount);

        LOGGER.info("Exchange operation {} from account {} to account {} target", fromAccount, toAccount, amount);

        BigDecimal result;
        if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrency(source.getCurrencyCode());
            result = exchangeService.exchangeWithMultiplay(uuid, source, target, rate, amount);

        } else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrency(target.getCurrencyCode());
            BigDecimal multiplier = new BigDecimal(1).divide(rate, 4, RoundingMode.HALF_DOWN);
            result = exchangeService.exchangeWithMultiplay(uuid, source, target, multiplier, amount);

        } else if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
            BigDecimal rateFrom = currencyService.loadCurrency(source.getCurrencyCode());
            BigDecimal rateTo = currencyService.loadCurrency(target.getCurrencyCode());
            result = exchangeThroughRub(uuid, source, target, rateFrom, rateTo, amount);

        } else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
            result = simpleExchange(uuid, source, target, amount);

        } else {
            throw new IllegalArgumentException("Unknown behavior");
        }

        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal simpleExchange(String uuid, AccountEntity source, AccountEntity target, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
        accountService.addMoneyToAccount(uuid, target.getId(), amount);
        return amount;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeWithMultiplay(String uuid, AccountEntity source, AccountEntity target, BigDecimal rate,
                                            BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
        BigDecimal result = amount.multiply(rate);
        accountService.addMoneyToAccount(uuid, target.getId(), result);
        return amount;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeThroughRub(String uuid, AccountEntity source, AccountEntity target, BigDecimal rateFrom,
                                         BigDecimal rateTo,
                                         BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
        BigDecimal rub = amount.multiply(rateFrom);
        BigDecimal result = rub.divide(rateTo, 4, RoundingMode.HALF_DOWN);
        accountService.addMoneyToAccount(uuid, target.getId(), result);
        return amount;
    }
}