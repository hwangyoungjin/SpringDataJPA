package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;


    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

    public long count(){
        return em.createQuery("select count (m) from Member m",Long.class).getSingleResult();
    }

    public List<Member> findByUsernameAndAgeGraterThan(String username, int age){
        return em.createQuery("select m from Member m where m.username = :name and m.age > :age")
                .setParameter("name",username)
                .setParameter("age",age)
                .getResultList();
    }
}
