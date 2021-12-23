package study.tdd.simpleboard.api.member.signup.valid;

import java.util.List;

public class ValidationPassword implements Valid {

    private static final int VALID_PASSWORD_LENGTH = 8;
    private static final List<Character> list = List.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')');

    @Override
    public boolean verify(String target) {
        if (!validPasswordLength(target)) {
            return false;
        }
        if (!validPasswordEnglish(target)) {
            return false;
        }
        if (!validPasswordSpecialCharacters(target)) {
            return false;
        }
        return validPasswordNumber(target);
    }

    private boolean validPasswordLength(String password) {
        return password.length() >= VALID_PASSWORD_LENGTH;
    }

    private boolean validPasswordEnglish(String password) {
        return password.chars()
                .filter(c -> c >= 'A' && c <= 'Z')
                .count() != 0;
    }

    private boolean validPasswordSpecialCharacters(String password) {
        return password.chars()
                .filter(c -> list.contains((char) c))
                .count() != 0;
    }

    private boolean validPasswordNumber(String password) {
        return password.chars()
                .filter(c -> c >= '0' && c <= '9')
                .count() != 0;
    }

}
