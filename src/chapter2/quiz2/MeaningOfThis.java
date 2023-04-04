package chapter2.quiz2;

public class MeaningOfThis {
    /*
    Q:
    다음 코드를 실행한 결과는 4, 5, 6, 42 중 어느 것일까?
     */

    public final int value = 4;

    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;

            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String... args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt(); // ???
    }

}
