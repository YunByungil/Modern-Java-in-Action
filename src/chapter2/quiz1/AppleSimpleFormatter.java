package chapter2.quiz1;

import chapter2.FilteringApples;

public class AppleSimpleFormatter implements AppleFormatter{

    @Override
    public String accept(FilteringApples.Apple apple) {
        return "An apple of " + apple.getWeight() + "g";
    }
}
