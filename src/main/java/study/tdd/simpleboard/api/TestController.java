package study.tdd.simpleboard.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.tdd.simpleboard.custom.UserCustom;
import study.tdd.simpleboard.util.JWTTokenProvider;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final JWTTokenProvider jwtTokenProvider;

    @PreAuthorize(value = "hasRole('USER')")
    @GetMapping("/test/role_member")
    public String test(Authentication authentication) {

        UserCustom userCustom = (UserCustom) authentication.getPrincipal();

        log.info(userCustom.getUsername());

        log.info("======= role member =====");
        log.info("======= role member =====");
        log.info("======= role member =====");
        return "success";
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/test/role_admin")
    public String test2() {
        log.info("======= role admin =====");
        log.info("======= role admin =====");
        log.info("======= role admin =====");
        return "fail";
    }

    @GetMapping("/test/access-token")
    public String accessToken() {
        return jwtTokenProvider.createAccessToken(1L);
    }

}
