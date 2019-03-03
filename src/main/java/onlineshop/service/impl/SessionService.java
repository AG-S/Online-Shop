package onlineshop.service.impl;

import onlineshop.entity.Session;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

public class SessionService {
    private List<Session> sessions;
    private UserService userService;

    public SessionService(UserService userService) {
        this.userService = userService;
        sessions =  Collections.synchronizedList(new ArrayList<Session>());
    }

    public Session login(String login, String password){
        if (!isPasswordOk(login,password)) {
            return null;
        }
        Session session = new Session();
        session.setUser(userService.getByLogin(login));
        String token = UUID.randomUUID().toString();
        session.setToken(token);
        session.setExpireTime(LocalDateTime.now().plusSeconds(60*3));
        sessions.add(session);
        return session;
    }

    public boolean isTokenValid(String token){
        Session session = getByToken(token);
        if (session == null)  {
            return false;
        }
        if (session.getExpireTime().isAfter(LocalDateTime.now())) {
            return true;
        }
        removeSession(session);
        return false;
        //LocalDateTime.now().compareTo(session.getExpireTime()) < 0

    }

    public void removeSession(Session session){
        if (session != null) {
            sessions.remove(session);
        }
    }

    public void removeSession(Cookie[] cookies){
        removeSession(getByCookie(cookies));
    }

    public boolean sessionBelongsAdmin(Cookie[] cookies){
        return userService.isUserAdmin(getByCookie(cookies).getUser().getLogin());
    }


    private boolean isPasswordOk(String login, String password){
        return userService.isPasswordOk(login, password);
    }


    private Session getByToken(String token){
        for (Session session : sessions) {
            if (token.compareTo(session.getToken()) == 0) {
                return session;
            }
        }
        return null;
    }


    private Session getByCookie(Cookie[] cookies){
        for (Cookie cookie : cookies) {
            if ("superuser".compareTo(cookie.getName()) == 0) {
                return getByToken(cookie.getValue());
            }
        }
        return null;
    }

}
