package dev.limhm.start;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

  /**
   * 반드시 static 메서드 private 불가, default 가능함. return 타입이 있으면 안됨
   */
  @BeforeAll
  static void beforeAll() {
    System.out.println("before all");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("after all");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("before each");
  }

  @AfterEach
  void afterEach() {
    System.out.println("after each");
  }

  @Test
  @DisplayName("스터디 만들기 \uD83D\uDE31")
  void create_new_study() {
    Study study = new Study(1);
    System.out.println("create");
    Assertions.assertNotNull(study);
  }

  @Test
  @Disabled
  void create1() {
    System.out.println("create 1");
  }

  @Test
  void create_new_study_again() {
    System.out.println("create 1");
  }

  @Test
  @DisplayName("조건이 참인지 확인")
  void test1() {
    Study study = new Study(2);
    Assertions.assertTrue(study.getLimit() > 0,
        "스터디 최대 참석 인원은 0보다 커야 한다.");
  }

  @Test
  @DisplayName("여러개의 assert 문이 있는 경우, 모두 구문 확인")
  void test2() {
    Study study = new Study(3);

    // 첫 번째 assert 문이 깨지면 뒤에 테스틑 실행되지 않지만,
    // assertAll() 을 사용하면, 모두 실행해 결과 확인 가능
    Assertions.assertAll(
        () -> Assertions.assertTrue(study.getLimit() < 0),
        () -> Assertions.assertNull(study),
        () -> Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(),
            () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.")
    );
  }

  @Test
  @DisplayName("예외 발생 확인")
  void test3() {
    IllegalArgumentException exception = Assertions.assertThrows(
        IllegalArgumentException.class, () -> new Study(-10));
    String message = exception.getMessage();
    Assertions.assertEquals("limit은 0보다 커야 한다.", message);
  }

  @Nested
  @DisplayName("특정 시간 안에 실행이 완료되는지 확인")
  class TimeoutTest {

    @Test
    @DisplayName("모든 작업이 완료되야 테스트 종료")
    void test1() {
      Assertions.assertTimeout(Duration.ofMillis(100), () -> {
        new Study(10);
        Thread.sleep(300);
      });
    }

    @Test
    @DisplayName("테스트 설정 시간이 지나면 바로 종료")
    void test2() {
      /* *
       * 주의해서 사용할 것
       * 코드 블록을 별도의 스레드에서 실행하기 때문이다.
       *
       * ThreadLocal 을 사용하는 코드가 있다면, 예상치 못한 결과 발생
       * 예를 들어 spring transaction 처리는 ThreadLocal 이 기본 전략인데,
       * ThreadLocal 은 공유가 안된다. 때문에 테스트 시, 제대로 동작하지 않아 롤백이 안될 수 있다.
       * */
      Assertions.assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
        new Study(10);
        Thread.sleep(300);
      });
    }
  }

  @Nested
  @DisplayName("실제 값이 기대한 값과 같은지 확인")
  class EqualsTest {

    @Test
    @DisplayName("assertEquals(expected, actual, message)")
    void test1() {
      Study study = new Study(1);
      Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(),
          "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.");
    }

    /**
     * test1과 test2의 차이 message 대신 messageSupplier 를 넘겨주면, 람다식을 이용할 수 있다. 장점은 문자열에 변수가 있을 때, 연산을 최대한
     * 지연해준다. 테스트가 실패하는 경우만 연산하게 된다. 성능에 유리하다. (위에 message 는 테스트 실패/성공 상관없이 무조건 연산)
     */
    @Test
    @DisplayName("assertEquals(expected, actual, messageSupplier)")
    void test2() {
      Study study = new Study(1);
      Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(),
          () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다.");
    }
  }

  @Nested
  @DisplayName("조건에 따라 테스트 실행하기")
  class ConditionTest {

    @Test
    @DisplayName("assumeTrue()")
    void test1() {
      String env = System.getenv("TEST_ENV");
      System.out.println("=== " + env);
      assumeTrue("LOCAL".equalsIgnoreCase(env));

      // TEST_ENV 가 LOCAL 이 아니기 때문에, 실행 안됨
      Study study = new Study(1);
      Assertions.assertTrue(study.getLimit() > 0);
    }

    @Test
    @DisplayName("assumingThat")
    void test2() {
      String env = System.getenv("TEST_ENV");
      assumingThat("LOCAL".equals(env), () -> {
        // 로컬 환경에서 테스트 할 코드
      });
      assumingThat("PRD".equals(env), () -> {
        // 운영 환경에서 테스트 할 코드
      });
    }

    @Test
    @DisplayName("")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void test5() {
      System.out.println("환경변수 TEST_ENV가 LOCAL이면 테스트 실행");
    }

    @Test
    @DisplayName("@DisabledOnOs")
    @DisabledOnOs({OS.MAC, OS.LINUX})
    void test3() {
      System.out.println("mac에서 테스트 실행 안함");
    }

    @Test
    @DisplayName("@EnabledOnOs")
    @EnabledOnOs(OS.MAC)
    void test4() {
      System.out.println("mac에서 테스트 실행");
    }
  }
}