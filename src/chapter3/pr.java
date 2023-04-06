package chapter3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.joining;

public class pr {
    public static void main(String[] args) {

        Supplier<Apple> sup = Apple::new;
        Function<Integer, Apple> fun = Apple::new;
        Function<String, Apple> fun2 = Apple::new;
        BiFunction<String, Integer, Apple> bi = Apple::new;

        List<Apple> list = Arrays.asList(
                new Apple("red", 150),
                new Apple("green", 100)
        );

//        list.sort((a1, a2) -> a1.getColor().compareTo(a2.getColor()));
        list.sort(comparing(Apple::getWeight));
        for (Apple apple : list) {
            System.out.println("apple = " + apple.getWeight());
        }
    }
}
