package study.tdd.simpleboard.api.member.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTOTest;
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
        assertThat(memberSignUpService.saveMember(toMemberSignUpRequestDTO(nickname, password, memberEmail))).isInstanceOf(Member.class);
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
