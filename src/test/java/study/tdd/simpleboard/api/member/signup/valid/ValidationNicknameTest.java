package study.tdd.simpleboard.api.member.signup.valid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("닉네임 검증 테스트")
public class ValidationNicknameTest {
    
    Valid valid = new ValidationNickname();
    
    @ParameterizedTest
    @ValueSource(strings = {"nickname123", "informix123", "JaeHyun111"})
    @DisplayName("닉네임이 최소 3글자 이상일경우 True 반환을 확인한다.")
    public void validateNicknameThreeLength(String nickname) {
        assertThat(valid.verify(nickname)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname1234", "informix1111", "JaeHyun123123"})
    @DisplayName("닉네임에 알파벳이 포함되어 있을때 True 를 반환한다.")
    public void validateNicknameEnglish(String nickname) {
        assertThat(valid.verify(nickname)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1341315", "18918!@$", "19510751!@#"})
    @DisplayName("닉네임에 알파벳이 포함되어 있지 않을 때 False 를 반환한다.")
    public void validateNicknameNotEnglish(String nickname) {
        assertThat(valid.verify(nickname)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc13191", "informix9991", "JaeHyun123"})
    @DisplayName("닉네임에 숫자가 포함 되어있을 경우 True 를 반환한다.")
    public void validateNicknameNumber(String nickname) {
        assertThat(valid.verify(nickname)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg", "informix", "JaeHyun"})
    @DisplayName("닉네임에 숫자가 포함 되어있지 않을 경우 False 를 반환한다.")
    public void validateNicknameNotNumber(String nickname) {
        assertThat(valid.verify(nickname)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcd", "wohaha"})
    @DisplayName("닉네임이 3글자 이상이고 알파벳이 포함 되어 있지만 숫자가 없을경우 False 를 반환한다.")
    public void meetsTwoCriteriaOneBlame(String nickname) {
        assertThat(valid.verify(nickname)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a1", "w1"})
    @DisplayName("알파벳과 숫자가 포함되어 있지만 3글자 미만인경우 False 를 반환한다.")
    public void meetsTwoCriteriaOneBlame2(String nickname) {
        assertThat(valid.verify(nickname)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname123", "dkfjasADFFKDSOj1141kfndofn123411"})
    @DisplayName("닉네임이 3글자이상, 알파벳 포함, 숫자 포함 일 경우 True 를 반환한다.")
    public void meetsAllCriteria(String nickname) {
        assertThat(valid.verify(nickname)).isTrue();
    }
    
}
