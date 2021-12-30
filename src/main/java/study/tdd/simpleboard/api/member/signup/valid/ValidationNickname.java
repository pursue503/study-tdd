package study.tdd.simpleboard.api.member.signup.valid;

public class ValidationNickname implements Valid {

    private static final int VALID_NICK_NAME_LENGTH = 3;


    @Override
    public boolean verify(String target) {
        if (!validNickNameEnglish(target)) {
            return false;
        }
        if (!validNickNameNumber(target)) {
            return false;
        }
        return validNickNameLength(target);
    }

    private boolean validNickNameLength(String nickname) {
        return nickname.length() >= VALID_NICK_NAME_LENGTH;
    }

    private boolean validNickNameEnglish(String nickname) {
        return nickname.chars()
                .filter(c -> (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                .count() != 0;
    }

    private boolean validNickNameNumber(String nickname) {
        return nickname.chars()
                .filter(c -> c >= '0' && c <= '9')
                .count() != 0;
    }

}
