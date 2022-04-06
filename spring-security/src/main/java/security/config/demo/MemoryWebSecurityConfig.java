package security.config.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Pecker
 * @Description 普通形式的配置, 直接设置用户名密码到内存中
 * @since 2022-03-08
 */
//@Configuration
public class MemoryWebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 指定密码编码格式方式:
     * 1.此种方式配置@Bean 属性
     * 2.通过实现UserDetailsService,构建 User 指定: .password("{noop}123456")
     *
     * @return 编码格式
     */
    //@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("123456");

        // 使用基于内存的 InMemoryUserDetailsManager
        auth.inMemoryAuthentication()
                //使用 PasswordEncoder 密码编码器
                // .passwordEncoder(passwordEncoder())
                // 配置用户
                .withUser("admin").password(password).roles("admin")
                // 配置其他用户
                .and().withUser("user").password(password).roles("user")
                .and().withUser("other").password(password).roles("other");
    }

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    //    //super.configure(http);
    //    /*
    //     下面配置的含义是：
    //     1.如果请求路径满足 /admin/** 格式，则用户需要具备 admin 角色。
    //     2.如果请求路径满足 /user/** 格式，则用户需要具备 user 角色。
    //     3.剩余的其他格式的请求路径，只需要认证（登录）后就可以访问。
    //     */
    //    http.authorizeRequests()
    //            .antMatchers("/**/admin/**").hasRole("admin")
    //            .antMatchers("/**/user/**").hasRole("user")
    //            .anyRequest().authenticated();
    //}
}