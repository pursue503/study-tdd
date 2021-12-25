package study.tdd.simpleboard.api.member.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberEntityTest {

    @Test
    public void createEntity() {
        Member member = new Member();
        assertThat(member).isNotNull();
    }

    @Test
    public void createProperties() {
        // 준비
        String nickname = "nickname";
        String password = "password";
        String memberEmail = "abc1234@naver.com";

        // 실행
        Member member = new Member(memberEmail, nickname, password);

        // 검증
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getPassword()).isEqualTo(password);
    }
}
