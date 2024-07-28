package io.slurm.cources.processing.service;

import io.slurm.cources.processing.dto.NewAccountDto;
import io.slurm.cources.processing.model.AccountEntity;
import io.slurm.cources.processing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    public final ResolveUserService resolveUserService;

    @Transactional
    public AccountEntity createNewAccount(NewAccountDto newAccountDto) {
        var account = new AccountEntity();
        account.setCurrencyCode(newAccountDto.getCurrencyCode());
        account.setUserId(newAccountDto.getUserId());
        account.setBalance(new BigDecimal(0));

        return accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountEntity addMoneyToAccount(String uid, Long accountId, BigDecimal money) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);

        return account.map(acc -> {
            var balance = acc.getBalance().add(money);
            acc.setBalance(balance);
            return accountRepository.save(acc);
        }).orElseThrow(() -> new IllegalArgumentException("Account with Id " + accountId + " was not found"));
    }

    public AccountEntity getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account with Id " + id + " was not found"));
    }

    public List<AccountEntity> getAllAccounts(){
        return accountRepository.findByUserId(resolveUserService.resolveId());
    }
}
