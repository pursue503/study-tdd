package study.tdd.simpleboard.api.member.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;
import study.tdd.simpleboard.api.member.signup.service.MemberSignUpService;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.member.signup.MemberSignUpErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("회원 가입 테스트")
public class MemberSignUpServiceTest {

    MemberRepository memberRepository = mock(MemberRepository.class);

    MemberSignUpService memberSignUpService = new MemberSignUpService(memberRepository);

    @Test
    public void createMemberService() {
        assertThat(memberSignUpService).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname1234:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("모든 조건을 통과시키고 회원가입을 성공한다.")
    public void saveMember(String nickname, String password, String memberEmail) {

        // given
        MemberSignUpRequestDTO memberSignUpRequestDTO = toMemberSignUpRequestDTO(nickname, password, memberEmail);

        // when then
        assertThat(memberSignUpService.saveMember(memberSignUpRequestDTO)).isEqualTo("회원가입에 성공하였습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"ni:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("회원가입 조건에서 닉네임 조건이 안맞을 경우 BizException 을 발생시킨다.")
    public void BizExceptionWhenNotValidNickname(String nickname, String password, String memberEmail) {

        // given
        MemberSignUpRequestDTO memberSignUpRequestDTO = toMemberSignUpRequestDTO(nickname, password, memberEmail);

        // when then
        assertThatThrownBy(() -> memberSignUpService.saveMember(memberSignUpRequestDTO))
                .isInstanceOf(BizException.class)
                .hasMessage(MemberSignUpErrorCode.SIGN_UP_NICKNAME_NOT_VALID.getMsg());
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname1234:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("회원가입시 회원의 아이디가 중복되어있으면 BizException 을 발생시킨다.")
    public void BizExceptionWhenDuplicatedNickname(String nickname, String password, String memberEmail) {

        MemberSignUpRequestDTO memberSignUpRequestDTO = toMemberSignUpRequestDTO(nickname, password, memberEmail);

        given(memberRepository.existsByNickname(memberSignUpRequestDTO.getNickname())).willReturn(true);

        assertThatThrownBy(() -> memberSignUpService.saveMember(memberSignUpRequestDTO))
                .isInstanceOf(BizException.class)
                .hasMessage(MemberSignUpErrorCode.SIGN_UP_NICKNAME_DUPLICATED.getMsg());
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname1234:abcd12:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("회원가입시 회원의 비밀번호가 조건을 만족하지 못하면 BizException 을 발생시킨다.")
    public void BizExceptionWhenNotValidPassword(String nickname, String password, String memberEmail) {
        // given
        MemberSignUpRequestDTO memberSignUpRequestDTO = toMemberSignUpRequestDTO(nickname, password, memberEmail);

        // when then
        assertThatThrownBy(() -> memberSignUpService.saveMember(memberSignUpRequestDTO))
                .isInstanceOf(BizException.class)
                .hasMessage(MemberSignUpErrorCode.SIGN_UP_PASSWORD_NOT_VALID.getMsg());
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname123", "nicknames33"})
    @DisplayName("닉네임이 중복되어있으면 True 를 반환한다.")
    public void duplicatedNickname(String nickname) {
        given(memberRepository.existsByNickname(nickname)).willReturn(true);
        assertThat(memberSignUpService.checkDuplicatedNickname(nickname)).isTrue();
    }

    private MemberSignUpRequestDTO toMemberSignUpRequestDTO(String nickname, String password, String memberEmail) {
        return new MemberSignUpRequestDTO(nickname, password, memberEmail);
    }

}
