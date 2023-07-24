package me.jaden.ch2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        Member member = createMember("memberC", "회원1");
        member.setUsername("회원명 변경");
        mergeMember(member);
    }

    private static Member createMember(String id, String userName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(userName);

        em.persist(member);
        tx.commit();

        em.close();
        return member;
    }

    private static void mergeMember(Member member) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Member mergeMember = em.merge(member);
        mergeMember.setUsername("zz");
        tx.commit();

        System.out.println("member = " + member.getUsername());
        System.out.println("mergeMember = " + mergeMember.getUsername());
        System.out.println("contain member? = " + em.contains(member));
        System.out.println("contain mergeMember ? = " + em.contains(mergeMember));

        em.close();
    }
}
