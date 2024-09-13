package dev.limhm.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import dev.limhm.Main;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.DisplayName;

@DisplayName("ArchUnit 클래스 의존성 확인하기")
@AnalyzeClasses(packagesOf = Main.class)
class ArchClassTest {

  /**
   * Controller 는 Service 와 Repository 로 끝나는 클래스를 참조할 수 있다.
   */
  @ArchTest
  ArchRule controllerClassRule = classes().that().haveSimpleNameEndingWith("Controller")
      .should().accessClassesThat().haveSimpleNameEndingWith("Service")
      .orShould().accessClassesThat().haveSimpleNameEndingWith("Repository");

  /**
   * Repository 는 Service 나 Controller 를 참조하면 안된다.
   */
  @ArchTest
  ArchRule repositoryClassRule = noClasses().that().haveSimpleNameEndingWith("Repository")
      .should().accessClassesThat().haveSimpleNameEndingWith("Service")
      .orShould().accessClassesThat().haveSimpleNameEndingWith("Controller");

  /**
   * Study* 로 시작하는 클래스는 ..study.. 패키지에 있어야 한다.
   */
  @ArchTest
  ArchRule studyClassesRule = classes().that().haveSimpleNameStartingWith("Study")
      .and().areNotEnums()
      .and().areNotAnnotatedWith(Entity.class)
      .should().resideInAnyPackage("..study..", "..start..");
}
