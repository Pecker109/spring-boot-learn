package security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security.model.Hr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Pecker
 * @Description 对登录逻辑进行认证操作, 继承并且在配置类里替换掉UsernamePasswordAuthenticationFilter
 * @since 2022-03-08
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    SessionRegistry sessionRegistry;

    /**
     * 该方法在 Filter#doFilter()(AbstractAuthenticationProcessingFilter) 方法中会被调用
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 前端传参方式是通过 json 方式进入 if 逻辑,表单方式进入 else
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            Map<String, String> loginData;
            loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);

            String username = ObjectUtils.defaultIfNull(loginData.get(getUsernameParameter()), StringUtils.EMPTY);
            String password =  ObjectUtils.defaultIfNull(loginData.get(getPasswordParameter()),StringUtils.EMPTY);
            username = username.trim();
            // 将从请求体中得到的用户名密码信息封装成 UsernamePasswordAuthenticationToken
            // 后续会和从 UserDetailService#loadUserByUsername() 加载到的用户名密码做比较
            // 根据用户名获取到 UserDetail 信息就是代表认证成功,否则会抛出异常
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            Hr principal = new Hr();
            principal.setUsername(username);
            // 还不太清楚干嘛的
            sessionRegistry.registerNewSession(request.getSession(true).getId(), principal);
            // 调用 ProviderManager#authenticate() 进行认证
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

}
