package dev.limhm.member;

import dev.limhm.domain.Member;
import dev.limhm.domain.Study;
import java.util.Optional;

public interface MemberService {

  Optional<Member> findById(Long memberId);

  void validate(Long memberId);

  void notify(Study study);

  void notify(Member member);
}
