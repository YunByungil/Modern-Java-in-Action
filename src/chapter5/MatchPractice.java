package chapter5;

import chapter4.Dish;

import static chapter4.Dish.*;

public class MatchPractice {
    public static void main(String[] args) {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("vegetarian");
        } // 하나라도 만족하냐?

        boolean isHealthy = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);
        System.out.println("isHealthy = " + isHealthy); // 모든 값이 만족하냐?

        boolean isHealthy2 = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);
        System.out.println("isHealthy2 = " + isHealthy2); // 모든 값이 다 만족 안 하냐?
    }
}
