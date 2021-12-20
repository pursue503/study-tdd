package study.tdd.simpleboard.api.member.signup;

public class MemberSignUpService {

    private final int VALID_NICK_NAME_LENGTH = 3;

    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }

    public boolean validateNickname(String nickname) {
        if (!validEnglish(nickname)) {
            return false;
        }
        if (!validNumber(nickname)) {
            return false;
        }
        if (!validLength(nickname)) {
            return false;
        }
        return true;
    }

    private boolean validLength(String nickname) {
        return nickname.length() >= VALID_NICK_NAME_LENGTH;
    }

    private boolean validEnglish(String nickname) {
        return nickname.chars()
                .filter(c -> (c >= 65 && c <= 90) || (c >= 97 && c <= 122))
                .count() != 0;
    }

    private boolean validNumber(String nickname) {
        return nickname.chars()
                .filter(c -> c >= 48 && c<= 57)
                .count() != 0;
    }
}
