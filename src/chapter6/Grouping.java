package chapter6;

import chapter4.Dish;

import java.util.List;
import java.util.Map;
import java.util.Set;
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


    }
}
