package study.tdd.simpleboard.api.member.signup;

public class MemberService {
    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }

    public boolean validateNickname(String nickname) {
        if(nickname.length() >= 3) {
            return true;
        }
        return false;
    }
}
