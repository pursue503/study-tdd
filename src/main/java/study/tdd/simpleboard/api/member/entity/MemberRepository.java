package study.tdd.simpleboard.api.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import study.tdd.simpleboard.api.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
