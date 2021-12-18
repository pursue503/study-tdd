package study.tdd.simpleboard.api.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberEmail;
    private String nickname;
    private String password;

    public Member(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

}
