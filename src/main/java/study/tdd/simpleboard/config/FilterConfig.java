package study.tdd.simpleboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.util.JWTProvider;

@RequiredArgsConstructor
@Order(2)
@Configuration
public class FilterConfig extends WebSecurityConfigurerAdapter {

    private final JWTProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(new JwtSecurityConfig(jwtProvider, memberRepository));
    }

}
