package com.anesti.expensemanagement.restapi.account;

import java.net.URI;

import java.util.List;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.restapi.exceptions.BadRequestException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class AccountController {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping("/accounts/{accountId}")
    public Account getAccount(@PathVariable long accountId) {
        return accountService.getAccountFromRepository(accountId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        if (account.getId() != 0) {
            logger.warn("An Attempt to create an account with ID {} was made. Post requests should not have a body that provides an id. " +
                "Account creation aborted.", account.getId());
            throw new BadRequestException("The account was attempted to be created with id " + account.getId() +
                ". Post Requests must not contain an ID in the body.");
        }

        Account savedAccount = accountService.addAccount(account);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAccount.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{accountId}")
    public void deleteAccount(@PathVariable long accountId) {
        accountService.deleteAccount(accountId);
    }
}
