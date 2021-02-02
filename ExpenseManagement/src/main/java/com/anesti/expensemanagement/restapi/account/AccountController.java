package com.anesti.expensemanagement.restapi.account;

import com.anesti.expensemanagement.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping("/accounts/{accountId}")
    public Optional<Account> getAccount(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts")
    public void addAccount(@RequestBody Account account) {
        accountService.addAccount(account);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{accountId}")
    public void addAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
    }
}
