package chapter5.quiz1;

import chapter5.Trader;
import chapter5.Transaction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Q4 {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        /*
        Q:
        2011년에 일어난 모든 트랙잭션을 찾아 값을 오름차순으로 정리하시오.
         */
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

        /*
        Q:
        거래자가 근무하는 모든 도시를 중복 없이 나타내시오
         */
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());

        Set<String> cities2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(toSet());

        /*
        Q:
        케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
         */
        List<Trader> cambridge = transactions.stream()
                .map(Transaction::getTrader)
                .filter(transaction -> transaction.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println("cambridge = " + cambridge);

        /*
        Q:
        모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
         */
        String reduce = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println("reduce = " + reduce);

        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining());
        System.out.println("traderStr = " + traderStr);


        /*
        Q:
        밀라노에 거래자가 있는가?
         */
        boolean milanBased = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println("milanBased = " + milanBased);

        /*
        Q:
        케임브리지에 거주하는 거래자의 모든 트랙잭션값을 출력하시오.
         */
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        /*
        전체 트랜잭션 중 최댓값은 얼마인가?
         */
        Optional<Integer> highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println("highestValue = " + highestValue);

        /*
        전체 트랜잭션 중 최솟값은 얼마인가?

         */
        Optional<Transaction> smallestTransaction = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        System.out.println("smallestTransaction = " + smallestTransaction);

        /*
        스트림은 최댓값이나 최솟값을 계산하는 데 사용할 키를 지정하는 Comparator를 인수로 받는 min과 max 메서드를 제공한다.
         */
        Optional<Transaction> min = transactions.stream()
                .min(comparing(Transaction::getValue));
        System.out.println("min = " + min);
    }
}
