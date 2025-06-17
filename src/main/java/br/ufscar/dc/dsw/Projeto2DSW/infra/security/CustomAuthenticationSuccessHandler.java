package br.ufscar.dc.dsw.Projeto2DSW.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        String url = request.getContextPath();

        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();

            if (authority.equals("ROLE_ADMINISTRADOR")) {
                url += "/administrador/home";
                break;
            } else if (authority.equals("ROLE_TESTADOR")) {
                url += "/testador/home";
                break;
            }
        }

        response.sendRedirect(url);
    }
}
