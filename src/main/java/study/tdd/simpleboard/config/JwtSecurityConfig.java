package study.tdd.simpleboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.filter.JWTFilter;
import study.tdd.simpleboard.util.JWTTokenProvider;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JWTTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JWTFilter jwtFilter = new JWTFilter(jwtTokenProvider, memberRepository);

        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
