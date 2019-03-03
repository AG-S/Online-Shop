package onlineshop.webservice.auth;

import onlineshop.service.impl.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {
    private String[] userParams;
    private UserService userService;

    public UserFilter(String[] userParams, UserService userService) {
        this.userParams = userParams;
        this.userService = userService;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (isPasswordOk()) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isPasswordOk (){
        return userService.isPasswordOk(userParams[0], userParams[1]);
    }

}
