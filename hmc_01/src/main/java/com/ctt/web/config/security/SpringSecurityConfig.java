package com.ctt.web.config.security;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.ResponseUtils;
import com.ctt.web.config.security.test.MobileAuthenticationSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 2:00
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    public SpringSecurityConfig(){
        super(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(dbUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println(findByIndexNameSessionRepository);
//        http.anonymous().disable();
        //配置路径权限
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/user/login","/video/list"
                ,"/video/priviewVideo","/vlog/list","/vlog/getVlog","/photo/photoList"
                ,"/photo/photoList","/sysFile/fileList","/sysFile/getFile","/sysFile/priviewImg").permitAll()
                .anyRequest().authenticated();

        //配置登陆登出接口
        http.formLogin().
                loginPage("/user/login")
                .successHandler(customAuthenticationSuccessHandler).failureHandler(customAuthenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
//                .addLogoutHandler(logoutHandler)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout","POST"))
                .logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                .and()
                .apply(mobileAuthenticationSecurityConfig);
        http.rememberMe().rememberMeParameter("remember").tokenRepository(redisPersistentTokenRepository).userDetailsService(dbUserDetailsService);
        SessionRegistry s = sessionRegistry();
        http.cors().and()
                .csrf()
                .disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .sessionManagement()
                .sessionAuthenticationFailureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        System.out.println("sessionAuthenticationFailureHandler : .....");
                    }
                })
                //最大session并发数量1
                .maximumSessions(1)
                //false之后登录踢掉之前登录,true则不允许之后登录
//                .expiredSessionStrategy(sessionInformationExpiredStrategy())
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(s);

        //配置错误处理
        http.exceptionHandling()
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        http.addFilterAt(new ConcurrentSessionFilter(s, event -> {
            ResponseUtils.out(event.getRequest(),event.getResponse(),WebResBean.createResBean(SystemStatusEnum.E_20014));
        }), ConcurrentSessionFilter.class);

    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new CustomSessionInformationExpiredStrategy();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(findByIndexNameSessionRepository);
    }

    /**
     * 密码加密
     */

    @Autowired
    private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new CustomBCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTION","DELETE","PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /** beans **/

//    @Resource
//    private LogoutHandler logoutHandler;

    @Autowired
    private FindByIndexNameSessionRepository findByIndexNameSessionRepository;

    @Autowired
    private RedisPersistentTokenRepository redisPersistentTokenRepository;

    @Autowired
    private DbUserDetailsService dbUserDetailsService;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;


}
