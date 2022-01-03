package study.tdd.simpleboard.api.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "member")
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public Member(String memberEmail, String nickname, String password) {
        this.memberEmail = memberEmail;
        this.nickname = nickname;
        this.password = password;
    }

    @Builder
    public Member(String memberEmail, String nickname, String password, Role memberRole) {
        this.memberEmail = memberEmail;
        this.nickname = nickname;
        this.password = password;
        this.memberRoleList.add(
                MemberRole.builder()
                        .role(memberRole)
                        .member(this)
                        .build()
        );
    }
}
