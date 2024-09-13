# 성능 테스트

## [HTTPie](https://httpie.io/cli)

httpie를 이용해 요청하기

```shell
http POST http://localhost:8080/study limit=10 name="test"

http http://localhost:8080/study/1
```


## [ab - 아파치 웹서버 성능검사 도구](https://httpd.apache.org/docs/current/programs/ab.html)

```shell
ab -n 100 -c 10 http://localhost:8080/study
```

## [JMeter](https://jmeter.apache.org/download_jmeter.cgi)

### 설치하기

Binaries에서 zip 파일을 다운받은 후 압축을 푼다.

```shell
# ~/apache-jmeter-5.6.3/bin 경로로 이동 후
# 실행
./jmeter
```

### 성능 테스트

테스트하고자 하는 서버와 JMeter가 실행되는 환경이 달라야 제대로 된 테스트를 할 수 있다.

- Thread Group: 한 쓰레드 당 유저 한명
  - Number of Threads (users): 쓰레드 개수
    - 설정 값: 10
  - Ramp-up period (seconds): 쓰레드 개수를 만드는데 소요할 시간 
    - 설정 값: 10
  - Loop Count
    - 설정 값: 1
  - 위와 같이 설정하면, 10초 안에 10개의 스레드를 생성해 1번 요청한다.
- Sampler: 어떤 유저가 해야 하는 액션
  - 여러 종류의 샘플러가 있지만 그 중에 HTTP Request 샘플러 추가
  - 여러 샘플러를 순차적으로 등록하는 것도 가능
- Listener: 응답을 받았을 할 일 (리포팅, 검증, 그래프 그리기 등)
  - View Results Tree
  - View Results in Table
  - Summary Report 
  - Aggregate Report 
  - Response Time Graph 
  - Graph Results 
  - ...
- Configuration: Sampler 또는 Listener가 사용할 설정 값 (쿠키, JDBC 커넥션 등)
- Assertion: 응답이 성공적인지 확인하는 방법 (응답 코드, 본문 내용 등)
  - 응답 코드 확인
  - 응답 본문 확인

```shell
jmeter -n -t 설정파일 -l 리포트파일 
```
