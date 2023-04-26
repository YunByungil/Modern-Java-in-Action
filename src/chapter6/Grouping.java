package chapter6;

import chapter4.Dish;

import java.util.*;
import java.util.stream.Collectors;

import static chapter4.Dish.*;
import static chapter6.Dish.dishTags;
import static java.util.stream.Collectors.*;

public class Grouping {
    enum CaloricLevel {
        DIET, NORMAL, FAT
    }

    public static void main(String[] args) {
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }));

        System.out.println("dishesByCaloricLevel = " + dishesByCaloricLevel);

        Map<Type, List<Dish>> caloricDishesByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));
        System.out.println("caloricDishesByType = " + caloricDishesByType);

        Map<Type, List<String>> dishNamesByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                mapping(Dish::getName, toList())));
        System.out.println("dishNamesByType = " + dishNamesByType);

        Map<Type, Set<String>> dishNamesByType2 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(),
                                toSet())));
        System.out.println("dishNamesByType2 = " + dishNamesByType2);

        Map<Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) {
                                        return CaloricLevel.DIET;
                                    } else if (dish.getCalories() <= 700) {
                                        return CaloricLevel.NORMAL;
                                    } else {
                                        return CaloricLevel.FAT;
                                    }
                                })
                        )
                );
        System.out.println("dishesByTypeCaloricLevel = " + dishesByTypeCaloricLevel);

        System.out.println("===========================================");
        /*
        서브그룹으로 데이터 수집
        한 개의 인수를 갖는 groupingBy(f)는 사실 groupingBy(f, toList())의 축약형이다.
         */
        Map<Type, Long> typesCount = menu.stream()
                .collect(groupingBy(Dish::getType, counting()));
        System.out.println("typesCount = " + typesCount);
        System.out.println();

        Map<Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("mostCaloricByType = " + mostCaloricByType);
        System.out.println();

        Map<Type, Dish> mostCaloricByType2 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get
                        )));
        System.out.println("mostCaloricByType2 = " + mostCaloricByType2);
        System.out.println();

        Map<Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                                    if (dish.getCalories() <= 400) {
                                        return CaloricLevel.DIET;
                                    } else if (dish.getCalories() <= 700) {
                                        return CaloricLevel.NORMAL;
                                    } else {
                                        return CaloricLevel.FAT;
                                    }
                                },
                                toCollection(HashSet::new) )));
        System.out.println("caloricLevelsByType = " + caloricLevelsByType);
        System.out.println();

        
        System.out.println("===========================================");



    }
}
