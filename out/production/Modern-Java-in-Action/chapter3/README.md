## 1. 람다란 무엇인가?
람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화한 것이라고 할 수 있다.
### 람다의 특징
- 익명: 보통의 메서드와 달리 이름이 없으므로 익명이라 표현한다. 구현해야 할 코드에 대한 걱정거리가 줄어든다.
- 함수: 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다.
- 전달: 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
- 간결성: 익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.

### 람다의 구성
```java
// 기존 코드
Comparator<Apple> byWeight = new Comparator<Apple>() {
    @Override
    public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
    }
};

// 람다 코드
Comparator<Apple> byWeight = 
        (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```
- 파라미터 리스트: Comparator의 compare 메서드 파라미터(사과 두 개)
- 화살표: 화살표(->)는 람다의 파라미터 리스트와 바디를 구분한다.
- 람다 바디: 두 사과의 무게를 비교한다. 람다의 반환값에 해당하는 표현식이다.

### 람다 표현식 예제
(String s) -> s.length(): int를 반환  
(Apple a) -> a.getWeight() > 150: boolean 반환  
() -> 42: 파라미터가 없으며 int 42를 반환  
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()): 비교 결과 반환  

|사용 사례|람다 예제|  
|------|--------|
|불린 표현식| (List<String> list) -> list.isEmpty()|
|객체 생성|()->new Apple(10)|
|객체에서 소비|(Apple a)->{System.out.println(a.getWeight)}|
|객체에서 선택/추출|(String s)-> s.length()|
|두 값을 조합|(int a, int b)-> a * b|
|두 객체 비교|(Apple a1, Apple a2) -> a1.getWeight.compareTo(a2.getWeight);|
 
## 2. 어디에, 어떻게 람다를 사용할까?
### 함수형 인터페이스
- 정확히 하나의 추상 메서드를 지정하는 인터페이스
- 많은 디폴트 메서드가 있더라고 추상 메서드가 오직 하나면 함수형 인터페이스다.  
ex) Comparator, Runnable
  
람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으므로   
`전체 표현식을 함수형 인터페이스의 인스턴스 취급`할 수 있다.  
### [간단한 예제](https://github.com/YunByungil/Modern-Java-in-Action/blob/main/src/chapter3/Main.java)

### 함수 디스크립터
람다 표현식의 시그너처를 서술하는 메서드   
    
() -> void : 파라미터 리스트가 없으며 void 를 반환하는 함수: Runnable  

(Apple, Apple) -> int: 두 개의 Apple을 인수로 받아 int를 반환하는 함수

## 3. 람다 활용: 실행 어라운드 패턴
실행 어라운드 패턴(execute around pattern): 자원을 열고 -> 처리한 다음(변동) -> 자원을 닫는다  

## 4. 함수형 인터페이스 사용
함수 디스크립터(function descriptor) : 함수형 인터페이스의 추상 메서드 시그너처  
  
공통의 함수 디스크립터를 기술하는 함수형 인터페이스 집합 : Comparable, Runnable, Callable 등 자바에서 포함하고 있다  .
### Predicate
- Input: 제네릭 형식 T 객체
- Output: Boolean 반환
### Consumer
- Input: 제네릭 형식 T 객체
- Output: void 반환
### Function
- Input: 제네릭 형식 T 객체
- Output: 제네릭 형식 R 객체  

## 5. 형식 검사, 형식 추론, 제약
### 형식 검사
람다가 사용되는 컨텍스트를 이용해서 람다의 형식을 추론할 수 있다.  
  
대상형식 (target type) : 어떤 컨텍스트에서 기대되는 람다 표현식의 형식  
  
filter(inventory, (Apple a)->a.getWeight > 150);  
  
1. 람다가 사용된 컨텍스트확인 : filter의 정의 확인
2. 두 번째 파라미터로 Predicate<Apple> 형식(대상 형식)을 기대한다.
3. Predicate<Apple>의 추상메서드를 확인한다.
4. Apple을 인수로 받아 boolean을 반환하는 test 메서드다 (Apple->boolean)
5. 함수 디스크립터와, 람다의 시그너처가 일치한다: 형식검사 완료 

### 같은 람다, 다른 함수형 인터페이스
대상 형식이라는 특징덕분에 같은 람다표현식이더라도 호환되는 추상메서드를 가진 다른 함수형 인터페이스 사용이 가능하다.  
  
```java
Callable<Integer> c = () -> 42;
PrivilegedAction<Integer> p = () -> 42;
```
하나의 람다표현식을 다양한 함수형 인터페이스에 사용할 수 있다.
  
### 형식 추론
- 람다 표현식이 사용된 컨텍스트(대상 형식)를 이용해 람다 표현식과 관련된 함수형 인터페이스 추론
- 대상 형식을 이용해서 함수 디스크립터를 알 수 있다.
- 컴파일러는 람다의 시그너처도 추론할 수 있다.  
  
