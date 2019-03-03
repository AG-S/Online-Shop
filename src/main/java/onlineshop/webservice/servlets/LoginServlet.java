package onlineshop.webservice.servlets;

import onlineshop.entity.Session;
import onlineshop.service.impl.SessionService;
import onlineshop.webservice.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class LoginServlet extends HttpServlet {
    private SessionService sessionService;

    public LoginServlet(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doGet(HttpServletRequest req,
                      HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.instance().getPage("login.html"));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = sessionService.login(req.getParameter("login"),req.getParameter("password"));
        if (session == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        String token = session.getToken();
        Cookie cookie = new Cookie("superuser", token);
        cookie.setMaxAge(60*3);
        resp.addCookie(cookie);
        resp.sendRedirect("/products");
    }
}
