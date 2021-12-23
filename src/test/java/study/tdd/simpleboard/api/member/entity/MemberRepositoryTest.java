package study.tdd.simpleboard.api.member.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void saveMember() {
        // given
        String nickname = "Informix";
        String password = "q1w2e3";
        String memberEmail = "abc1234@naber.com";
        Member wantToSaveMember = new Member(memberEmail, nickname, password);

        // when
        Member savedMember = memberRepository.save(wantToSaveMember);

        // then
        assertThat(savedMember).isInstanceOf(Member.class);
        assertThat(savedMember).isEqualTo(wantToSaveMember);
    }

    @Test
    public void findMemberId() {
        // given
        String nickname = "Informix";
        String password = "q1w2e3";
        String memberEmail = "abc1234@naver.com";
        Member wantToSaveMember = new Member(memberEmail, nickname, password);

        // when
        Member saveMember = memberRepository.save(wantToSaveMember);

        // 영속성 컨텍스트 저장하고 비우기
        entityManager.flush();
        entityManager.clear();

        Member findMember = memberRepository.findById(saveMember.getMemberId()).orElseThrow(NullPointerException::new);

        // then
        assertThat(findMember).isInstanceOf(Member.class);
        assertThat(findMember.getNickname()).isEqualTo(nickname);
        assertThat(findMember.getPassword()).isEqualTo(password);
    }

    @Test
    public void existsByNickname() {

        // given
        String nickname = "Informix";
        String password = "q1w2e3";
        String memberEmail = "abc1234@naver.com";
        Member wantToSaveMember = new Member(memberEmail, nickname, password);

        //when
        Member saveMember = memberRepository.save(wantToSaveMember);

        // 영속성 컨텍스트 저장하고 비우기
        entityManager.flush();
        entityManager.clear();

        boolean checkNickname = memberRepository.existsByNickname(nickname);

        assertThat(checkNickname).isTrue();
    }

}
