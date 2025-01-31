package dev.limhm.study;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import dev.limhm.domain.Member;
import dev.limhm.domain.Study;
import dev.limhm.domain.StudyStatus;
import dev.limhm.member.MemberService;
import dev.limhm.study.StudyServiceTest.ContainerPropertyInitializer;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = ContainerPropertyInitializer.class)
class StudyServiceTest {

  // lombok 사용하면, @Slf4j 애너테이션으로 대체 가능
  static Logger LOGGER = LoggerFactory.getLogger(StudyServiceTest.class);

  /**
   * static 으로 생성해야 여러 테스트에서 공유해서 사용 가능하다. 그렇지 않으면 테스트 메소드마다 컨테이너를 새로 띄우기 때문에 느려진다.
   */
  @Container
  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
      .withDatabaseName("studytest");

  @Mock
  StudyRepository studyRepository;

  @Autowired
  Environment environment;

  @Value("${container.port}") int port;

  @BeforeAll
  static void beforeAll() {
    // 컨테이너 내의 로그 스트리밍
    Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(LOGGER);
    postgreSQLContainer.followOutput(logConsumer);
  }

  @BeforeEach
  void setup() {
    System.out.println("===*");
    System.out.println(environment.getProperty("container.port"));
    // 또는
    System.out.println(port);
    studyRepository.deleteAll();
  }

