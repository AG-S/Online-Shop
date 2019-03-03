package onlineshop.service.impl;

import onlineshop.dao.UserDao;
import onlineshop.entity.User;
import onlineshop.entity.UserType;
import onlineshop.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class UserService implements IUserService {

    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getByLogin(String login) {

        return userDao.getByLogin(login);
    }

    @Override
    public UserType getTypeByLogin(String login) {
        return userDao.getTypeByLogin(login);
    }

    @Override
    public boolean isUserAdmin(String login) {
        System.out.println(getTypeByLogin(login).toString());
        return  getTypeByLogin(login).equals(UserType.ADMIN);

    }

    @Override
    public boolean isPasswordOk(String login, String password) {
        User user = userDao.getByLogin(login);
	    String hashPasswordForCompare = DigestUtils.md5Hex(login+user.getSole());
        return user.getPassword().compareTo(hashPasswordForCompare)==0;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
