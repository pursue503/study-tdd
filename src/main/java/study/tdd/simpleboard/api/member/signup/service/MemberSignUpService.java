package study.tdd.simpleboard.api.member.signup.service;

import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;
import study.tdd.simpleboard.api.member.signup.valid.Valid;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.member.signup.MemberSignUpErrorCode;

public class MemberSignUpService {

    private final Valid validationNickname;
    private final Valid validationPassword;
    private final MemberRepository memberRepository;

    // Constructor
    public MemberSignUpService(MemberRepository memberRepository, Valid validationNickname, Valid validationPassword) {
        this.memberRepository = memberRepository;
        this.validationNickname = validationNickname;
        this.validationPassword = validationPassword;
    }

    // Method

    public Member saveMember(MemberSignUpRequestDTO memberSignUpRequestDTO) {
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
        return memberSignUpRequestDTO.toEntity();
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


}
