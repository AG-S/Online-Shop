package onlineshop.webservice.servlets;

import onlineshop.entity.Product;
import onlineshop.service.impl.ProductService;
import onlineshop.webservice.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllProductServlet extends HttpServlet {
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAll();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String,Object> params = new HashMap<>();
        params.put("products",products);
        String page = pageGenerator.getPage("index.html",params);
        resp.getWriter().write(page);
    }
}
