package chapter2.quiz1;

import chapter2.FilteringApples;

public class AppleFancyFormatter implements AppleFormatter{
    @Override
    public String accept(FilteringApples.Apple apple) {
        String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
        return "A " + characteristic + " " + apple.getColor() + " apple";
    }
}
