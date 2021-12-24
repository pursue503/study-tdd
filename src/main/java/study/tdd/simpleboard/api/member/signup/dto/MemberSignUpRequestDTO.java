package study.tdd.simpleboard.api.member.signup.dto;

import study.tdd.simpleboard.api.member.entity.Member;

/**
 * 회원가입을 위한 데이터들을 담아줄 DTO
 * 필요한 것만 Getter 을 만듭니다.
 */
public class MemberSignUpRequestDTO {

    private final String nickname;
    private final String password;
    private final String memberEmail;

    public MemberSignUpRequestDTO(String nickname, String password, String memberEmail) {
        this.nickname = nickname;
        this.password = password;
        this.memberEmail = memberEmail;
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .password(password)
                .memberEmail(memberEmail)
                .build();
    }

    public boolean validateParam() {
        return this.nickname != null
                &&
                this.password != null
                &&
                this.memberEmail != null;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
