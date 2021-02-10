package com.anesti.expensemanagement.restapi.expense;

import java.util.List;

import com.anesti.expensemanagement.Expense;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "expenses", path = "expenses")
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    List<Expense> findByAccountId(Long accountId);
}
