package codesver.hellospring.repository;

import codesver.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);  // 구현체를 자동으로 구현하여 등록
    // -> select m from Member m where m.name = ?
}
