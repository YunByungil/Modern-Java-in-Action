package chapter2.quiz1;

import chapter2.FilteringApples;

import java.util.List;

public class Quiz1 {
    /*
    Q:
    사과 리스트를 인수로 받아 다양한 방법으로 문자열을 생성할 수 있도록 prettyPrintApple 메서드를 구현하시오.

     */
    public static void prettyPrintApple(List<FilteringApples.Apple> inventory, AppleFormatter formatter) {
        for (FilteringApples.Apple apple : inventory) {
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }



}
