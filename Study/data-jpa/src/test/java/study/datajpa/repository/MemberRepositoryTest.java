package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @PersistenceContext
    EntityManager em;

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
        assertThat(foundOptionalMember.orElseThrow()).isEqualTo(memberA);
    }

    @Test
    void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> pagedMembers = memberRepository.findByAge(age, pageRequest);
        Page<MemberDTO> pagedMemberDTOs = pagedMembers.map(m -> new MemberDTO(m.getId(), m.getUsername(), null));

        // then
        List<Member> content = pagedMembers.getContent();
//        long totalElements = pagedMembers.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
//        assertThat(pagedMembers.getTotalElements()).isEqualTo(5);
        assertThat(pagedMembers.getNumber()).isEqualTo(0);
//        assertThat(pagedMembers.getTotalPages()).isEqualTo(2);
        assertThat(pagedMembers.isFirst()).isTrue();
        assertThat(pagedMembers.hasNext()).isTrue();
    }

    @Test
    void bulkUpdate() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberRepository.bulkAgePlus(20);

        // then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMemberLazy() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<Member> foundMembers = memberRepository.findAll();
        for (Member foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember.getUsername());
            System.out.println("foundMember.getTeam().getName() = " + foundMember.getTeam().getName());
        }
    }

    @Test
    void queryHint() {
        // given
        Member member = memberRepository.save(new Member("member1", 10));
        em.flush(); // Query 적용하기
        em.clear(); // Persistence Context 초기화하기

        // when
        Member foundMember = memberRepository.findReadOnlyByUsername(member.getUsername());
        foundMember.setUsername("member2");

        em.flush();
    }

    @Test
    void lock() {
        // given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        // when
        List<Member> foundMembers = memberRepository.findLockByUsername(member1.getUsername());
    }
}