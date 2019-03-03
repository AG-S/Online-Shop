package onlineshop.service.impl;

import onlineshop.dao.ProductDao;
import onlineshop.entity.Product;
import onlineshop.service.IProductService;

import java.util.List;

public class ProductService implements IProductService{
    private ProductDao productDao;

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product findById(int id) {

        return productDao.getById(id);
    }

    @Override
    public void insert(Product product) {
        productDao.insert(product);

    }

    @Override
    public void deleteById(int id) {
        productDao.getById(id);

    }

    @Override
    public void update(Product product) {
        productDao.update(product);

    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
