package io.slurm.cources.processing.controller;

import io.slurm.cources.processing.dto.NewAccountDto;
import io.slurm.cources.processing.dto.PutAccountMoneyDto;
import io.slurm.cources.processing.dto.ExchangeMoneyDto;
import io.slurm.cources.processing.model.AccountEntity;
import io.slurm.cources.processing.service.AccountService;
import io.slurm.cources.processing.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
public class ProcessingController {

    private final AccountService accountService;

    private final ExchangeService exchangeService;

    @PostMapping("/account")
    public AccountEntity createAccount(@RequestBody NewAccountDto newAccountDto){
        return accountService.createNewAccount(newAccountDto);
    }

    @PutMapping("/account/{id}")
    public AccountEntity putMoney(@PathVariable("id") Long accountId, @RequestBody PutAccountMoneyDto data){
        return accountService.addMoneyToAccount(data.getUid(), accountId, data.getMoney());
    }

    @PutMapping("/account/exchange/{uid}")
    public BigDecimal exchange(@PathVariable String uid, @RequestBody ExchangeMoneyDto exchangeMoneyDto) {
        return exchangeService.exchangeCurrency(uid, exchangeMoneyDto.getFromAccountId(),
                exchangeMoneyDto.getToAccountId(), exchangeMoneyDto.getMoney());
    }

    @GetMapping("/account")
    public List<AccountEntity> getAllAccounts(){
        return accountService.getAllAccounts();
    }

}
