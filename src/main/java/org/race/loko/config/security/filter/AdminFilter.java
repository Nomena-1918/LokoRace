package org.race.loko.config.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.race.loko.model.profil.Admin;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class AdminFilter implements Filter {

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

        if (session.getAttribute(user) instanceof Admin) {
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
