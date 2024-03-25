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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);


            em.flush();
            em.clear();

            //반환타입이 명확하게 Member.class이니까 TypedQuery
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            //반환타입이 명확하지 않은 m.username, m.age이니까 Query
            Query query1 = em.createQuery("select m.username, m.age from Member m");


            List resultList = query1.getResultList();

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