package br.com.dio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.stream.Stream;

import br.com.dio.dao.BasicBasketDao;
import br.com.dio.model.BasicBasket;
import br.com.dio.model.Box;

public class BasicBasketService {

    private final static Scanner scanner = new Scanner(System.in);

    private static final BasicBasketDao basicBasketDao = new BasicBasketDao();

    public static void checkStock() {
        var res = basicBasketDao.checkStock();
        System.out.printf(
                "Existem %s cestas em estoque, das quais %s estão fora do prazo de validade \n",
                res.inStock(), res.outdate());
    }

    public static BigDecimal soldItems() {
        System.out.printf("Quantas cestas serão vendidas? ");
        var amount = scanner.nextInt();

        return basicBasketDao.soldItems(amount);
    }

    public static void removeItemsOutOfDate() {
        var res = basicBasketDao.removeItemsOutOfDate();
        System.out.printf("Foram descartadas do estoque %s cestas vencidas, o prejuízo foi de %s \n",
                res.size(), res.lost());
    }

    private static long receive(Box box) {
        var unitPrice = box.price().divide(new BigDecimal(box.amount()), RoundingMode.CEILING);
        var finalPrice = unitPrice.add(unitPrice.multiply(new BigDecimal("0.20")));
        var baskets = Stream.generate(() -> new BasicBasket(box.validate(), finalPrice))
                .limit(box.amount())
                .toList();
        basicBasketDao.addAll(baskets);

        return baskets.size();
    }

    public static void receiveBasicBasket(Box box) {
        var added = receive(box);
        System.out.printf("Foram adicionadas %s cestas ao estoque\n", added);
    }


}
