package jpql;

import jpql.domain.data.Address;
import jpql.domain.dto.MemberDTO;
import jpql.domain.entity.Member;
import jpql.domain.entity.Team;

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
            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Team> foundTeams = em.createQuery("select m.team from Member m join m.team t", Team.class).getResultList();
            List<Address> foundAddresses = em.createQuery("select o.address from Order o", Address.class).getResultList();
            List<MemberDTO> foundNameAndAges =
                    em.createQuery("select new jpql.domain.dto.MemberDTO(m.username, m.age) " +
                                    "from Member m", MemberDTO.class)
                            .getResultList();

            for (MemberDTO foundNameAndAge : foundNameAndAges) {
                System.out.println("foundNameAndAge.get = " + foundNameAndAge.getUsername());
                System.out.println("foundNameAndAge.getAge() = " + foundNameAndAge.getAge());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
