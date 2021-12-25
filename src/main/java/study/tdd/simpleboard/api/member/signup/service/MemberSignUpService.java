package study.tdd.simpleboard.api.member.signup.service;

import org.springframework.stereotype.Service;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;
import study.tdd.simpleboard.api.member.signup.valid.Valid;
import study.tdd.simpleboard.api.member.signup.valid.ValidationNickname;
import study.tdd.simpleboard.api.member.signup.valid.ValidationPassword;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.member.signup.MemberSignUpErrorCode;

@Service
public class MemberSignUpService {

    private final Valid validationNickname = new ValidationNickname();
    private final Valid validationPassword = new ValidationPassword();
    private final MemberRepository memberRepository;

    // Constructor
    public MemberSignUpService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Method

    public String saveMember(MemberSignUpRequestDTO memberSignUpRequestDTO) {
        if (!memberSignUpRequestDTO.validateParam()) {
            throw new BizException(MemberSignUpErrorCode.SIGN_UP_PARAM_NULL_OR_EMPTY);
        }
        if (!validationNickname.verify(memberSignUpRequestDTO.getNickname())) {
            throw new BizException(MemberSignUpErrorCode.SIGN_UP_NICKNAME_NOT_VALID);

        }
        if (!validationPassword.verify(memberSignUpRequestDTO.getPassword())) {
            throw new BizException(MemberSignUpErrorCode.SIGN_UP_PASSWORD_NOT_VALID);
        }
        if (checkDuplicatedNickname(memberSignUpRequestDTO.getNickname())) {
            throw new BizException(MemberSignUpErrorCode.SIGN_UP_NICKNAME_DUPLICATED);
        }
        memberRepository.save(memberSignUpRequestDTO.toEntity());
        return "회원가입에 성공하였습니다.";
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


}