상황에 따라 명시적으로 형식을 포함하는 것이 좋을 때도 있고 형식을 배제하는 것이 가독성을 향상시킬 때도 있다.  
```java
// 형식 추론을 하지 않는다
Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());


// 형식 추론을 한다
Comparator<Apple> c = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());
```
### 지역 변수 사용
- 자유변수(free variable) : 파라미터로 넘겨진 변수가 아닌 외부에서 정의된 변수 => 람다캡처링(capturing lambda)
- 제약 : final로 선언되어있거나 실질적으로 final로 선언된 변수와 똑같이 사용되어야한다.  
  
람다 표현식은 한번만 할당할 수 있는 지역변수를 캡쳐한다.  
  
```java
int portNumber = 1337;
Runnable r = ()->System.out.println(portNumber);
portNumber = 31337;
```
- 인스턴스 변수는 힙에 저장, 지역변수는 스택에 저장된다.
- 람다가 스레드에서 실행된다면 변수를 할당한 스레드가 사라져서 변수 할당이 해제되었는데도 람다를 실행하는 스레드에서 해당 변수에 접근하려 할 수 있다.
- 원래 변수에 접근을 허용하지 않고 자유 지역 변수의 복사본을 제공한다
- 복사본의 값이 바뀌지 않아야한다 == 지역 변수에는 한 번만 값을 할당해야 한다.  
  
## 6. 메서드 레퍼런스
특정 메서드만을 호출하는 람다의 축약형이다.  
  
명시적으로 메서드명을 참조하여 가독성을 높일 수 있다! 메서드 명 앞에 구분자(::)를 붙이는 방식으로 활용한다.  
  
Apple 클래스에 정의된 getWeight 메서드 레퍼런스  
  
- Apple::getWeight
- (Apple a)-> a.getWeight()  
  
### 메서드 참조를 만드는 방법
1. 정적 메서드 참조  
Integer의 paserInt 메서드는 Integer::parseInt로 표현할 수 있다.  
2. 다양한 형식의 인스턴스 메서드 참조  
String의 length 메서드는 String::length로 표현할 수 있다.
3. 기존 객체의 인스턴스 메서드 참조  
Transaction 객체를 할당받은 expensiveTransaction 지역 변수가 있고,  
Transaction 객체에는 getValue 메서드가 있으면,  
expensiveTransaction::getValue라고 표현할 수 있다.  
  
### 생성자 참조
ClassName::new 처럼 클래스와 new 키워드를 사용해 기존 생성자의 레퍼런스를 만들 수 있다.
```java
public class Apple {
    private String color;
    private Integer weight;
    public Apple() {
    }
    public Apple(String color) {
        this.color = color;
    }
    public Apple(Integer weight) {
        this.weight = weight;
    }
    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public Integer getWeight() {
        return weight;
    }
}
```

```java
Supplier<Apple> sup = Apple::new;
Function<Integer, Apple> fun = Apple::new;
Function<String, Apple> fun2 = Apple::new;
BiFunction<String, Integer, Apple> bi = Apple::new;
```
시그니처를 대응시켜 생성자에 접근이 가능하다.  
  
## 7. 람다, 메서드 레퍼런스 정리
- 코드 전달: 함수형 인터페이스를 구현해서 사용
- 익명 클래스 사용: 코드 지저분하다.
- 람다 표현식 사용
- 메서드 참조 사용: 람다 표현식의 인수를 더 깔끔하게 전달할 수 있다.
  - inventory.sort(comparing(Apple::getWeight));

## 8. 람다 표현식을 조합할 수 있는 메서드
디폴트 메서드를 사용한다.  
### Comparator 조합
```java
inventory.sort(
    Comparator.comparing(Apple::getWeight)
    .reversed() // 역정렬
    .thenComparing(Apple::getColor)); // 두 사과 비교 후 무게 같을 때 정렬 법
```
### Predicate 조합
```java
Predicate<Apple> redApple = a -> "RED".equals(a.getColor());

Predicate<Apple> notRedApple = redApple.negate(); // 기존 Predicate를 반전

Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150); // 기존 Predicate에 and를 이용해서 빨강이면서 무거운 사과로 조합

Predicate<Apple> redAndHeavyAppleOrGreen = redApple // 기존 Predicate에 or를 이용해서 빨강이면서 무거운 사과 또는 그냥 녹색사과로 조합
  .and(a -> a.getWeight() > 150)
  .or(a -> "GREEN".equals(a.getColor()));
```

### Function 조합
andThen: 주어진 함수를 먼저 적용한 결과를 다른 함수의 입력으로 전달하는 함수를 반환    
compose: 인수로 주어진 함수를 먼저 실행 한 후에 그 결과를 외부 함수의 인수로 제공  

```java
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.andThen(g); // g(f(x))
int result = h.apply(1); // 4를 반환
```
```java
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.compose(g); // f(g(x))
int result = h.apply(1); // 3을 반환
```

