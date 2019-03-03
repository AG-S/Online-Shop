package onlineshop.dao.mapper;

import onlineshop.entity.User;
import onlineshop.entity.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setUserType((resultSet.getInt("user_type") ==1 ) ? UserType.ADMIN :UserType.USER);
        user.setSole(resultSet.getString("sole"));
        return user;
    }
}
