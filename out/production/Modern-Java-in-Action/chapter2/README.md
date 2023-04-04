## 동적 파라미터화
아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미한다.
- 리스트의 모든 요소에 대해서 '어떤 동작'을 수행할 수 있음
- 리스트 관련 작업을 끝낸 다음에 '어떤 다른 동작'을 수행할 수 있음
- 에러가 발생하면 '정해진 어떤 다른 동작'을 수행할 수 있음

## 1. 변화하는 요구사항에 대응하기
기존의 농장 재고목록 애플리케이션에 리스트에 녹색 사과만 필터링하는 기능을 추가한다고 가정하자.  

사과 색을 정의하는 Color num이 존재한다고 가정하자.    
### 첫 번째 시도: 녹색 사과 필터링
````java
    enum Color { RED, GREEN }
    
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>(); // 사과 누적 리스트
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) { // 녹색 사과만 선택
                result.add(apple);
            }
        }
        return result;
    }  
````
녹색 사과 말고 `빨간` 사과도 필터링하고 싶어졌다.  
어떻게 고쳐야 할까?  
  
메서드를 복하새 ㅓfilterRedApples라는 새로운 메서드를 만들고,  
if문의 조건을 빨간 사과로 바꾸는 방법을 선택할 수 있다.  
더 좋은 방법은?  
`거의 비슷한 코드가 반복 존재한다면 그 코드를 추상화한다.`  
  
### 두 번째 시도: 색을 파라미터화
어떻게 해야 코드를 반복 사용하지 않고 filterRedApples를 구현할 수 있을까?  
````java
public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor() == color) {
                result.add(apple);
            }
        }
        return result;
    }
````  
다음처럼 구현한 메서드를 호출할 수 있다.  
````java
List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
List<Apple> redApples = filterApplesByColor(inventory, RED);
````

색 이외에도 가벼운 사과와 무거운 사과로 구분할 수 있게 만들어보자.  
````java
public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }
````
코드를 자세히 보면 목록을 검색하고, 각 사과에 필터링 조건을 적용하는 부분의 코드가 색 필터링 코드와 대부분 중복된다.  
`DRY(don't repeat yourself): 같은 것을 반복하지 말자.`  
  
### 세 번째 시도: 가능한 모든 속성으로 필터링



  