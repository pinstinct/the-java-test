package dev.limhm.start;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
  void create() {
    Study study = new Study();
    System.out.println("create");
    Assertions.assertNotNull(study);
  }

  @Test
  @Disabled
  void create1() {
    System.out.println("create 1");
  }

}