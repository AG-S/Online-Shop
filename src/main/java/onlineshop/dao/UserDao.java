package onlineshop.dao;

import onlineshop.entity.User;
import onlineshop.entity.UserType;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    User getByLogin(String login);
    UserType getTypeByLogin(String login);
}
