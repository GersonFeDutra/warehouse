package br.com.dio.model;

import java.math.BigDecimal;

public class Money {
    private BigDecimal money = BigDecimal.ZERO;

    public BigDecimal get() {
        return money;
    }

    public void set(BigDecimal value) {
        this.money = value;
    }

    public void add(BigDecimal value) {
        money = money.add(value);
    }
}
