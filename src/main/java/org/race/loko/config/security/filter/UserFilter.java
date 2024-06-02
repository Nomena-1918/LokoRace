package org.race.loko.config.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.race.loko.models.profil.Admin;
import org.race.loko.models.profil.Equipe;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class UserFilter implements Filter {

    @Value("${session.keyuser}")
    private String user="user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        if (user instanceof Admin || user instanceof Equipe) {
            chain.doFilter(request, response);
        }

        else {
            ((HttpServletResponse) response).sendRedirect("/access-denied");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
