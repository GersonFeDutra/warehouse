package br.com.dio.service;

import java.math.BigDecimal;

import br.com.dio.model.Money;

public class MoneyService {
    private static final Money money = new Money();

    public static void checkMoney() {
        System.out.printf("O caixa no momento é de %s\n", money.get());
    }

    public static void add(BigDecimal value) {
        money.add(value);
        System.out.printf("O valor da venda é de %s \n", value);
    }
}
