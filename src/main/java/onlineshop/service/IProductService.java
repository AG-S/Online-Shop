package onlineshop.service;

import onlineshop.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();

    Product findById(int id);

    void insert(Product product);

    void deleteById(int id);

    void update(Product product);

}
