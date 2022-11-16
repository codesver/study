package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void testMember() {
        // given
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        // when
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member foundMember1 = memberRepository.findById(member1.getId()).get();
        Member foundMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(foundMember1).isEqualTo(member1);
        assertThat(foundMember2).isEqualTo(member2);

        // List 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // Count 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // Delete 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(0);
    }


    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member memberA = new Member("AAA", 10);
        Member memberB = new Member("AAA", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void namedQueryTest() {
        Member memberA = new Member("AAA", 10);
        Member memberB = new Member("BBB", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> result = memberRepository.findByUsername("AAA");
        Member foundMember = result.get(0);
        assertThat(foundMember).isEqualTo(memberA);
    }

    @Test
    void testQueryAnnotation() {
        Member memberA = new Member("AAA", 10);
        Member memberB = new Member("BBB", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> foundMembers = memberRepository.findUser("AAA", 10);
        Member foundMember = foundMembers.get(0);
        assertThat(foundMember).isEqualTo(memberA);
    }

    @Test
    void findUsernameList() {
        Member memberA = new Member("AAA", 10);
        Member memberB = new Member("BBB", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<String> usernames = memberRepository.findUsernameList();
        for (String username : usernames) {
            System.out.println("username = " + username);
        }
    }

    @Test
    void findMemberDTO() {
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member memberA = new Member("AAA", 10);
        memberA.setTeam(teamA);
        memberRepository.save(memberA);

        List<MemberDTO> foundMemberDTOs = memberRepository.findMemberDTO();
        for (MemberDTO foundMemberDTO : foundMemberDTOs) {
            System.out.println("foundMemberDTO = " + foundMemberDTO);
        }
    }

    @Test
    void findByNames() {
        Member memberA = new Member("AAA", 10);
        Member memberB = new Member("BBB", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> foundMembers = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
    }

    @Test
    void returnType() {
        Member memberA = new Member("AAA", 10);
        Member memberB = new Member("BBB", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> foundMembers = memberRepository.findMembersByUsername("AAA");
        Member foundMember = memberRepository.findMemberByUsername("AAA");
        Optional<Member> foundOptionalMember = memberRepository.findOptionalMemberByUsername("AAA");

        assertThat(foundMembers.get(0)).isEqualTo(memberA);
        assertThat(foundMember).isEqualTo(memberA);
        assertThat(foundOptionalMember.get()).isEqualTo(memberA);
    }
}