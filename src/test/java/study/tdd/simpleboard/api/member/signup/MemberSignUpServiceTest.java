package study.tdd.simpleboard.api.member.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.valid.ValidationNickname;
import study.tdd.simpleboard.api.member.signup.valid.ValidationPassword;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("회원 가입 테스트")
public class MemberSignUpServiceTest {

    MemberRepository memberRepository = mock(MemberRepository.class);

    MemberSignUpService memberSignUpService = new MemberSignUpService(memberRepository, new ValidationNickname(), new ValidationPassword());

    @Test
    public void createMemberService() {
        assertThat(memberSignUpService).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname1234:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("모든 조건을 통과시키고 회원가입을 성공한다.")
    public void saveMember(String nickname, String password, String memberEmail) {
        assertThat(memberSignUpService.saveMember(nickname, password, memberEmail)).isInstanceOf(Member.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname:password:pursue503@naver.com", "Informix:q1w2e3:pursue503@naver.com", "JaeHyun:qwerty123:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("모든 파라매터가 null 이 아닐 때 True 반환을 확인한다.")
    public void validateSignUpParam(String nickname, String password, String memberEmail) {
        assertThat(memberSignUpService.validateSignUpParam(nickname, password, memberEmail)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname", "Informix", "JaeHyun"})
    @DisplayName("파라매터가 1개 이상 비어 있을 때 False 를 반환하는 것을 확인한다.")
    public void whenOneParameterIsNull(String nickname) {
        assertThat(memberSignUpService.validateSignUpParam(nickname, null, null)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname123", "nicknames33"})
    @DisplayName("닉네임이 중복되어있으면 True 를 반환한다.")
    public void duplicatedNickname(String nickname) {
        given(memberRepository.existsByNickname(nickname)).willReturn(true);
        assertThat(memberSignUpService.checkDuplicatedNickname(nickname)).isTrue();
    }

}
