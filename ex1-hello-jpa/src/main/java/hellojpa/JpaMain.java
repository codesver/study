package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Child childA = new Child();
            Child childB = new Child();

            Parent parent = new Parent();
            parent.addChild(childA);
            parent.addChild(childB);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent foundParent = em.find(Parent.class, parent.getId());
            em.remove(foundParent);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member.getUsername() = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team.getName() = " + team.getName());
    }
}
