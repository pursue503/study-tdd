package study.tdd.simpleboard.api.member.signup;

public class MemberSignUpService {

    private final int VALID_NICK_NAME_LENGTH = 3;
    private final int VALID_PASSWORD_LENGTH = 8;


    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }

    public boolean validateNickname(String nickname) {
        if (!validNickNameEnglish(nickname)) {
            return false;
        }
        if (!validNickNameNumber(nickname)) {
            return false;
        }
        if (!validNickNameLength(nickname)) {
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password) {

    }

    private boolean validNickNameLength(String nickname) {
        return nickname.length() >= VALID_NICK_NAME_LENGTH;
    }

    private boolean validNickNameEnglish(String nickname) {
        return nickname.chars()
                    .filter(c -> (c >= 65 && c <= 90) || (c >= 97 && c <= 122))
                .count() != 0;
    }

    private boolean validNickNameNumber(String nickname) {
        return nickname.chars()
                .filter(c -> c >= 48 && c<= 57)
                .count() != 0;
    }

    private boolean validPasswordLength(String password) {
        return password.length() >= VALID_PASSWORD_LENGTH;
    }

    private boolean validPasswordEnglish(String password) {
        return password
                .chars()
    }

}
