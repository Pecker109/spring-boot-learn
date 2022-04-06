package security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import security.model.Hr;
import security.model.RespBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Pecker
 * @Description 自定义认证成功逻辑处理
 * @since 2022-03-24
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Hr hr = (Hr) authentication.getPrincipal();
        hr.setPassword(null);
        RespBean ok = RespBean.ok("登录成功!", hr);
        String s = new ObjectMapper().writeValueAsString(ok);
        out.write(s);
        out.flush();
        out.close();
    }
}
