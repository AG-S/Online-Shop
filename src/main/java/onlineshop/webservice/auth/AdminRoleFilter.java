package onlineshop.webservice.auth;

import onlineshop.service.impl.SessionService;
import onlineshop.service.impl.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminRoleFilter implements Filter {

    private SessionService sessionService;

    public AdminRoleFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (isUserAdmin((HttpServletRequest) servletRequest)) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isUserAdmin (HttpServletRequest httpServletRequest){
        return sessionService.sessionBelongsAdmin(httpServletRequest.getCookies());
    }
}
