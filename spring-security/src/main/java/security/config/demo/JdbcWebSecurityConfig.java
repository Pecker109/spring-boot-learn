//package security.config.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
///**
// * @author Pecker
// * @Description JDBC 方式配置,从数据库中获取认证和授权信息
// * @since 2022-03-08
// */
////@Configuration
//public class JdbcWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserService userService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    /**
//     * 认证相关的配置
//     *
//     * @param auth auth
//     * @throws Exception Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //设置UserDetailsService的实现类
//        auth.userDetailsService(userService);
//    }
//
//
//    /**
//     * 授权相关的配置
//     *
//     * @param http http
//     * @throws Exception Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 自定义表单登录
//        http.formLogin() //表单提交
//                .loginPage("/login.html") //自定义登录页面
//                .loginProcessingUrl("/user/login")  //登录访问路径，必须和表单提交接口一样
//                .defaultSuccessUrl("/admin/index")   //认证成功之后跳转的路径
//                //自定义退出页面
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login.html")
//                .and().authorizeRequests()
//                //设置哪些路径可以直接访问，不需要认证
//                .antMatchers("/user/login", "/login.html").permitAll()
//                .anyRequest().authenticated(); //需要认证
//        //.and().csrf().disable(); //关闭csrf防护
//    }
//
//
//}
