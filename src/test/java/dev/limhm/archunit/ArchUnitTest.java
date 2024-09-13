package dev.limhm.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("ArchUnit 테스트")
public class ArchUnitTest {

  @Nested
  @DisplayName("ArchUnit 패키지 의존성 확인하기")
  class packageDependencyTests {

    // 자바 클래스 가져오기
    JavaClasses classes = new ClassFileImporter().importPackages("dev.limhm");

    @Test
    @DisplayName("..domain.. 패키지에 있는 클래스는 ..study.., ..member.., ..domain에서 참조 가능")
    void test1() {
      ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
          .should().onlyBeAccessed().byClassesThat()
          .resideInAnyPackage("..study..", "..member..", "..domain..", "..start..");
      domainPackageRule.check(classes);
    }

    @Test
    @DisplayName("..member.. 패키지에 있는 클래스는 ..study..와 ..member..에서만 참조 가능")
    void test2() {
      ArchRule memberPackageRule = classes().that().resideInAnyPackage("..member..")
          .should().onlyBeAccessed().byClassesThat()
          .resideInAnyPackage("..study..", "..member..");
      memberPackageRule.check(classes);
    }

    @Test
    @DisplayName("(반대로) ..domain.. 패키지는 ..member.. 패키지를 참조하지 못한다")
    void test3() {
      ArchRule memberPackageRule = noClasses().that().resideInAPackage("..domain..")
          .should().accessClassesThat().resideInAnyPackage("..member..");
      memberPackageRule.check(classes);
    }

    @Test
    @DisplayName("..study.. 패키지에 있는 클래스는 ..study.. 에서만 참조 가능")
    void test4() {
      ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
          .should().accessClassesThat().resideInAPackage("..study..");
      studyPackageRule.check(classes);
    }

    @Test
    @DisplayName("순환 참조 없어야 한다")
    void test5() {
      ArchRule freeOfCycles = slices().matching("..dev.limhm.(*)..")
          .should().beFreeOfCycles();
      freeOfCycles.check(classes);
    }
  }
}
