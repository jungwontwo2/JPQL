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
            for (int i = 0; i < 100; i++) {
                Member member1 = new Member();
                member1.setAge(i);
                em.persist(member1);
            }



            em.flush();
            em.clear();
            List<Member> result = em.createQuery("select m From Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
            System.out.println("result.size() = " + result.size());
            for (Member member : result) {
                System.out.println("member = " + member);
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