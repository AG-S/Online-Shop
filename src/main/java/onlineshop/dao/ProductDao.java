package onlineshop.dao;

import onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    void insert(Product product);
    void update(Product product);
    void delete(int id);
    Product getById(int id);

}
