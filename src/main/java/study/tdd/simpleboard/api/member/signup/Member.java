package study.tdd.simpleboard.api.member.signup;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member {

    private String nickname;
    private String password;

    public Member(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
