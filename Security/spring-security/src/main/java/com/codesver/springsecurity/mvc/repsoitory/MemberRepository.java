package com.codesver.springsecurity.mvc.repsoitory;

import com.codesver.springsecurity.mvc.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUserId(String userId);
}
