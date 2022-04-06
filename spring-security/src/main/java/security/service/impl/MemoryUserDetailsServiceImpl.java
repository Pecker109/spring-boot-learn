package security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-03-08
 */
//@Service
public class MemoryUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String psd = passwordEncoder.encode("123456");
        //String psd = BCrypt.hashpw("123456", BCrypt.gensalt());
        return User.withUsername("admin")
                // 注意密码格式 : PasswordEncoderFactories 可看有哪些编码格式
                //.password("{noop}123456")
                .password(psd)
                .authorities("admin")
                .build();
    }


}