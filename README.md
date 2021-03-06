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

* 스프링 시큐리티 ignoring() 2부
> 팁 : static 한 리소스 요청에 대해 ignore에 설정하지 않고 authorizeHttpRequests에 
> permitAll로 설정하는건 좋지 못하다. 왜냐하면 모두 인증을 통과하기는 하지만 filter 로직을 수행하기 때문이다. <br/>
> 팁 : 왠만하면 동적 리소스는 permitAll()에 거는게 좋다.

* Async 웹 MVC를 지원하는 필터: WebAsyncManagerIntegrationFilter
> 1.목표 <br/>
>> async 한 상태에 대해서도 SecurityContext가 공유되고 있음을 확인하기
> >> 1.log 패키지 생성, SecurityLogger class 생성
> >>
> >> 2.SecurityLogger log 메소드 생성 (매개변수 message, thread 이름 찍기, principal 정보 찍기)
> >>
> >> 3.Controller에서 async-handler 요청을 받아서 Callbable<String> 반환하기(log 메소드로 mvc내에서와 callable 내에서 다름을 확인)
> 

* 스프링 시큐리티와 @Async
> 1.목표 <br/>
> async 한 서비스에서의 Principal 정보 확인하기 (principal이 공유되지 않음) <br/>
> > 1.AccountSerivce에 ayncService 만들기 (@Async) <br/>
> > 2.@Async를 사용하기 위해 SpringSecurityFormApplication에 @EnableAsync 설정 <br/>
> > 3.Controller /async-service 처리하고 service 앞,뒤, 안에 로그 찍기
>
> 2.목표 <br/>
> 위의 공유되지 않은 pricipal 정보를 공유시킬 수 있도록 설정 변경해보기. <br/>
> configure에서 SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
>
> ![로그파일](./doc/로그파일.JPG)

* SecurityContext 영속화 필터: SecurityContextPersistenceFilter
> 기능 : SecurityContext를 유지하게 해주는 필터, login 한 뒤 다시 /dashboard를 요청하면 로그인하지 않아도 된다.
> 만약 추가적인 filter를 등록할 것이라면 SecurityContextPersistenceFilter 우선순위 뒤에 와야 한다.
> 그래야 영속적인 유지가 가능하다.
> 기본적인 구현체는 HttpSessionSecurityContextRepository이다.

* 시큐리티 관련 헤더 추가하는 필터: HeaderWriterFilter
> 1.헤더에 대한 필터를 적용할 수 있게 해 준다. (예 : https만 허용) <br/>
> 2.나중에 필요한 상황이 오면 강의를 한 번 더 보도록 하자.

* CSRF 어택 방지 필터: CsrfFilter
> 1.요청이 들어오면 client에 csrf 토큰을 발행해준다. 화면에서 hidden으로 csrf 토큰이 있음<br/>
> 강의 정리 필요

* CSRF 토큰 사용 예제
> 1.목표 <br/>
> 회원가입 페이지에서 _csrf 값 확인해보기
> > 1.SignupController 만들기 @GetMapping, PostMapping으로 회원가입 처리 <br/>
> > 2.html은 복사
![로그인페이지](./doc/로그인페이지.JPG)
> 포스트맨으로 요청하면 _csrf값이 없어서 401 error가 난다.

> 2.목표 <br/>
> 회원가입페이지로 가면 응답으로 _csrf가 포함되어 있는 테스트코드 작성
> 회원가입요청 시 csrf 없는 요청 실패 테스트 작성
> 회원가입요청 시 csrf 포함 요청 성공 테스트 작성

* 로그아웃 처리 필터: LogoutFilter
> /logout은 DefaultLogoutPageGeneratingFilter가 페이지를 만들어줌. <br/>
> logout 화면에서 버튼을 누른 경우 post로 요청이 날아가고 logouthandler가 실행
> , 다 성공하면 logoutSuccessHandler가 동작하므로 
> 여러 logouthandler를 등록할 수 있다.<br/> 

> 1.목표
> logout을 커스터마이징 해보기
> configure에 설정

* 폼 인증 처리 필터: UsernamePasswordAuthenticationFilter
> 1.복습하기 <br/>
> AuthenticationManager, Authentication
> 로그인 로직이 AuthenticationManager 에 등록되어 맞다면 Authentication를 SecurityContext에 담는다.
> 
* 로그인/로그아웃 폼 페이지 생성해주는 필터: DefaultLogin/LogoutPageGeneratingFilter
> http.formLogin() 다음에 필터 체인 <br/>
> 로그인을 등록하면 로그아웃 필터도 같이 사라진다.

