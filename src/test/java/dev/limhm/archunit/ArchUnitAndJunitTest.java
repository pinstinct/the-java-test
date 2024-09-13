package dev.limhm.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import dev.limhm.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@Nested
@DisplayName("ArchUnit, Junit5 연동")
@AnalyzeClasses(packagesOf = Main.class)
public class ArchUnitAndJunitTest {

  @ArchTest
  ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
      .should().onlyBeAccessed().byClassesThat()
      .resideInAnyPackage("..study..", "..member..", "..domain..", "..start..");

  @ArchTest
  ArchRule memberPackageRule = classes().that().resideInAnyPackage("..member..")
      .should().onlyBeAccessed().byClassesThat()
      .resideInAnyPackage("..study..", "..member..");

  @ArchTest
  ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
      .should().accessClassesThat().resideInAPackage("..study..");

  @ArchTest
  ArchRule freeOfCycles = slices().matching("..dev.limhm.(*)..")
      .should().beFreeOfCycles();
}
