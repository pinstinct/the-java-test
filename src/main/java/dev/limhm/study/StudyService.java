package dev.limhm.study;

import dev.limhm.domain.Member;
import dev.limhm.domain.Study;
import dev.limhm.member.MemberService;

public class StudyService {

  private final MemberService memberService;
  private final StudyRepository repository;


  public StudyService(MemberService memberService, StudyRepository studyRepository) {
    assert memberService != null;
    assert studyRepository != null;
    this.memberService = memberService;
    this.repository = studyRepository;
  }

  public Study createNewStudy(Long memberId, Study study) {
    Member member = memberService.findById(memberId)
        .orElseThrow(
            () -> new IllegalArgumentException("Member doesn't exist for id: " + memberId));
    study.setOwner(member);
    Study newStudy = repository.save(study);
    memberService.notify(newStudy);
    memberService.notify(member);
    return newStudy;
  }
}
