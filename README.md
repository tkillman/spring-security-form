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

* 스프링 시큐리티 커스터마이징: JPA 연동
> 1.목표 <br/>
> jpa를 통해 유저를 데이터베이스에서 읽어오도록 변경하자.
> >1.maven jpa와 h2 추가 <br/>
> >2.model 패키지 추가 <br/>
> >3.Account Entity 생성 <br/>id, username, password, role <br/><br/>
> >4.repository 패키지 추가 <br/>
> >5.AccountRepository class 생성, findByUsername method 생성<br/>
> >6.service 패키지 추가 <br/>
> >7.AccountService class 생성 <br/>
> >UserDetailsService 상속, loadUserByUsername method 재정의 <br/>
> > >loadUserByUsername의 역할은 UserDetails 반환
> >
> >8.inmemory 사용 주석 <br/>
> >9.AccountController 생성, user를 저장할 수 있도록 생성
> >>주의사항!!! password 형태는 '{noop}패스워드' 이여야 한다.

* 스프링 시큐리티 커스터마이징: PasswordEncoder
> 1.목표 <br/>
> PasswordEncoder bean 생성
> >1.main 메소드에 PasswordEncoder 등록 <br/>
> >예전에는 {noop}를 사용했으나 여러 이유로 포맷이 교체되었다고 한다.
> 

* 스프링 시큐리티 테스트 1부
> 1.목표 <br/>
> index_anonymous 테스트
> > 유저가 없을 때 index 접근하였을 때 status ok인지 확인
>
> index_donggyu 테스트
> > donggyu 유저가 index 접근하였을 때 status ok인지 확인
>
> admin_donggyu 테스트
> > donggyu 유저가 admin 접근하였을 때 status forbidden 확인
> 
> admin_admin 테스트
> > admin 유저가 admin 접근하였을 때 status ok 인지 확인

> 2.목표 <br/>
> @WithAnonymousUser <br/>
> @WithMockUser(username = "donggyu", roles = "USER") 사용해보기 <br/>
> @WithCustomUser annotation 생성 후 @WithMockUser(username = "donggyu", roles = "USER") 주입

* 스프링 시큐리티 테스트 2부
> 1.목표 <br/>
> 로그인 테스트
> > login_success 테스트 <br/>
> >> before 로그인 생성 service 저장 후 로그인 성공 <br/>
> >
> > login_fail 테스트 <br/>
> >> 잘못된 로그인 정보로 로그인 시 로그인 실패 테스트 <br/>

## 스프링 시큐리티: 아키텍처
* SecurityContextHolder와 Authentication
> 1.소제목 <br/>
> 2.소제목 <br/>

* AuthenticationManager와 Authentication
> 1.소제목 <br/>
> 2.소제목 <br/>

## 웹 애플리케이션 시큐리티
* 스프링 시큐리티 ignoring() 1부
> 1.목표 <br/>
> static 한 리소스 요청에 대해 ignore 설정 (예 : /favicon.io) <br/>
> > WebSecurity web을 매개변수로 가지는 configure 메소드 재정의
> 
> 
> 