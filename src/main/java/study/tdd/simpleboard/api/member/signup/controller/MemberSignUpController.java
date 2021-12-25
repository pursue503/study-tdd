package study.tdd.simpleboard.api.member.signup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.tdd.simpleboard.api.common.ResponseDTO;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;

@RestController
public class MemberSignUpController {

    @PostMapping("/members")
    public ResponseDTO<String> signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) {
        return new ResponseDTO<>("", "회원가입에 성공하였습니다.", HttpStatus.OK);
    }

}
