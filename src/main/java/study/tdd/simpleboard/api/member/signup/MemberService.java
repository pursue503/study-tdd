package study.tdd.simpleboard.api.member.signup;

public class MemberService {
    public boolean validateSignUpParam(String nickname, String password) {
        return nickname != null && password != null;
    }

    public boolean validateNickname(String nickname) {
        if (nickname.length() >= 3) {

            int numberCount = 0;
            int englishCount = 0;

            for (char c : nickname.toCharArray()) {
                if ((c >= 48 && c <= 57)) {
                    numberCount++;
                    continue;
                }

                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    englishCount++;
                    continue;
                }

                return false;

            }


            if (englishCount == 0) {
                return false;
            }
            if (numberCount == 0) {
                return false;
            }

            return true;
        }
        return false;
    }
}
