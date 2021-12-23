package study.tdd.simpleboard.api.member.signup;

import study.tdd.simpleboard.api.member.signup.valid.Valid;
import study.tdd.simpleboard.api.member.signup.valid.ValidationNickname;
import study.tdd.simpleboard.api.member.signup.valid.ValidationPassword;

public class MemberSignUpService {


    private final Valid validationNickname = new ValidationNickname();
    private final Valid validationPassword = new ValidationPassword();

    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }

    public boolean validateNickname(String nickname) {
        return validationNickname.verify(nickname);
    }

    public boolean validatePassword(String password) {
        return validationPassword.verify(password);
    }



}
