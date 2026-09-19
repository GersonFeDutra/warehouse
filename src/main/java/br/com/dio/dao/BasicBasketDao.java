package br.com.dio.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.dio.model.BasicBasket;

public class BasicBasketDao {

    // In-memory storage
    private static List<BasicBasket> stock = new ArrayList<>();

    public Stream<BasicBasket> filterOutOfDate() {
        return stock.stream().filter(b -> b.validate().isBefore(LocalDate.now()));
    }

    public record CheckResponse(int inStock, long outdate) {
    }

    public CheckResponse checkStock() {
        int amount = stock.size();
        long outOfDate = filterOutOfDate().count();
        return new CheckResponse(amount, outOfDate);
    }

    public List<BasicBasket> getOutOfDateItems() {
        var outOfDate = filterOutOfDate().toList();
        return outOfDate;
    }

    public record RemoveItemsOutOfDateResponse(long size, BigDecimal lost) {
    }

    public RemoveItemsOutOfDateResponse removeItemsOutOfDate() {
        var outOfDate = filterOutOfDate().toList();
        var lost = outOfDate.stream().map(BasicBasket::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        stock = stock.stream().filter(b -> b.validate().isBefore(LocalDate.now())).collect(Collectors.toList());

        return new RemoveItemsOutOfDateResponse(outOfDate.size(), lost);
    }

    public BigDecimal soldItems(int amount) {
        stock.sort(Comparator.comparing(BasicBasket::price));
        var toSold = stock.subList(0, amount);
        var value = toSold.stream().map(BasicBasket::price).reduce(BigDecimal.ZERO, BigDecimal::add);

        return value;
    }

    public void addAll(List<BasicBasket> baskets) {
        stock.addAll(baskets);
    }

}
