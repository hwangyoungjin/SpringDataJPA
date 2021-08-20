package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testMember(){
        //given
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(saveMember.getId()).get();

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
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        //that

        //단건 조회
        Assertions.assertEquals(findMember1,member1);
        Assertions.assertEquals(findMember2,member2);
        //리스트 조회
        List<Member> all = memberRepository.findAll();
        Assertions.assertEquals(all.size(), 2);
        //카운트조회
        long count = memberRepository.count();
        Assertions.assertEquals(count, 2);
        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        //카운트조회
        count = memberRepository.count();
        Assertions.assertEquals(count, 0);
    }


    @Test
    public void usernameAndAge(){
        //given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("AAA",10);
        Member findMember = members.get(0);

        //then
        Assertions.assertEquals(findMember.getUsername(),"AAA");
        Assertions.assertEquals(findMember.getAge(),20);

    }

    @Test
    public void testQuery(){
        //given
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<Member> result = memberRepository.findUser("AAA",10);
        
        //that
        Assertions.assertEquals(result.get(0),m1);
    }

    @Test
    public void findMemberDto(){
        //given
        Team team = new Team("teamA");
        teamRepository.save(team);
        Member m1 = new Member("AAA",10);
        memberRepository.save(m1);

        //when
        List<MemberDto> result = memberRepository.findMemberDto();

        //that
        for(MemberDto m : result){
            System.out.println("dto : "+m);
        }
    }

}