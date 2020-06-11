package com.anesti.expensemanagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    public void canCreateMoney() {
        Money money = new Money(Currency.USD, 100);

        assertEquals(Currency.USD, money.getCurrency());
        assertEquals(100, money.getAmount());
        assertEquals(new Money(Currency.USD, 100), money);
    }

    @Test
    public void cannotCreateNegativeValueMoney() {
        assertThrows(IllegalArgumentException.class, () -> new Money(Currency.USD, -100));
    }
}