* 로그인/로그아웃 폼 커스터마이징
> 1.로그인,로그아웃 페이지 커스터마이징 <br/>
>>1.configure 재정의, controller로 page 요청 처리

* Basic 인증 처리 필터: BasicAuthenticationFilter
> 1.base64기반 기본인증 <br/>
> 2.보안에 취약하기 때문에 base64사용 <br/>

* 요청 캐시 필터: RequestCacheAwareFilter
> 1.로그인 하지 않은 상태로 /dashboard 요청시 wrappedSavedRequest에 /dashboard 요청을 넣어놓고 /login이 성공 했을 때
> 다시 /dashboard로 보내준다. <br/>
 
* 시큐리티 관련 서블릿 스팩 구현 필터: SecurityContextHolderAwareRequestFilter
> 1.소제목 <br/>

* 익명 인증 필터: AnonymousAuthenticationFilter
> 1.anonymous 를 커스터마이징 할 때 쓴다. 거의 쓸일이 없다고 한다. <br/>

* 세션 관리 필터: SessionManagementFilter
> 1.세션 변조 방지 전략 <br/>
> migrateSession(서블릿 3.0 이하) <br/>
> changeSessionId(서블릿 3.1 이상) <br/>
> 
> 2.유효하지 않은 세션 리다이렉트 url <br/>
> invalidSessionUrl  <br/>
> 
> 3.동시성 제어 <br/>
> maximumSessions <br/>
> 브라우저를 바꿔서 maximumSessions 테스트해보기 <br/>
> ![기존로그인세션만료](./doc/기존로그인세션만료.JPG)
> 
> 4.세션생성전략 <br/>
> IF_REQUIRED <br/>
> NEVER <br/>
> STATELESS <br/>
> ALWAYS <br/>
> 만약 서버가 여러 대라면 spring session을 공부 <br/>

* 인증/인가 예외 처리 필터: ExceptionTranslationFilter
> ExceptionTranslationFilter -> FilterSecurityInterceptor(AccessDecisionManager, AffirmativeBased) <br/>
> <br/>(인증)AuthenticationException -> AuthenticationEntryPoint
> <br/>(인가)AccessDeniedException-> AccessDenidedHandler로 처리

> FilterSecurityInterceptor 다음에 ExceptionTranslationFilter가 와야 한다. FilterSecurityInterceptor는 두가지 예외를 발생시킨다.
> <br/>인증(Authentication)과 인가(Access) 
> <br/>인증이 실패하는 경우 AuthenticationEntryPoint가 처리
> <br/>인가가 실패하는 경우 AccessDeniedHandler 처리 <br/>
> 
> 목표 <br/>
> AccessDenied에 대한 페이지를 재설정 해보자. (로그도 남기는 코드를 작성해보자)<br/>
>![인가예외처리](./doc/인가예외처리.JPG)
> 

* 인가 처리 필터: FilterSecurityInterceptor
> 1.rememberMe, fullyAuthenticated <br/>
> rememberMe 로그인 기억하기, fullyAuthenticated 로그인 기억 무시

* 토큰 기반 인증 필터 : RememberMeAuthenticationFilter
> 목표 <br/>
> 로그인 기억하기 기능 만들기 <br/>
> > 1.configure에 http.rememberMe() 설정 <br/>
> rememberMe를 설정하게 되면 RememberMeAuthenticationFilter가 추가적으로
> 등록되게 되고 cookie값으로 사용자에게 하나 더 내려가게 된다.
> jssessionId를 지우더라도 다시 cookie 값에 의해 jsessionId가 만들어지므로
> 테스트는 로그인 후 jsessionId cookie를 지우고 다시 브라우저 재 실행시
> 로그인이 풀리지 않고 jsessionId가 생성되는것을 보면 된다.
> ![로그인유지](./doc/로그인유지.JPG)

> * 커스텀 필터 추가하기
    > WebAsyncManagerIntegrationFilter 앞에 시간을 찍는 필터를 추가해보자. <br/>
> > 1.LoggingFilter class 생성, GenericFilterBean 상속 <br/>
> > 2.doFilter 작성
> > 3.configure 에서 addFilter 사용.