package study.tdd.simpleboard.api.member.signup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import study.tdd.simpleboard.api.member.signup.service.MemberSignUpService;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.member.signup.MemberSignUpErrorCode;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberSignUpController.class)
public class MemberSignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MemberSignUpService memberSignUpService;

    @ParameterizedTest
    @CsvSource(value = {"nickname1234:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("회원가입 성공 테스트")
    public void MemberSignUpOk(String nickname, String password, String memberEmail) throws Exception {

        // 준비
        Map<String, Object> body = new HashMap<>();
        body.put("nickname", nickname);
        body.put("password", password);
        body.put("memberEmail", memberEmail);
        given(memberSignUpService.saveMember(any())).willReturn("회원가입에 성공하였습니다.");

        // 실행
        ResultActions actions = mockMvc.perform(post("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(mapper.writeValueAsString(body)));

        // 검증
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입에 성공하였습니다."));
    }

    @ParameterizedTest
    @CsvSource(value = {"ni:abcd1234!A:pursue503@naver.com", ":abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("닉네임이 비어있거나 닉네임 조건에 맞지 않을경우 400 BAD_REQUEST 가 발생한다.")
    public void BAD_REQUESTWhenNotValidNickname(String nickname, String password, String memberEmail) throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("nickname", nickname);
        body.put("password", password);
        body.put("memberEmail", memberEmail);
        given(memberSignUpService.saveMember(any())).willThrow(new BizException(MemberSignUpErrorCode.SIGN_UP_PARAM_NULL_OR_EMPTY));
        // 실행

        ResultActions actions = mockMvc.perform(post("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(mapper.writeValueAsString(body)));

        // 검증
        actions.andExpect(status().isBadRequest());
    }


}
