package study.tdd.simpleboard.api.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);

    @Query(value = "select DISTINCT m from Member m join fetch m.memberRoleList r where m.memberId = :id")
    Optional<Member> findByMemberAllData(@Param("id") Long memberId);

}
