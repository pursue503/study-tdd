package study.tdd.simpleboard.api.member.signup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.tdd.simpleboard.api.common.ResponseDTO;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;
import study.tdd.simpleboard.api.member.signup.service.MemberSignUpService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberSignUpController {

    private final MemberSignUpService memberSignUpService;

    @PostMapping("/members")
    public ResponseDTO<String> signUp(@RequestBody MemberSignUpRequestDTO memberSignUpRequestDTO) {
        return new ResponseDTO<>("", memberSignUpService.saveMember(memberSignUpRequestDTO), HttpStatus.OK);
    }

}
