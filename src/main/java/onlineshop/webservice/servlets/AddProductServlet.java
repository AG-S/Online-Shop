package onlineshop.webservice.servlets;

import onlineshop.entity.Product;
import onlineshop.service.impl.ProductService;
import onlineshop.webservice.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> params = new HashMap<>();
        params.put("product", product);
        String page = pageGenerator.getPage("addProduct.html", params);
        resp.getWriter().write(page);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        product.setId(Integer.valueOf(req.getParameter("id")));
        product.setName(req.getParameter("name"));
        product.setCost(Double.valueOf(req.getParameter("cost")));
        productService.insert(product);
        resp.sendRedirect("/products");

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
