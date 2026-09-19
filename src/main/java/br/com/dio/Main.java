package br.com.dio;

import java.util.Scanner;

import br.com.dio.service.BasicBasketService;
import br.com.dio.service.BoxService;
import br.com.dio.service.MoneyService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Bem vindo ao sistema de armazém");
        run();

    }

    public static void run() {
        try (var scanner = new Scanner(System.in)) {
            int option;

            System.out.println("Selecione a opção desejada");
            while (true) {
                System.out.println("1 - Verificar estoque de cesta básica");
                System.out.println("2 - Verificar caixa");
                System.out.println("3 - Receber Cestas");
                System.out.println("4 - Vender Cestas");
                System.out.println("5 - Remover itens vencidos");
                System.out.println("6 - Sair");
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> BasicBasketService.checkStock();
                    case 2 -> MoneyService.checkMoney();
                    case 3 -> BasicBasketService.receiveBasicBasket(BoxService.fetchBox());
                    case 4 -> MoneyService.add(BasicBasketService.soldItems());
                    case 5 -> BasicBasketService.removeItemsOutOfDate();
                    case 6 -> System.exit(0);
                }
            }
        }
    }


}
