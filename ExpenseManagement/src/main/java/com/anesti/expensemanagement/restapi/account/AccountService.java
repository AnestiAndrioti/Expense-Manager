package com.anesti.expensemanagement.restapi.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.anesti.expensemanagement.Account;

import com.anesti.expensemanagement.restapi.exceptions.ResourceNotFoundException;

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

    public Account getAccountFromRepository(long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            logger.warn("An Attempt to fetch an account with ID {} was made. This account does not exist.", accountId);
            throw new ResourceNotFoundException("Account with id " + accountId + " was not found.");
        }
    }

    public Account addAccount(Account account) { 
        return accountRepository.save(account);
    }

    public void deleteAccount(long accountId) {
        Account account = getAccountFromRepository(accountId);
        accountRepository.delete(account);
    }
}
