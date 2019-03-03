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

public class UpdateProductServlet  extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        Product product = productService.findById(id);
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String,Object> params = new HashMap<>();
        params.put("product",product);
        String page = pageGenerator.getPage("updateProduct.html",params);
        resp.getWriter().write(page);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        product.setId(Integer.valueOf(req.getParameter("id")));
        product.setName(req.getParameter("name"));
        product.setCost (Double.valueOf(req.getParameter("cost")));
        productService.update(product);
        resp.sendRedirect("/products");

    }


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
