# Modern-Java-in-Action
모던 자바 인 액션

자바8은 간결한 코드, 멀티코어 프로세서의 쉬운 활용이라는 두 가지 요구사항을 기반으로 한다.  
일단 자바 8에서 제공하는 새로운 기술이 어떤 것인지 확인하자.  
- 스트림 API
- 메서드에 코드를 전달하는 기법
- 인터페이스의 디폴트 메서드
  
  
## 1. 자바 8의 3가지 프로그래밍 개념
### 스트림 처리
스트림이란? 한 번에 한 개씩 만들어지는 연속적인 데이터 항목들의 모임이다.   

스트림 API의 핵심은 기존에는 한 번에 한 항목을 처리했지만 이제 자바 8에서는 우리가 하려는 작업을 고수준으로 추상화해서 일련의 스트림으로 만들어 처리할 수 있다는 것이다.  

또한 입력 부분을 여러 CPU 코어에 쉽게 할당할 수 있다는 부가적인 이득도 얻을 수 있다.  
  
스레드라는 복잡한 작업을 사용하지 않으면서도 **공짜**로 병렬성을 얻을 수 있다.  

### 동작 파라미터화로 메서드에 코드 전달하기
코드 일부를 API로 전달하는 기능: 함수형 프로그래밍  

### 병렬성과 공유 가변 데이터
스트림 메서드로 전달하는 코드는 다른 코드와 동시에 실행되도 안전하게 실행이 된다.

공유 가변데이터에 접근하지 않기 때문이다 => 순수(pure) 함수, 부작용없는(side-effect-free) 함수, 상태없는(stateless) 함수 : 함수형 프로그래밍

자바 역시 하드웨어나 프로그래머의 기대의 변화에 부응할 수 있도록 변화하였다.  
  
## 2. 자바 함수
### 함수
자바의 함수: 부작용을 일으키지 않는 함수  

자바8로 인해 이급 시민 => 일급 시민 변화가 일어났다.  

일급(first-class) 시민 : 조작이 가능한 것, 바꿀 수 있는 값 : 기본값(int, double), 객체(String, Integer, HashMap 등)  
이급 시민 : 전달 할 수 없는 구조체, 바꿀 수 없는 값 : 메서드, 클래스  

### 메서드 참조(method reference)
디렉터리에서 모든 숨겨진 파일을 필터링한다고 가정하자.  
File클래스는 isHidden 메서드를 제공한다.  
isHidden은 File 클래스를 인수로 받아 boolean을 반환하는 함수다.  
다음 에졔처럼 FileFilter 객체 내부에 위치한 isHidden의 결과를 File.listFiles 메서드로 전달하는 방법으로 숨겨진 파일을 필터링할 수 있다.  
````java
File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
    @Override
    public boolean accept(File pathname) {
        return false;
        }
    });
````
단 세 행의 코드지만 각 행이 무슨 작업을 하는지 투명하지 않다.  
자바 8에서는 다음처럼 코드를 구현할 수 있다.  

````java
File[] hiddenFiles = new File(".").listFiles(File::isHidden);
````

**자바8의메서드 참조 ::**를 이용해서 listFiles에 직접 전달할 수 있다.  
`자바 8에서는 더 이상 메서드가 이급값이 아닌 일급값이라는 것이다.`  
  
### 람다: 익명 함수
자바 8에서는 메서드를 일급값으로 취급할 뿐 아니라 **람다**를 포함하여 함수도 값으로 취급할 수 있다.  
  

직접 메서드를 정의할 수도 있지만, 이용할 수 있는 있는 편리한 클래스나 메서드가 없을 때 새로운 람다 문법을 이용하면 코드를 더 간결하게 구현할 수 있다.  

람다 문법 형식으로 구현된 프로그램 == 함수형 프로그래밍 == 함수를 일급값으로 넘겨주는 프로그램을 구현한다.  
  
## 3. 스트림
- 컬렉션
  - for-each 루프를 이용해서 각 요소를 반복하면서 작업을 수행
  - 외부 반복
- **스트림**
  - 라이브러리 내부에서 모든 데이터가 처리된다.
  - 내부 반복
  - 여러 CPU 코어에 작업을 각각 할당해서 처리시간을 줄일 수 있다.

- 주어진 조건에 따라 데이터를 필터링(filtering)
- 데이터를 추출(extracting)
- 데이터 그룹화(gropuing)
- 동작을 쉽게 병렬화 가능  
  포킹(forking) => 포킹된 리스트 처리 => 처리된 결과를 합침

## 4. 디폴트 메서드
기존에는 인터페이스를 바꿔야 하는 상황에서는 인터페이스를 구현하는 모든 클래스의 구현을 바꿔야 했다.  
디폴트 메서드를 이용하면 기존의 코드를 건드리지 않고도 원래의 인터페이스 설계를 자유롭게 확장할 수 있다.    
  
예를 들어 자바 8에서는 List에 직접 sort 메서드를 호출할 수 있다.  
이는 자바 8의 List 인터페이스에 다음과 같은 디폴트 메서드 정의가 추가되었기 때문이다.  
````java
default void sort(Comparator<? super E> c) {
    Collections.sort(this, c);
}
````
따라서 자바 8부터는 디폴트 sort를 구현하지 않아도 된다.  

## 그 외
- Optional<T>: 값을 갖거나 갖지 않을 수 있는 컨테이너 객체
- 구조잭(structural) 패턴 매칭 기법