  /**
   * ApplicationContextInitializer: 스프링 ApplicationContext를 프로그래밍으로 초기화 할 때 사용할 수 있는 콜백 인터페이스로, 특정
   * 프로파일을 활성화 하거나, 프로퍼티 소스를 추가하는 등의 작업을 할 수 있다.
   */
  static class ContainerPropertyInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
      // 테스트용 프로퍼티 소스를 정의할 때 사용
      TestPropertyValues.of("container.port=" + postgreSQLContainer.getMappedPort(5432))
          .applyTo(context.getEnvironment());
    }
  }

  @Nested
  @DisplayName("Mock 객체 만들기")
  @Order(1)
  @ExtendWith(MockitoExtension.class)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  class MockTest {

    @Test
    @DisplayName("인라인으로 인터페이스를 구현해 테스트")
    @Order(1)
    void implementInterface() {
      MemberService memberService = new MemberService() {
        @Override
        public Optional<Member> findById(Long memberId) {
          return Optional.empty();
        }

        @Override
        public void validate(Long memberId) {

        }

        @Override
        public void notify(Study study) {

        }

        @Override
        public void notify(Member member) {

        }
      };

      StudyRepository studyRepository = new StudyRepository() {
        @Override
        public void flush() {

        }

        @Override
        public <S extends Study> S saveAndFlush(S entity) {
          return null;
        }

        @Override
        public <S extends Study> List<S> saveAllAndFlush(Iterable<S> entities) {
          return List.of();
        }

        @Override
        public void deleteAllInBatch(Iterable<Study> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> longs) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Study getOne(Long aLong) {
          return null;
        }

        @Override
        public Study getById(Long aLong) {
          return null;
        }

        @Override
        public Study getReferenceById(Long aLong) {
          return null;
        }

        @Override
        public <S extends Study> List<S> findAll(Example<S> example) {
          return List.of();
        }

        @Override
        public <S extends Study> List<S> findAll(Example<S> example, Sort sort) {
          return List.of();
        }

        @Override
        public <S extends Study> List<S> saveAll(Iterable<S> entities) {
          return List.of();
        }

        @Override
        public List<Study> findAll() {
          return List.of();
        }

        @Override
        public List<Study> findAllById(Iterable<Long> longs) {
          return List.of();
        }

        @Override
        public <S extends Study> S save(S entity) {
          return null;
        }

        @Override
        public Optional<Study> findById(Long aLong) {
          return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
          return false;
        }

        @Override
        public long count() {
          return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Study entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends Study> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<Study> findAll(Sort sort) {
          return List.of();
        }

        @Override
        public Page<Study> findAll(Pageable pageable) {
          return null;
        }

        @Override
        public <S extends Study> Optional<S> findOne(Example<S> example) {
          return Optional.empty();
        }

        @Override
        public <S extends Study> Page<S> findAll(Example<S> example, Pageable pageable) {
          return null;
        }

        @Override
        public <S extends Study> long count(Example<S> example) {
          return 0;
        }

        @Override
        public <S extends Study> boolean exists(Example<S> example) {
          return false;
        }

        @Override
        public <S extends Study, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
          return null;
        }
      };

      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);
    }

    /* 위의 코드는 구현해야 할 코드 양이 많아지기 때문에 Mock 을 사용 */

    @Test
    @DisplayName("Mockito.mock() 메서드로 만드는 방법")
    @Order(2)
    void usingMock() {
      MemberService memberService = mock(MemberService.class);
      StudyRepository studyRepository = mock(StudyRepository.class);
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);
    }

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    @DisplayName("Mock 애너테이션으로 만드는 방법")
    @Order(3)
    void usingAnnotation() {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);
    }

    @Test
    @DisplayName("Mock 애너테이션을 메소드에서 사용하는 방법")
    @Order(4)
    void test1(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);
    }

  }

  @Nested
  @DisplayName("Mock 객체 Stubbing")
  @Order(2)
  @ExtendWith(MockitoExtension.class)
  class MockStubbing {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Nested
    @DisplayName("Mock 객체의 행동")
    class MockAction {

      @Test
      @DisplayName("Optional 타입은 Optional.empty 리턴")
      void test() {
        Optional<Member> optional = memberService.findById(1L);
        System.out.println(optional);
        assertNotNull(optional);
      }

      @Test
      @DisplayName("void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않는다.")
      void test1() {
        memberService.validate(2L);
      }
    }

    @Nested
    @DisplayName("Mock 객체 조작")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MockOperation {

      @Test
      @DisplayName("특정한 매개변수를 받은 경우 특정한 값을 리턴")
      @Order(1)
      void test() {
        // 리턴받을 객체
        Member member = new Member();
        member.setId(1L);
        member.setEmail("abc@xyz.com");

        // stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Optional<Member> result = memberService.findById(1L);
        System.out.println(result + ", id: " + result.get().getId());
        assertEquals("abc@xyz.com", result.get().getEmail());
      }

      @Test
      @DisplayName("다른 매개변수를 호출하면 동작하지 않는다")
      @Order(2)
      void test1() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("abc@xyz.com");

        // 위에서 리턴받을 객체를 1L로 정의했는데, 2L 매개변수를 주면 리턴 값을 받을 수 없다.
        when(memberService.findById(2L)).thenReturn(Optional.of(member));

        Optional<Member> result = memberService.findById(1L);
        System.out.println(result.get().getId());
        assertNotNull(result);
      }

      /**
       * @see: <a
       * href="https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#3">argument
       * matchers</a>
       */
      @Test
      @DisplayName("매개변수를 범용적으로 작성하는 방법(Argument matchers)")
      @Order(3)
      void test2() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("abc@xyz.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("abc@xyz.com", memberService.findById(1L).get().getEmail());
        assertEquals("abc@xyz.com", memberService.findById(2L).get().getEmail());
      }

      @Test
      @DisplayName("특정한 매개변수를 받은 경우 예외를 던지는 방법")
      @Order(4)
      void test3() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("abc@xyz.com");

        when(memberService.findById(1L)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> memberService.findById(1L));
      }

      @Test
      @DisplayName("void 메소드의 예외를 던지는 방법")
      @Order(5)
      void test4() {
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
          memberService.validate(1L);
        });

        memberService.validate(2L);
      }

      @Test
      @DisplayName("메소드가 동일한 매개변수로 여러번 호출될 될 때 각기 다르게 행동하도록 조작")
      @Order(6)
      void test5() {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("abc@xyz.com");

        when(memberService.findById(any()))
            .thenReturn(Optional.of(member))  // 첫번째
            .thenThrow(new RuntimeException())  // 두번째
            .thenReturn(Optional.empty());  // 세번째

        // 첫번째
        Optional<Member> result = memberService.findById(1L);
        assertEquals("abc@xyz.com", result.get().getEmail());

        // 두번째
        assertThrows(RuntimeException.class, () -> {
          memberService.findById(2L);
        });

        // 세번째
        assertEquals(Optional.empty(), memberService.findById(3L));
      }
    }
  }

  @Nested
  @DisplayName("Mock 객체 Stubbing 연습 문제")
  @Order(3)
  @ExtendWith(MockitoExtension.class)
  class StubbingTest {

    @Test
    @DisplayName("연습문제")
    void test(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);

      Member member = new Member();
      member.setId(1L);
      member.setEmail("abc@xyz.com");

      Study study = new Study(10, "test");

      // memberService 객체에 findById 메소드를 1L 값으로 호출하면 member 객체를 리턴하도록 stubbing
      when(memberService.findById(1L)).thenReturn(Optional.of(member));

      // studyRepository 객체데 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 stubbing
      when(studyRepository.save(study)).thenReturn(study);

      studyService.createNewStudy(1L, study);
      assertNotNull(study.getOwner());
      assertEquals(member, study.getOwner());
    }
  }

  @Nested
  @DisplayName("Mock 객체 확인")
  @Order(4)
  @ExtendWith(MockitoExtension.class)
  class MockObject {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    static Member getMember() {
      Member member = new Member();
      member.setId(1L);
      member.setEmail("abc@xyz.com");
      return member;
    }

    static Study getStudy() {
      return new Study(10, "테스트");
    }

    /**
     * @see: <a
     * href="https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification">verify</a>
     */
    @Test
    @DisplayName("특정 메소드가 특정 매개변수로 몇번 호출 됐는지 확인하기")
    void test() {
      StudyService studyService = new StudyService(memberService, studyRepository);
      Member member = getMember();
      Study study = getStudy();

      when(memberService.findById(1L)).thenReturn(Optional.of(member));
      when(studyRepository.save(study)).thenReturn(study);

      studyService.createNewStudy(1L, study);
      assertEquals(member, study.getOwner());

      // memberService 에서 study 라는 매개변수를 가지고 1번 호출 됐는지 검증
      verify(memberService, times(1)).notify(study);
      // validate 는 호출 안 됐는지 검증
      verify(memberService, never()).validate(any());
    }

    @Test
    @DisplayName("어떤 순서로 호출됐는지 확인하기")
    void test1() {
      StudyService studyService = new StudyService(memberService, studyRepository);
      Member member = getMember();
      Study study = getStudy();

      when(memberService.findById(1L)).thenReturn(Optional.of(member));
      when(studyRepository.save(study)).thenReturn(study);

      studyService.createNewStudy(1L, study);
      assertEquals(member, study.getOwner());

      // notify(study) 호출 후, notify(member) 가 호출 됐는지 검증
      InOrder inOrder = Mockito.inOrder(memberService);
      inOrder.verify(memberService).notify(study);
      inOrder.verify(memberService).notify(member);

      verifyNoMoreInteractions(memberService);
    }
  }

  @Nested
  @DisplayName("BDD 스타일 Mockito API")
  @Order(5)
  @ExtendWith(MockitoExtension.class)
  class BddStyle {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    @Test
    @DisplayName("when 대신 given / verify 대신 then 사용하기")
    void test() {
      // given
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);

      Member member = new Member();
      member.setId(1L);

      Study study = new Study(10, "테스트");

      // when() 대신 given() 사용
      given(memberService.findById(1L)).willReturn(Optional.of(member));
      given(studyRepository.save(study)).willReturn(study);

      // when
      studyService.createNewStudy(1L, study);

      // then
      assertEquals(member, study.getOwner());
      // verify() 대신 then() 사용
      then(memberService).should(times(1)).notify(study);
      then(memberService).should(times(1)).notify(member);
      then(memberService).shouldHaveNoMoreInteractions();
    }
  }

  @Nested
  @DisplayName("Mockito 연습 문제")
  @Order(6)
  @ExtendWith(MockitoExtension.class)
  class MockitoPractice {

    @Test
    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다")
    void openTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
      // given
      StudyService studyService = new StudyService(memberService, studyRepository);
      Study study = new Study(10, "더 자바, 테스트");
      assertNull(study.getOpenedDateTime());

      // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기
      given(studyRepository.save(study)).willReturn(study);

      // when
      studyService.opentStudy(study);

      // then
      // TODO study의 status가 OPENED로 변경됐는지 확인
      assertEquals(StudyStatus.OPENED, study.getStatus());
      // TODO study의 openedDataTime이 null이 아닌지 확인
      assertNotNull(study.getOpenedDateTime());
      // TODO memberService의 notify(study)가 호출 됐는지 확인
      then(memberService).should().notify(study);
    }
  }
}