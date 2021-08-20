package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {
    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        //given
        Member member = new Member("memberA");
        Member saveMember = memberJpaRepository.save(member);

        //when
        Member findMember = memberJpaRepository.find(saveMember.getId());

        //that
        Assertions.assertEquals(findMember.getId(),member.getId());
        Assertions.assertEquals(findMember.getUsername(),member.getUsername());
        Assertions.assertEquals(findMember, member);
    }

    @Test
    public void basicCRUD(){
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //when
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        //that

        //단건 조회
        Assertions.assertEquals(findMember1,member1);
        Assertions.assertEquals(findMember2,member2);
        //리스트 조회
        List<Member> all = memberJpaRepository.findAll();
        Assertions.assertEquals(all.size(), 2);
        //카운트조회
        long count = memberJpaRepository.count();
        Assertions.assertEquals(count, 2);
        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        //카운트조회
        count = memberJpaRepository.count();
        Assertions.assertEquals(count, 0);
    }

    @Test
    public void usernameAndAge(){
        //given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        //when
        List<Member> members = memberJpaRepository.findByUsernameAndAgeGraterThan("AAA",10);
        Member findMember = members.get(0);

        //then
        Assertions.assertEquals(findMember.getUsername(),"AAA");
        Assertions.assertEquals(findMember.getAge(),20);

    }
}