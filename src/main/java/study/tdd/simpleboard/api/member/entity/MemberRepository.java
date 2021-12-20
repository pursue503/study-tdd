package study.tdd.simpleboard.api.member.entity;

import study.tdd.simpleboard.api.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    List<Member> memberList = new ArrayList<>();

    public Member save(Member member) {
        memberList.add(member);
        return member;
    }

    public Member findByNickname(String nickname) {
        return memberList.stream()
                .filter(entity -> entity.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }
}
