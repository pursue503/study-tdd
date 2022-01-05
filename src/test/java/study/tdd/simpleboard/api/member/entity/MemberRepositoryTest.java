package study.tdd.simpleboard.api.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.tdd.simpleboard.api.member.signup.dto.MemberSignUpRequestDTO;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void saveMember() {
        // 준비
        String nickname = "Informix";
        String password = "q1w2e3";
        String memberEmail = "abc1234@naber.com";
        Member wantToSaveMember = Member.builder()
                .nickname(nickname)
                .password(password)
                .memberEmail(memberEmail)
                .build();

        // 실행
        Member savedMember = memberRepository.save(wantToSaveMember);

        // 검증
        assertThat(savedMember).isInstanceOf(Member.class);
        assertThat(savedMember).isEqualTo(wantToSaveMember);
    }

    @Test
    public void findMemberId() {
        // 준비
        String nickname = "Informix";
        String password = "q1w2e3";
        String memberEmail = "abc1234@naver.com";
        Member wantToSaveMember = Member.builder()
                .nickname(nickname)
                .password(password)
                .memberEmail(memberEmail)
                .build();

        // 실행
        Member saveMember = memberRepository.save(wantToSaveMember);

        // 영속성 컨텍스트 저장하고 비우기
        entityManager.flush();
        entityManager.clear();

        Member findMember = memberRepository.findById(saveMember.getMemberId()).orElseThrow(NullPointerException::new);

        // 검증
        assertThat(findMember).isInstanceOf(Member.class);
        assertThat(findMember.getNickname()).isEqualTo(nickname);
        assertThat(findMember.getPassword()).isEqualTo(password);
    }

    @Test
    public void existsByNickname() {

        // given
        String nickname = "Informix";
        String password = "q1w2e3";
        String memberEmail = "abc1234@naver.com";
        Member wantToSaveMember = Member.builder()
                .nickname(nickname)
                .password(password)
                .memberEmail(memberEmail)
                .build();

        //when
        memberRepository.save(wantToSaveMember);

        // 영속성 컨텍스트 저장하고 비우기
        entityManager.flush();
        entityManager.clear();

        boolean checkNickname = memberRepository.existsByNickname(nickname);

        assertThat(checkNickname).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname:password:pursue503@naver.com"}, delimiterString = ":")
    @DisplayName("회원 정보 전체 가져오기 테스트")
    public void findByMemberAllDataTest(String nickname, String password, String memberEmail) {

        // 준비
        MemberSignUpRequestDTO memberSignUpRequestDTO = new MemberSignUpRequestDTO(nickname, password, memberEmail);

        Member saveMember = memberRepository.save(memberSignUpRequestDTO.toEntity());

        entityManager.flush();
        entityManager.clear();

        Member findMember = memberRepository.findByMemberAllData(saveMember.getMemberId()).orElseThrow(NonUniqueResultException::new);

        assertThat(findMember.getMemberRoleList().get(0).getRole()).isEqualTo(Role.MEMBER);
    }

}
