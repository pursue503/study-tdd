package study.tdd.simpleboard.api.member.signup;

public class MemberService {
    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }
}
