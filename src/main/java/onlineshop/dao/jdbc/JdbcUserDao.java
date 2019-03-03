package onlineshop.dao.jdbc;

import onlineshop.dao.UserDao;
import onlineshop.dao.mapper.UserRowMapper;
import onlineshop.entity.User;
import onlineshop.entity.UserType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static final String SELECT_ALL = "Select id, login, password, user_type, sole From user_p;";
    private static final String SELECT_BY_LOGIN = "Select id, login, password, user_type, sole From user_p Where login = ?;";
    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            List<User> users = new ArrayList<>();
            UserRowMapper userRowMapper = new UserRowMapper();
            while (resultSet.next()) {
                User user = (userRowMapper.mapRow(resultSet));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Get all users method failed", e);
        }

    }

    @Override
    public User getByLogin(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LOGIN);){
            preparedStatement.setString(1,login);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                UserRowMapper userRowMapper = new UserRowMapper();
                resultSet.next();
                User user = (userRowMapper.mapRow(resultSet));
                if (resultSet.next()) {
                    throw new RuntimeException("More than one row for user were selected by login = "+login);
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Get user by Login method failed", e);
        }
    }

    @Override
    public UserType getTypeByLogin(String login) {
        return getByLogin(login).getUserType();
    }
}
