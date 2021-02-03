package com.anesti.expensemanagement.restapi.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.anesti.expensemanagement.Account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    Logger logger = LogManager.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public void addAccount(Account account) {
        Optional<Account> optionalAccount = getAccount(account.getId());
        if (optionalAccount.isEmpty()) {
            accountRepository.save(account);
        } else {
            logger.warn("Attempting to create account with ID {}. This ID is already used by another account. " +
                "Account creation aborted.", account.getId());
        }
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
