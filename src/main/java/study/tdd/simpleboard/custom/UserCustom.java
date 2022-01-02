package study.tdd.simpleboard.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import study.tdd.simpleboard.api.member.entity.Member;

import java.util.Collection;

/**
 *
 * Authentication 내부에 들어갈 객체
 *
 */
public class UserCustom extends User {

//    private final Long memberId;

    public UserCustom(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getMemberId().toString(), member.getPassword(), authorities);
//        this.memberId = member.getMemberId();
    }


}
