package dev.limhm.member;

import dev.limhm.domain.Member;
import java.util.Optional;

public interface MemberService {

  Optional<Member> findById(Long memberId);
}
