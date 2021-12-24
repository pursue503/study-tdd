package study.tdd.simpleboard.api.member.signup;

import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;
import study.tdd.simpleboard.api.member.signup.valid.Valid;

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
            return null;
        }
        if (!validationNickname.verify(memberSignUpRequestDTO.getNickname())) {
            return null;

        }
        if (!validationPassword.verify(memberSignUpRequestDTO.getPassword())) {
            return null;

        }
        if (checkDuplicatedNickname(memberSignUpRequestDTO.getNickname())) {
            return null;

        }
        return memberSignUpRequestDTO.toEntity();
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


}
