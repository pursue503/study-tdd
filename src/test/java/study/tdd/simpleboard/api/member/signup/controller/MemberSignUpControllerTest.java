package study.tdd.simpleboard.api.member.signup.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MemberSignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void MemberSignUpOk() throws Exception {

        // 준비

        // 실행
        ResultActions actions = mockMvc.perform(post("/members"));

        // 검증
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입에 성공하였습니다."));

    }

}
