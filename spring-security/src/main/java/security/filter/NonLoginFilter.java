package security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.service.impl.CustomUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Pecker
 * @Description 对登录以外的请求做处理
 * @since 2022-03-08
 */
@Component
@Slf4j
public class NonLoginFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        /*
         * 请求流程:
         * 1.登录接口走 LoginFilter
         * 2.登录成功后返回 token
         * 3.其他请求从 token 中校验用户信息
         */

        // 获取 token 校验用户信息
        String authHeader = request.getHeader("token");
        if (authHeader != null) {
            //解析token获取用户名
            String username = getUserNameByToken(authHeader);
            log.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private String getUserNameByToken(String token) {
        return token;
    }
}

