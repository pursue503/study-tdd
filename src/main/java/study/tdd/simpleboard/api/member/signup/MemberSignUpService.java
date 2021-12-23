package study.tdd.simpleboard.api.member.signup;

import study.tdd.simpleboard.api.member.signup.valid.Valid;

public class MemberSignUpService {

    private final int VALID_PASSWORD_LENGTH = 8;

    private final Valid validationNickname;

    public MemberSignUpService(Valid validationNickname) {
        this.validationNickname = validationNickname;
    }

    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }

    public boolean validateNickname(String nickname) {
        return validationNickname.verify(nickname);
    }

    public boolean validatePassword(String password) {
        return true;
    }

    private boolean validPasswordLength(String password) {
        return password.length() >= VALID_PASSWORD_LENGTH;
    }

    private boolean validPasswordEnglish(String password) {
        return true;
    }

}
