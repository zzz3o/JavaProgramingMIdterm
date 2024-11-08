# JavaProgramingMIdterm

자바프로그래밍2 중간고사 프로젝트

![image](https://github.com/user-attachments/assets/6f468194-64f4-4eeb-9c6a-1867cd42b9dc)

## 프로젝트 설명

Swing을 사용하여 기본 사칙 연산을 수행하는 간단한 계산기

## 개발 기간

- 24.10.11 ~ 24.11.1

## 주요 기능

- **기본 산술 연산**: 덧셈, 뺄셈, 곱셈, 나눗셈
- **연속 연산**: 결과 값에 추가로 연산이 가능
- **에러 처리**: 잘못된 수식 또는 0으로 나누기 시 `Error` 표시


## 코드 구성

1. **CalculatorFrame**: GUI 프레임으로 디스플레이와 키패드를 생성합니다.
2. **RoundedButton**: 둥근 모서리 버튼 클래스.
3. **RoundedTextField**: 둥근 모서리의 텍스트 필드로 계산기 디스플레이에 사용됩니다.
4. **ButtonClickListener**: 버튼 클릭 이벤트를 처리하여 입력과 계산 기능을 구현합니다.
5. **calculate(String expression)**: 수식을 파싱하고 계산하는 메서드입니다.


## 사용법

1. 프로젝트를 실행합니다.
2. 숫자와 연산자를 입력한 후 `=` 버튼을 눌러 결과를 확인합니다.
3. 결과 값에서 추가 연산을 계속할 수 있으며, `C` 버튼으로 입력을 초기화할 수 있습니다.


## 요구사항

- **Java JDK** 1.8 이상 (JDK에 포함된 Swing 라이브러리)


## 개발자 정보

- **이름**: Seo Woojin
- **이메일**: zzzeow3@gmail.com
