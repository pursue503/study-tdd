package study.tdd.simpleboard.api.member.signup;

import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.api.member.signup.valid.Valid;
import study.tdd.simpleboard.api.member.signup.valid.ValidationNickname;
import study.tdd.simpleboard.api.member.signup.valid.ValidationPassword;

public class MemberSignUpService {


    private final Valid validationNickname = new ValidationNickname();
    private final Valid validationPassword = new ValidationPassword();
    private final MemberRepository memberRepository;

    // Constructor
    public MemberSignUpService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Method

    public Member saveMember(String nickname, String password, String memberEmail) {
        if (!validateSignUpParam(nickname, password, memberEmail)) {
            return null;
        }
        if (!validateNickname(nickname)) {
            return null;

        }
        if (!validatePassword(password)) {
            return null;

        }
        if (checkDuplicatedNickname(nickname)) {
            return null;

        }
        return new Member(memberEmail, nickname, password);
    }

    public boolean validateSignUpParam(String nickname, String password, String memberEmail) {
        return nickname != null && password != null && memberEmail != null;
    }



    public boolean validateNickname(String nickname) {
        return validationNickname.verify(nickname);
    }

    public boolean validatePassword(String password) {
        return validationPassword.verify(password);
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }



}
