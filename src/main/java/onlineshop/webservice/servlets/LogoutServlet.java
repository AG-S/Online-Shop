package onlineshop.webservice.servlets;

import onlineshop.service.impl.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet  extends HttpServlet {
    private SessionService sessionService;

    public LogoutServlet(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sessionService.removeSession(req.getCookies());
        resp.sendRedirect("/login");
    }
}
