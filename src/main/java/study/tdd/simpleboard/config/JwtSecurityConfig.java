package study.tdd.simpleboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.filter.JWTFilter;
import study.tdd.simpleboard.util.JWTProvider;

/**
 * JWT Security Filter 를 등록하는 Config 클래스 입니다.
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JWTProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void configure(HttpSecurity builder) {
        JWTFilter jwtFilter = new JWTFilter(jwtProvider, memberRepository);

        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
