package br.com.dio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.stream.Stream;

import br.com.dio.dao.BasicBasketDao;
import br.com.dio.model.BasicBasket;
import br.com.dio.model.Box;
import br.com.dio.model.Money;

public class BasicBasketService {

    private final static Scanner scanner = new Scanner(System.in);

    private static final Money money = new Money();

    private static final BasicBasketDao basicBasketDao = new BasicBasketDao();

    private static long receive(LocalDate validate, long amount, BigDecimal price) {
        var box = new Box(amount, validate, price);
        var unitPrice = box.price().divide(new BigDecimal(box.amount()), RoundingMode.CEILING);
        var finalPrice = unitPrice.add(unitPrice.multiply(new BigDecimal("0.20")));
        var baskets = Stream.generate(() -> new BasicBasket(box.validate(), finalPrice))
                .limit(box.amount())
                .toList();
        basicBasketDao.addAll(baskets);

        return baskets.size();
    }

    private static void checkStock() {
        var res = basicBasketDao.checkStock();
        System.out.printf(
                "Existem %s cestas em estoque, das quais %s estão fora do prazo de validade \n",
                res.inStock(), res.outdate());
    }

    private static void soldItems() {
        System.out.printf("Quantas cestas serão vendidas? ");
        var amount = scanner.nextInt();
        var value = basicBasketDao.soldItems(amount);
        money.add(value);
        System.out.printf("O valor da venda é de %s \n", value);
    }

    private static void checkMoney() {
        System.out.printf("O caixa no momento é de %s\n", money.get());
    }

    private static void removeItemsOutOfDate() {
        var res = basicBasketDao.removeItemsOutOfDate();
        System.out.printf("Foram descartadas do estoque %s cestas vencidas, o prejuízo foi de %s \n",
                res.size(), res.lost());
    }

    private static void receiveItems() {
        System.out.println("Informe o valor da entrega");
        var price = scanner.nextBigDecimal();
        System.out.println("Informe a quantidade de cestas da entrega");
        var amount = scanner.nextLong();
        System.out.println("Informe a data de vencimento");
        var validate = scanner.next();
        var day = Integer.parseInt(validate.split("/")[0]);
        var month = Integer.parseInt(validate.split("/")[1]);
        var year = Integer.parseInt(validate.split("/")[2]);

        var added = receive(LocalDate.of(year, month, day), amount, price);
        System.out.printf("Foram adicionadas %s cestas ao estoque\n", added);
    }

    public static void run() {
        System.out.println("Selecione a opção desejada");
        int option;
        while (true) {
            System.out.println("1 - Verificar estoque de cesta básica");
            System.out.println("2 - Verificar caixa");
            System.out.println("3 - Receber Cestas");
            System.out.println("4 - Vender Cestas");
            System.out.println("5 - Remover itens vencidos");
            System.out.println("6 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> checkStock();
                case 2 -> checkMoney();
                case 3 -> receiveItems();
                case 4 -> soldItems();
                case 5 -> removeItemsOutOfDate();
                case 6 -> System.exit(0);
            }
        }
    }

}
