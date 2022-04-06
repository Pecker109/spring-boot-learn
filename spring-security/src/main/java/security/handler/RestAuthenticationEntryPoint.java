package security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import security.model.RespBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Pecker
 * @Description 当未登录或者访问失效时，自定义的返回结果
 * 也可以自定义权限受限处理，需要实现 AccessDeniedHandler接口,比如 MyAccessDeniedHandler
 * @since 2022-03-24
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(401);
        PrintWriter out = response.getWriter();
        RespBean respBean = RespBean.error("访问失败!");
        if (authException instanceof InsufficientAuthenticationException) {
            respBean.setMsg("请求失败，请联系管理员!");
        }
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
