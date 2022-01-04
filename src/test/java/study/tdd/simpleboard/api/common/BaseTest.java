package study.tdd.simpleboard.api.common;

import org.springframework.boot.test.mock.mockito.MockBean;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.util.JWTProvider;

public abstract class BaseTest {

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    JWTProvider jwtProvider;

}
