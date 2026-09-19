package br.com.dio.service;

import java.time.LocalDate;
import java.util.Scanner;

import br.com.dio.model.Box;

public class BoxService {
    private static final Scanner scanner = new Scanner(System.in);

    public static Box fetchBox() {
        System.out.println("Informe o valor da entrega");
        var price = scanner.nextBigDecimal();
        System.out.println("Informe a quantidade de cestas da entrega");
        var amount = scanner.nextLong();
        System.out.println("Informe a data de vencimento");
        var validate = scanner.next();
        var day = Integer.parseInt(validate.split("/")[0]);
        var month = Integer.parseInt(validate.split("/")[1]);
        var year = Integer.parseInt(validate.split("/")[2]);

        return new Box(amount, LocalDate.of(year, month, day), price);
    }

}
