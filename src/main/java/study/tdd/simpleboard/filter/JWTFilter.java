package study.tdd.simpleboard.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.custom.UserCustom;
import study.tdd.simpleboard.util.JWTProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final String ACCESS_TOKEN_HEADER = "ACCESS-TOKEN";
    private final JWTProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getAccessToken(request);

        if (!ObjectUtils.isEmpty(accessToken)) {
            Long memberId = Long.parseLong(jwtProvider.verifyAccessToken(accessToken));

            Authentication authentication = getAuthentication(memberId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_HEADER);
    }

    private Authentication getAuthentication(Long memberId) {
        Member member = memberRepository.findByMemberAllData(memberId).orElseThrow(NullPointerException::new);

        List<GrantedAuthority> authorities = member.getMemberRoleList().stream().map(entity -> new SimpleGrantedAuthority(entity.getRole().getRole())).collect(Collectors.toList());
        UserDetails userDetails = new UserCustom(member, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
