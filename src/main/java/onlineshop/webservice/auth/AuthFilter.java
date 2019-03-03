package onlineshop.webservice.auth;

import onlineshop.service.impl.SessionService;
import onlineshop.service.impl.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthFilter implements Filter {
    private SessionService sessionService;

    public AuthFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (isTokenValid(httpServletRequest)) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private boolean isTokenValid(HttpServletRequest httpServletRequest){
        boolean result = false;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("superuser".equals(cookie.getName())) {
                    if (sessionService.isTokenValid(cookie.getValue())) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
