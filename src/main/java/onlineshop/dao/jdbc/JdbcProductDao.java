package onlineshop.dao.jdbc;

import onlineshop.dao.ProductDao;
import onlineshop.dao.mapper.ProductRowMapper;
import onlineshop.entity.Product;
import onlineshop.entity.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final String SELECT_ALL = "Select id, name, cost From product;";
    private static final String INSERT = "Insert into Product (id, name, cost) values (?, ?, ?);";
    private static final String UPDATE = "Update Product set name = ?, cost = ? where id = ?;";
    private static final String DELETE = "Delete from Product where id = ?;";
    private static final String SELECT_BY_ID = "Select id, name, cost From product Where id = ?;";
    private DataSource dataSource;

    private static ProductRowMapper productRowMapper = new ProductRowMapper();


    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = (productRowMapper.mapRow(resultSet));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Get all method failed", e);
        }
    }

    public void insert(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT);){
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getCost());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Insert method failed", e);
        }

    }

    @Override
    public Product getById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                ProductRowMapper productRowMapper = new ProductRowMapper();
                resultSet.next();
                Product product = (productRowMapper.mapRow(resultSet));
                if (resultSet.next()) {
                    throw new RuntimeException("More than one row for product were selected by id = "+id);
                }
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Get by ID  method failed", e);
        }

    }

    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);){
            preparedStatement.setInt(3, product.getId());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getCost());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Update method failed", e);
        }

    }

    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE);){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Delete method failed", e);
        }

    }
}
