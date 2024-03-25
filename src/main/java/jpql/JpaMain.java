package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //비영속
        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("teamA");
            member1.setAge(10);
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();
            //연관관계 없는 엔티티 외부 조인(막조인 ㅋㅋ) on절에만 그 조건을 잘 적어두자
            String query = "select m From Member m left join Team t on m.username = t.name";
            List<Member> result = em.createQuery(query, Member.class).getResultList();
            for (Member member : result) {
                System.out.println("member.getTeam() = " + member.getTeam().getName());
                System.out.println("member.getUsername() = " + member.getUsername());
            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}