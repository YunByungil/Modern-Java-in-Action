package chapter5.quiz1;

import chapter4.Dish;

public class Q3 {
    public static void main(String[] args) {
        /*
        Q:
        map과 reduce 메서드를 이용해서 스트림의 요리 개수를 계산하시오.
         */
        int count = Dish.menu.stream()
                .map(d -> 1)
                .reduce(0, (a, b) -> a + b);
        System.out.println("count = " + count);
    }
}
