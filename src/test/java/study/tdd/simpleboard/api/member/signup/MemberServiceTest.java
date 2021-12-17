package study.tdd.simpleboard.api.member.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {

    @Test
    public void createMemberService() {
        MemberService memberService = new MemberService();
        assertThat(memberService).isNotNull();
    }

    @Nested
    @DisplayName("닉네임, 비밀번호, 비밀번호 확인을 검증")
    class SignupTest {

        @ParameterizedTest
        @CsvSource(value = {"nickname:password", "Informix:q1w2e3", "JaeHyun:qwerty123"}, delimiterString = ":")
        @DisplayName("모든 파라매터가 null 이 아닐 때 True 반환을 확인한다.")
        public void validateSignUpParam(String nickname, String password) {
            MemberService memberService = new MemberService();
            assertThat(memberService.validateSignUpParam(nickname, password)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"nickname", "Informix", "JaeHyun"})
        @DisplayName("1개의 파라매터가 비어 있을 때 false를 반환하는 것을 확인한다.")
        public void whenOneParameterIsNull(String nickname) {
            MemberService memberService = new MemberService();
            assertThat(memberService.validateSignUpParam(nickname, null)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"nickname123", "informix123", "JaeHyun111"})
        @DisplayName("닉네임이 최소 3글자 이상일경우 True 반환을 확인한다.")
        public void validateNickname(String nickname) {
            MemberService memberService = new MemberService();
            assertThat(memberService.validateNickname(nickname)).isTrue();
        }

    }


}
