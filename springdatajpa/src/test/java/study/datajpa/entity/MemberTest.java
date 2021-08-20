package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",10,teamA);
        Member member3 = new Member("member3",10,teamB);
        Member member4 = new Member("member4",10,teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //db에 강제로 저장
        em.flush();
        //영속성 컨텍스트 비우기
        em.clear();

        //when
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        //that
        for (Member m : members){
            System.out.println("member = "+m);
            System.out.println(" -> member.team = "+m.getTeam());
        }
    }
}