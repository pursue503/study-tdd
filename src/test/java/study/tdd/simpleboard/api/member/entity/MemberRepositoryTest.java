package study.tdd.simpleboard.api.member.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    @Test
    public void createMemberRepository() {
        MemberRepository memberRepository = new MemberRepository();
        assertThat(memberRepository).isEqualTo(memberRepository);
    }

    @Test
    public void saveMember() {
        // given
        MemberRepository memberRepository = new MemberRepository();
        String nickname = "Informix";
        String password = "q1w2e3";
        Member wantToSaveMember = new Member(nickname, password);

        // when
        Member savedMember = memberRepository.save(wantToSaveMember);

        // then
        assertThat(savedMember).isInstanceOf(Member.class);
        assertThat(savedMember).isEqualTo(wantToSaveMember);
    }

    @Test
    public void findMember() {
        // given
        MemberRepository memberRepository = new MemberRepository();
        String nickname = "Informix";
        String password = "q1w2e3";
        Member wantToSaveMember = new Member(nickname, password);

        // when
        memberRepository.save(wantToSaveMember);
        Member findMember = memberRepository.findByNickname(nickname);

        // then
        assertThat(findMember).isInstanceOf(Member.class);
        assertThat(findMember.getNickname()).isEqualTo(nickname);
        assertThat(findMember.getPassword()).isEqualTo(password);
    }

}
