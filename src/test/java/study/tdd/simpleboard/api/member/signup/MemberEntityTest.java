package study.tdd.simpleboard.api.member.signup;

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
        // given
        String nickname = "nickname";
        String password = "password";

        // when
        Member member = new Member(nickname, password);

        // then
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getPassword()).isEqualTo(password);
    }
}
