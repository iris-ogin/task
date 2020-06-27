# task

#### 구성
* gradle + spring-boot + hibernate + postgresql

#### 실행방법
* database object생성 
  * support/database/money-splash.sql (database name : moneysplash)
* resources/appllication-dev.properties.tmpl 를 참고하여 resources/appllication-dev.properties 파일 생성
* dev profile로 Application.java 파일 실행

#### 테스트
```
뿌리기 : curl -d "amount=3000&gainerCount=10" -H "X-ROOM-ID: abc" -H "X-USER-ID: 1" -X POST http://localhost:8080/v1/money-gun/splash
받기 : curl -d "token={생성된 토큰}" -H "X-ROOM-ID: abc" -H "X-USER-ID: 2" -X POST http://localhost:8080/v1/money-gun/gain
조회 : curl -d  -H "X-ROOM-ID: abc" -H "X-USER-ID: 1" -X GET http://localhost:8080/v1/money-gun/splash/view?token={생성된 토큰}
```

#### 간략한 전략
#####  뿌리기 생성 시 금액 계산
 * 입력받은  뿌릴 인원 보다 1개 작은 만큼의 리스트는 전체 금액의 절반 보다 작은 random 숫자를 받는 금액으로 한다.
  ```
        long rest = amount;
        long gainAmount = RandomUtils.nextLong(0, rest / 2);
        rest = rest - gainAmount;
  ```
* 마지막은 남은 금액을 넣는다.
* 금액이 random으로 생성되었기 때문에 받기 API에서는 순차적으로 받도록 설계
      
##### 뿌리기와 받기 후 잔고 처리 
 * trigger를 사용하여 처리

  ``` sql 
    tr_when_create_splash : 뿌리기를 생성했을 경우 만든 사람의 잔고를 차감한다.
    fn_when_gain_splash : 받기를 수행한 경우 받은 사람의 잔고를 증가한다. 
  ```
 
#### 고민을 조금 더 해봐야 할 부분
 * 받기 시 lock 에 대한 고민
   *  비관적인 lock을 잡아서 처리 했다면 어땠을지 고민해본다.
  
