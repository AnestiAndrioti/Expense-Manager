package com.anesti.expensemanagement.restapi.expense;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.Expense;
import com.anesti.expensemanagement.ExpenseManager;
import com.anesti.expensemanagement.restapi.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAccountExpenses(long accountId) {
        Account accountFromRepository = accountService.getAccountFromRepository(accountId);
        return new ArrayList<>(expenseRepository.findByAccountId(accountFromRepository.getId()));
    }

    public Expense getExpenseFromAccount(long accountId, long expenseId) {
        return expenseRepository.findByAccountId(accountId)
                .stream()
                .filter(expense -> expense.getId() == expenseId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Expense with expenseId " + expenseId + " in account " +
                        "with accountId " + accountId + " was not found."));
    }

    public Expense addExpenseToAccount(long accountId, Expense expense) throws IOException, InterruptedException {
        Account accountFromRepository = accountService.getAccountFromRepository(accountId);
        ExpenseManager.addExpenseToAccount(accountFromRepository, expense);
        return expenseRepository.save(expense);
    }

    public void deleteExpenseFromAccount(long accountId, long expenseId) {
        Account accountFromRepository = accountService.getAccountFromRepository(accountId);
        Expense expense = getExpenseFromAccount(accountId, expenseId);

        ExpenseManager.deleteExpenseFromAccount(accountFromRepository, expense);
        expenseRepository.delete(expense);
    }
}
