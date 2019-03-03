package onlineshop.service;

import onlineshop.entity.User;
import onlineshop.entity.UserType;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getByLogin(String login);
    UserType getTypeByLogin(String login);
    boolean isUserAdmin(String login);
    boolean isPasswordOk(String login, String password);
}
