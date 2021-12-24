package study.tdd.simpleboard.api.member.signup.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import study.tdd.simpleboard.api.member.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberSignUpRequestDTOTest {


    @ParameterizedTest
    @CsvSource(value = {"nickname:password:pursue503@naver.com", "Informix:q1w2e3:pursue503@naver.com", "JaeHyun:qwerty123:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("모든 파라매터가 null 이 아닐 때 True 반환을 확인한다.")
    public void signUpRequestDTOValidateParamTest(String nickname, String password, String memberEmail) {

        // 준비
        MemberSignUpRequestDTO memberSignUpRequestDTO = new MemberSignUpRequestDTO(nickname, password, memberEmail);
        assertThat(memberSignUpRequestDTO.validateParam()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname", "Informix", "JaeHyun"})
    @DisplayName("파라매터가 1개 이상 비어 있을 때 False 를 반환하는 것을 확인한다.")
    public void whenOneParameterIsNull(String nickname) {
        // given
        MemberSignUpRequestDTO memberSignUpRequestDTO = new MemberSignUpRequestDTO(nickname, null, null);

        // when then
        assertThat(memberSignUpRequestDTO.validateParam()).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname:password:pursue503@naver.com", "Informix:q1w2e3:pursue503@naver.com", "JaeHyun:qwerty123:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("DTO 에서 Member 객체가 생성되는지 확인한다.")
    public void createMember(String nickname, String password, String memberEmail) {

        // given
        MemberSignUpRequestDTO memberSignUpRequestDTO = new MemberSignUpRequestDTO(nickname, password, memberEmail);

        // when then
        assertThat(memberSignUpRequestDTO.toEntity()).isInstanceOf(Member.class);
    }

}
