package security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import security.filter.LoginFilter;
import security.handler.*;
import security.model.RespBean;
import security.service.impl.CustomUserDetailService;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * @author Pecker
 * @Description 自定义 SpringSecurity 配置
 * 现在这个配置登录逻辑是没问题的,登录/doLogin 会走 LoginFilter 进行认证和授权,
 * 其他接口在登录之前都会报错,这也是没问题的,
 * 问题是登录完之后其他的接口都会有同一个 AuthenticationToken ,其他接口不会再进行认证过程了
 * 这肯定是有问题的
 * @since 2022-03-08
 */
@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 主要用户获取 UserDetail
     */
    @Autowired
    CustomUserDetailService userDetailService;
    /**
     * 无权限拦截器
     */
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    /**
     * 认证成功处理器
     */
    @Autowired
    CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    /**
     * 认证失败处理器
     */
    @Autowired
    CustomAuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 登出成功处理器
     */
    @Autowired
    CustomLogoutSuccessHandler logoutSuccessHandler;
    /**
     * 授权管理器
     */
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    /**
     * 根据用户传来的请求地址，分析出请求需要的角色
     */
    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 登录成功处理逻辑
        loginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        // 登录失败处理逻辑
        loginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // 配置认证管理器
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        // 配置认证管理器要处理的 url
        loginFilter.setFilterProcessesUrl("/doLogin");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }

    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 安全过滤器链配置
     *
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Spring Security可以通过 http.authorizeRequests() 对web请求进行授权保护(主要是对url进行控制),Spring Security使用标准Filter建立了对web请求的拦截，最终实现对资源的授权访问。
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and()
                .csrf().disable()
                //没有认证时，在这里处理结果，不要重定向
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
            HttpServletResponse resp = event.getResponse();
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(401);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(RespBean.error("您已在另一台设备登录，本次登录已下线!")));
            out.flush();
            out.close();
        }), ConcurrentSessionFilter.class);

        // 自定义的 LoginFilter 替换 UsernamePasswordAuthenticationFilter
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }




}
