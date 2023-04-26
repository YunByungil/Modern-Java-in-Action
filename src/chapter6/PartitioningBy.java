package chapter6;

import chapter4.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chapter4.Dish.menu;
import static java.util.stream.Collectors.*;

public class PartitioningBy {
    public static void main(String[] args) {
         /*
        6.4 분할채식 요리와 채식이 아닌 요리로 분류
         */
        Map<Boolean, List<chapter4.Dish>> partitionedMenu = menu.stream()
                .collect(partitioningBy(chapter4.Dish::isVegetarian));
        System.out.println("partitionedMenu = " + partitionedMenu);
        System.out.println();

        List<Dish> vegetarianDishes = partitionedMenu.get(true); // 참값의 키로 맵에서 모든 채식 요리를 얻을 수 있다.
        System.out.println("vegetarianDishes = " + vegetarianDishes); // filter만 이용해서도 얻을 수 있음.
        System.out.println();

        /*
        분할의 장점:
        채식이 아닌 모든 요리 리스트를 얻을 수 있고,
        컬렉터를 두 번째 인수로 전달할 수 있는 오버로드 된 더버전의 partitioningBy 메서드도 있다.
         */
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)));
        System.out.println("vegetarianDishesByType = " + vegetarianDishesByType);
        System.out.println();

        /*
        채식 요리와 채식이 아닌 요리 각각의 그룹에서 가장 칼로리가 높은 요리도 찾을 수 있다.
         */
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));
        System.out.println("mostCaloricPartitionedByVegetarian = " + mostCaloricPartitionedByVegetarian);
        System.out.println();
        /*
        Quiz:
        Quiz1
         */
        System.out.println("===========================================");
        /*
        6.4.2 - 숫자를 소수와 비소수로 분할하기
        정수 n을 인수로 받아서 2에서 n까지의 자연수를 소수와 비소수로 나누는 프로그램을 구현하자. -> isPrime 메서드
         */
        Map<Boolean, List<Integer>> booleanListMap = partitionPrimes(11);
        System.out.println("booleanListMap = " + booleanListMap);

    }

    public static boolean isPrime(int candidate) {
        return IntStream.range(2, candidate) // 2부터 candidate 미만 사이의 자연수를 생성한다.
                .noneMatch(i -> candidate % i == 0); // 스트림의 모든 정수로 candidate를 나눌 수 없으면 참을 반환한다.
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }
}
