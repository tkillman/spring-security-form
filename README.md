# Spring-security

## 스프링 시큐리티: 폼 인증
###
* 스프링 웹 프로젝트 만들기
> 1.spring init <br/>
> 2.thyme, spring-security, spring-web <br/>

* 스프링 시큐리티 연동
> 1.index.html 생성 <br/>
> 2.Controller 생성 <br/>
> 3.url 접속해보기 <br/>
> 4.security 의존성 추가 maven <br/>
> 5.기존 접속하던 화면들이 로그인 화면으로 전환되는거 확인 <br/>
> 6.로그인 해보기
> >id : user, password: 로그 확인

* 스프링 시큐리티 설정하기
> 1.목표 <br/>
> index, info는 로그인 없이, dashboard는 로그인 사용자만 로그인 하지 않았다면 로그인 페이지로 이동되도록, admin은 ADMIN 권한자만 페이지 갈 수 있도록 설정 <br/>
>> 1.config package 생성<br/>
>> 2.SecurityConfig class 생성<br/>
>> 3.WebSecurityConfigureAdapter 상속받기, @EnableWebSecurity 설정 <br/>
>> 4.configure override 재정의

* 스프링 시큐리티 커스터마이징: 인메모리 유저 추가
> 1.목표 <br/>
> 자동으로 생성되는 로그인 유저가 아닌 내가 설정한 유저로 로그인하기, admin에게 ADMIN role 부여하기
>>1.로그를 잘 보면 UserDetailsServiceAutoConfiguration 에서 설정한다.
>>2.spring.security.user.name<br/>,spring.security.user.password<br/>,spring.security.user.roles<br/>를 설정하면 된다는 것을 확인 할 수 있다.<br/>

> 2.목표 <br/>
> donggyu 유저를 만들고 USER 권한주기, donggyu 유저는 dashboard는 접근하지만 admin은 접근하지 못하는 것 확인
> >1.AuthenticationManagerBuilder 매개변수로 가진 configure 재정의



