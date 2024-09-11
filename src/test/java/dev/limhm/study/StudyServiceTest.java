package dev.limhm.study;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import dev.limhm.domain.Member;
import dev.limhm.domain.Study;
import dev.limhm.member.MemberService;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

class StudyServiceTest {

  @Nested
  @DisplayName("Mock 객체 만들기")
  @ExtendWith(MockitoExtension.class)
  class MockTest {

    @Test
    @DisplayName("인라인으로 인터페이스를 구현해 테스트")
    void implementInterface() {
      MemberService memberService = new MemberService() {
        @Override
        public Optional<Member> findById(Long memberId) {
          return Optional.empty();
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
    void usingAnnotation() {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);
    }

    @Test
    @DisplayName("Mock 애너테이션을 메소드에서 사용하는 방법")
    void test1(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);
    }

  }
}