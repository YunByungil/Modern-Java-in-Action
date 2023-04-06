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
  

