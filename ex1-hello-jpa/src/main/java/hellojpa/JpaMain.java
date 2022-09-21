package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member = new Member();
            member.setUsername("member");
            member.changeTeam(teamA);
            em.persist(member);

            Member foundMember = em.find(Member.class, member.getId());
            foundMember.changeTeam(teamB);

            List<Member> membersOfTeamA = teamA.getMembers();
            List<Member> membersOfTeamB = teamB.getMembers();
            for (Member memberA : membersOfTeamA) {
                System.out.println("memberA = " + memberA.getUsername());
            }
            for (Member memberB : membersOfTeamB) {
                System.out.println("memberB = " + memberB.getUsername());
            }
            System.out.println("foundMember.getTeam() = " + foundMember.getTeam().getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
