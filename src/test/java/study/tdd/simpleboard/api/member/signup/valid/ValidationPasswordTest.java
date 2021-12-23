package study.tdd.simpleboard.api.member.signup.valid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("비밀번호 검증 테스트")
public class ValidationPasswordTest {

    Valid valid = new ValidationPassword();
    
    @ParameterizedTest
    @ValueSource(strings = {"abcd1234!A", "bbbb1111@#A"})
    @DisplayName("모든 조건을 만족할 때 (8자 이상, 대문자하나 포함, 특수문자 하나포함, 숫자 하나이상) True 를 반환한다.")
    public void success(String password) {
        assertThat(valid.verify(password)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc12!A", "vcbaa!#A"})
    @DisplayName("비밀번호 조건에서 길이조건을 만족하지 못할 때 False 를 반환한다.")
    public void meetTheLengthCondition(String password) {
        assertThat(valid.verify(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc123!!##", "bcaicjac!!@@"})
    @DisplayName("비밀번호 조건에서 대문자조건을 만족하지 못할 때 False 를 반환한다.")
    public void meetTheEnglishCondition(String password) {
        assertThat(valid.verify(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc123421A", "vaijvz11BB"})
    @DisplayName("비밀번호 조건에서 특수문자조건을 만족하지 못할 때 False 를 반환한다.")
    public void meetTheSpecialCharactersCondition(String password) {
        assertThat(valid.verify(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc!@#!BBA", "aaiw!@!#PPZZ"})
    @DisplayName("비밀번호 조건에서 숫자조건을 만족하지 못할 때 False 를 반환한다.")
    public void meetTheNumberCondition(String password) {
        assertThat(valid.verify(password)).isFalse();
    }
    
}
