package com.example.springsecurityform.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        // web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 해당 3개는 .and()로 연결 할수도 있다.
        http.authorizeHttpRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                // 해당 내용은 좋지않다. ignore에 대해서 filter 로직을 수행하기 때문이다.
                // ignore 할거면 WebSecurity 매개변수로 가지는 configure에 설정하자.
                //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().authenticated();

        http.formLogin();
        http.httpBasic();

        // 해당 application에서 만들어진 하위 thread에도 principal 모두 공유시킬 수 있도록 함.
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
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
