package com.anesti.expensemanagement.restapi.expense;

import java.io.IOException;

import java.net.URI;

import java.util.List;

import com.anesti.expensemanagement.Expense;
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
public class ExpenseController {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    Logger logger = LogManager.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/accounts/{accountId}/expenses")
    public List<Expense> getAccountExpenses(@PathVariable long accountId) {
        return expenseService.getAccountExpenses(accountId);
    }

    @RequestMapping("/accounts/{accountId}/expenses/{expenseId}")
    public Expense getSpecificExpense(@PathVariable("accountId") long accountId,
        @PathVariable("expenseId") long expenseId) {
        return expenseService.getExpenseFromAccount(accountId, expenseId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accounts/{accountId}/expenses")
    public ResponseEntity<?> addExpenseToAccount(@PathVariable("accountId") long accountId, @RequestBody Expense expense) throws IOException, InterruptedException {
        if (expense.getId() != 0) {
            logger.warn("An Attempt to create an expense account with ID {} was made. Post requests should not have a body that provides an id. " +
                "Expense creation aborted.", expense.getId());
            throw new BadRequestException("The expense was attempted to be created with id " + expense.getId() +
                ". Post Requests must not contain an ID in the body.");
        }

        Expense savedExpense = expenseService.addExpenseToAccount(accountId, expense);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedExpense.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{accountId}/expenses/{expenseId}")
    public void removeExpenseFromAccount(@PathVariable("accountId") long accountId,
        @PathVariable("expenseId") long expenseId) {

        expenseService.deleteExpenseFromAccount(accountId, expenseId);
    }
}
