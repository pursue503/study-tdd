package study.tdd.simpleboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.filter.JWTFilter;
import study.tdd.simpleboard.util.JWTProvider;

/**
 * 보안 설정
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 *
 * @author pursue503
 * @update JWT Security Filter 설정 추가
 *
 */

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {

    private final JWTProvider jwtProvider;
    private final JWTFilter jwtFilter;
    private final MemberRepository memberRepository;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/test/role_member").hasRole("USER")
//                .antMatchers("/test/role_admin").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/posts/**").permitAll();


        http.csrf()
                .disable();

        http.headers()
                .frameOptions()
                .disable();

        http.formLogin()
                .disable();

//        http.apply(new JwtSecurityConfig(jwtProvider, memberRepository));
        http.addFilterBefore(new JWTFilter(jwtProvider, memberRepository), UsernamePasswordAuthenticationFilter.class);
    }


}
