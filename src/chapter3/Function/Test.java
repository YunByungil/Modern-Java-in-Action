package chapter3.Function;

import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline =
                addHeader.andThen(Letter::checkSpelling)
                        .andThen(Letter::addFooter);

        String ladbd = transformationPipeline.apply("labda123");
        System.out.println("result = " + ladbd);

    }
}
