package dev.limhm.study;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.limhm.domain.Member;
import dev.limhm.domain.Study;
import dev.limhm.member.MemberService;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class StudyServiceTest {

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
}