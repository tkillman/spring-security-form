package com.example.springsecurityform.config;

import com.example.springsecurityform.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Override
    public void configure(WebSecurity web) {
        // web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        // 해당 3개는 .and()로 연결 할수도 있다.
        String loginPage = "/login";

        http.authorizeHttpRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup", loginPage).permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")

                // hasAnyAuthority은 hasRole과 기능이 같지만 spring security규칙에 의해 role 명칭은 prefix ROLE_를 붙여줘야 한다.
                // .mvcMatchers("/user").hasAnyAuthority("ROLE_USER")
                // 해당 내용은 좋지않다. ignore에 대해서 filter 로직을 수행하기 때문이다.
                // ignore 할거면 WebSecurity 매개변수로 가지는 configure에 설정하자.
                //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().authenticated();

        // loginPage 설정하면 DefaultLogin/LogoutPageGeneratingFilter(default 화면을 만들어주던 필터) 가 빠지게 된다.
        http.formLogin().loginPage(loginPage);
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");

        http.httpBasic();

        // 해당 application에서 만들어진 하위 thread에도 principal 모두 공유시킬 수 있도록 함.
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        // Session 전략
        // maximumSessions 1개
        // maxSessionsPreventsLogin true 로그인 사용자가 있으면 로그인 불가, false 로그인 사용자 세션 만료
        http.sessionManagement()
                .sessionFixation()
                .changeSessionId()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false); // default false

        // access denied 페이지 변경
        // http.exceptionHandling().accessDeniedPage("/access-denied");
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            log.error(Optional.ofNullable(principal)
                    .orElse("anonymous")
                    + " is denied access " + request.getRequestURI());
            response.sendRedirect("/access-denied");
        });

        // 로그인 기억
        // http.authorizeRequests().anyRequest().rememberMe();

        // 로그인 기억 무시
        // http.authorizeRequests().anyRequest().fullyAuthenticated();

        // 로그인 기억하기
        http.rememberMe()
                .userDetailsService(accountService)
                .key("remember-me-sample");

        // custom logout 설정
//        http.logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/성공시 url")
//                .deleteCookies("")
//                .addLogoutHandler((request, response, authentication) -> log.debug("logout"))
//                .logoutSuccessHandler((request, response, authentication) -> log.debug("logout Success"));
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // {noop} 옵션은 암호화와 관련되어있다. 옵션사용시 저장할 경우 암호화되서 패스워드 저장
//        auth.inMemoryAuthentication()
//                .withUser("donggyu").password("{noop}123").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}123").roles("ADMIN");
//    }
}
